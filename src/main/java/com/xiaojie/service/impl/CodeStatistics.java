package com.xiaojie.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xiaojie.config.ApiProperties;
import com.xiaojie.entity.CodeCommitDetail;
import com.xiaojie.entity.CodeCommitRecord;
import com.xiaojie.entity.CodeStatisticsTask;
import com.xiaojie.entity.ProjectInfo;
import com.xiaojie.entity.UserInfo;
import com.xiaojie.enums.TaskStatusEnum;
import com.xiaojie.gitlab4j.api.Constants.ProjectOrderBy;
import com.xiaojie.gitlab4j.api.Constants.SortOrder;
import com.xiaojie.gitlab4j.api.GitLabApi;
import com.xiaojie.gitlab4j.api.GitLabApiException;
import com.xiaojie.gitlab4j.api.models.Branch;
import com.xiaojie.gitlab4j.api.models.Commit;
import com.xiaojie.gitlab4j.api.models.Diff;
import com.xiaojie.gitlab4j.api.models.Project;
import com.xiaojie.service.CodeCommitDetailService;
import com.xiaojie.service.CodeStatisticsTaskService;
import com.xiaojie.service.CommitInfoService;
import com.xiaojie.service.ProjectInfoService;
import com.xiaojie.service.UserInfoService;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CodeStatistics {
    
    @Autowired
    private CommitInfoService commitInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CodeCommitDetailService codeCommitDetailService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private CodeStatisticsTaskService codeStatisticsTaskService;
    
    /**
     * 
     * 测试一分钟执行一次
     * @date 2022年7月15日下午12:15:11
     *
     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void executeJob(String taskExecutionTime){
//        task(taskExecutionTime);
//    }
    
    /**
     * 
     * 每周星期天凌晨3点实行一次 
     * @date 2022年7月15日下午12:15:11
     *
     */
    @Scheduled(cron = "0 0 3 ? * SUN")//每周星期天凌晨3点实行一次 
    //0 */1 * * * ? 一分钟执行一次
    public void executeJobFirst(){
        String  taskExecutionTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        task(taskExecutionTime,1);
    }
    /**
     * 
     * 每周星期天凌晨3点10分实行一次
     * @date 2022年7月15日下午12:15:11
     *
     */
    @Scheduled(cron = "0 10 3 ? * SUN")//每周星期天凌晨3点10分实行一次
    public void executeJobSecond(){
        String  taskExecutionTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        task(taskExecutionTime,2);
    }
    /**
     * 
     * 每周星期天凌晨3点20分实行一次
     * @date 2022年7月15日下午12:15:11
     *
     */
    @Scheduled(cron = "0 20 3 ? * SUN")//每周星期天凌晨3点20分实行一次
    public void executeJobThird(){
        String  taskExecutionTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        task(taskExecutionTime,3);
    }
    /**
     * 执行任务方法
     * @date 2022年7月15日下午12:14:03
     * 
     * @param taskExecutionTime 执行时间
     * @param times 0:手动  1：第一次执行 2：第二次执行 3：第三次执行
     */
    public void task(String taskExecutionTime,int times){
        try {
            List<CodeStatisticsTask> codeStatisticsTasks = codeStatisticsTaskService.selectByTaskExecutionTime(taskExecutionTime);
            if(CollectionUtils.isNotEmpty(codeStatisticsTasks)&&codeStatisticsTasks.size()>0) {
                if(codeStatisticsTasks.get(0).getProcessStatus()==TaskStatusEnum.SUCCESS.getCode()) {
                    log.info("第"+times+"次执行,此次任务执行时间:"+taskExecutionTime+",任务执行成功,不要再次执行");
                    return;
                }else if(codeStatisticsTasks.get(0).getProcessStatus()==TaskStatusEnum.PROCESSING.getCode()){
                    log.info("第"+times+"次执行,此次任务执行时间:"+taskExecutionTime+",任务正在执行,请稍后重试");
                    return;
                }
            }else {
                log.info("第"+times+"次执行,此次任务执行时间:"+taskExecutionTime+",任务执行开始执行");
                CodeStatisticsTask codeStatisticsTask = new CodeStatisticsTask();
                codeStatisticsTask.setTaskExecutionTime(taskExecutionTime);
                codeStatisticsTask.setProcessStatus(TaskStatusEnum.PROCESSING.getCode());
                codeStatisticsTask.setCreateTime(new Date());
                codeStatisticsTask.setUpdateTime(new Date());
                codeStatisticsTaskService.save(codeStatisticsTask);
            }
            getUserCodeSta(taskExecutionTime);
            codeStatisticsTaskService.updateStatusByTaskExecutionTime(TaskStatusEnum.SUCCESS.getCode(), new Date(), taskExecutionTime );
        } catch (GitLabApiException e) {
            log.info("第"+times+"次执行,此次任务执行时间:"+taskExecutionTime+"代码统计定时任务调用gitlab接口出现异常",e);
            codeStatisticsTaskService.updateStatusByTaskExecutionTime(TaskStatusEnum.FAIL.getCode(), new Date(), taskExecutionTime );
        }catch (Exception e) {
            log.info("第"+times+"次执行,此次任务执行时间:"+taskExecutionTime+"代码统计定时任务出现异常",e);
            codeStatisticsTaskService.updateStatusByTaskExecutionTime(TaskStatusEnum.FAIL.getCode(),  new Date(), taskExecutionTime );
        }

    }
    
    
    /**
     * 
     * 获取用户代码统计
     * @date 2022年7月15日上午10:43:57
     * 
     * @throws GitLabApiException
     * @throws ParseException 
     */
    public void getUserCodeSta (String taskExecutionTime) throws GitLabApiException, ParseException  {
        String hostUrl = ApiProperties.hostUrl;
        String branchName = ApiProperties.branchName;
        List<UserInfo> users =   userInfoService.getUsers();
        for (UserInfo userInfo : users) {
            //获取当前用户所有的项目
            List<Project> userProjects =  getProjectByUser(hostUrl,userInfo.getAccessToken());
            if(CollectionUtils.isEmpty(userProjects)||userProjects.size()==0) {
                continue;
            }
            for (Project userProject : userProjects) {
                log.info("projectId:"+userProject.getId());
                List<ProjectInfo> projectInfos =  projectInfoService.selectByUserIdAndProjectName(userInfo.getId(), String.valueOf(userProject.getId()));
                if(CollectionUtils.isEmpty(projectInfos)||projectInfos.size()==0) {
                    //插入项目信息表
                    ProjectInfo projectInfo = new ProjectInfo();
                    projectInfo.setProjectName(userProject.getName());
                    projectInfo.setProjectId(String.valueOf(userProject.getId()));
                    projectInfo.setProjectUrl(userProject.getWebUrl());
                    projectInfo.setUserId(userInfo.getId());
                    projectInfoService.save(projectInfo);
                }
                
                //获取当前项目所有分支
                List<Branch> projectBranchs = getBranchByProject(hostUrl,userInfo.getAccessToken(),userProject.getId());
                if(CollectionUtils.isEmpty(projectBranchs)||projectBranchs.size()==0) {
                    continue;
                }
                for (Branch projectBranch : projectBranchs) { 
                    log.info("branchName:"+projectBranch.getName());
                    if(branchName.equals(projectBranch.getName())) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
                        Date date =simpleDateFormat.parse(taskExecutionTime);
                        Date since = DateUtil.beginOfWeek(date, true);
                        Date until = DateUtil.endOfWeek(date, true);
                        //根据分支name获取commits
                        List<Commit> commits = getCommitByBranch(hostUrl,userInfo.getAccessToken(),userProject.getId(),branchName,since,until);
                        if(CollectionUtils.isEmpty(commits)||commits.size()==0) {
                            continue;
                        }
                        DateTime startCommitTime = DateUtil.beginOfDay(new Date());
                        DateTime endCommitTime = DateUtil.endOfDay(new Date());
                        //删除当天代码提交记录表
                        commitInfoService.deteleByCommitTime(startCommitTime, endCommitTime);
                        //删除当天代码提交明细
                        codeCommitDetailService.deteleByCommitTime(startCommitTime, endCommitTime);
                        //删除记录
                        for(Commit commit : commits) {
                            log.info("commitId:"+commit.getId());
                            Commit userommit = getCodesByCommitId(hostUrl, userInfo.getAccessToken(), commit.getId(), userProject.getId());
                            log.info("additions:"+userommit.getStats().getAdditions());
                            log.info("deletions:"+userommit.getStats().getDeletions());
                            log.info("total:"+userommit.getStats().getTotal());
                            log.info("title:"+userommit.getTitle());
                            
                            //插入代码提交记录表
                            CodeCommitRecord commitRecord = new CodeCommitRecord();
                            commitRecord.setCodeCommitTotal(userommit.getStats().getTotal());
                            commitRecord.setCodeCommitAdditions(userommit.getStats().getAdditions());
                            commitRecord.setCodeCommitDeletions(userommit.getStats().getDeletions());
                            commitRecord.setCommitBranchName(branchName);
                            commitRecord.setCodeCommitTitle(userommit.getTitle());
                            commitRecord.setCommitTime(new Date());
                            commitRecord.setProjectId(userProject.getId().intValue());
                            commitRecord.setProjectName(userProject.getName());
                            commitRecord.setUserId(userInfo.getId());
                            commitRecord.setUserName(userInfo.getUserName());
                            commitInfoService.save(commitRecord);
                            
                            List<Diff> diffs =getCommitContentByCommitId(hostUrl, userInfo.getAccessToken(), commit.getId(), userProject.getId());
                            if(CollectionUtils.isEmpty(diffs)||diffs.size()==0) {
                                continue;
                            }
                            //插入代码提交明细表
                            CodeCommitDetail codeCommitDetail = null;
                            for(Diff diff : diffs) {
                                codeCommitDetail = new CodeCommitDetail();
                                codeCommitDetail.setCodeCommitId(commitRecord.getId());
                                codeCommitDetail.setCodeCommitContent(diff.getDiff());
                                codeCommitDetail.setCommitTime(new Date());
                                codeCommitDetailService.save(codeCommitDetail);
                            }
                        }
                    }
                    
                }
            
            }
        }
    }
    
    /**
     * 
     * 获取当前用户所有的项目
     * @date 2022年7月13日下午8:15:48
     * 
     * @param hostUrl
     * @param personalAccessToken
     * @return
     * @throws GitLabApiException
     */
    private List<Project>  getProjectByUser(String hostUrl, String personalAccessToken) throws GitLabApiException{
        //获取当前用户可见的所有项目（即使用户不是成员）
        GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
        List<Project> projects = gitLabApi.getProjectApi().getProjects(false,null,ProjectOrderBy.ID,SortOrder.DESC,null,false,false,true,false,false);
        return projects;
        
    }
    
    /**
     * 
     * 获取当前项目所有分支
     * @date 2022年7月13日下午8:15:33
     * 
     * @param hostUrl
     * @param personalAccessToken
     * @param projectId
     * @return
     * @throws GitLabApiException
     */
    private List<Branch>  getBranchByProject(String hostUrl, String personalAccessToken,Long projectId) throws GitLabApiException{
        //获取当前用户可见的所有项目（即使用户不是成员）
      GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
      List<Branch> branchs = gitLabApi.getRepositoryApi().getBranches(projectId);
      return branchs;
        
    }
    
    
    /**
     * 
     * 根据分支name获取commits
     * @date 2022年7月13日下午8:14:48
     * 
     * @param hostUrl
     * @param personalAccessToken
     * @param projectId
     * @param branchName
     * @return
     * @throws GitLabApiException
     */
    private List<Commit>  getCommitByBranch(String hostUrl, String personalAccessToken,Long projectId,String branchName,Date since, Date until) throws GitLabApiException{
      GitLabApi gitLabApi = new GitLabApi(hostUrl , personalAccessToken);
      List<Commit> commits = gitLabApi.getCommitsApi().getCommits(projectId,branchName,since,until);
      return commits;
        
    }
    
    /**
     * 
     * 根据commits的id获取代码量  
     * @date 2022年7月13日下午8:13:27
     * 
     * @param hostUrl
     * @param personalAccessToken
     * @param commitId
     * @param projectId
     * @return
     * @throws GitLabApiException
     */
    private Commit  getCodesByCommitId(String hostUrl, String personalAccessToken,String commitId,Long projectId) throws GitLabApiException{
      GitLabApi gitLabApi = new GitLabApi(hostUrl , personalAccessToken);
      Commit commit= gitLabApi.getCommitsApi().getCommit(projectId, commitId);
      return commit;
        
    }
    
    
    
    /**
     * 
     * 获取提交内容的差异
     * @date 2022年7月14日下午12:35:05
     * 
     * @param hostUrl
     * @param personalAccessToken
     * @param commitId
     * @param projectId
     * @return
     * @throws GitLabApiException
     */
    private List<Diff>  getCommitContentByCommitId(String hostUrl, String personalAccessToken,String commitId,Long projectId) throws GitLabApiException{
      GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken);
      List<Diff> diffs = gitLabApi.getCommitsApi().getDiff(projectId, commitId);
      return diffs;
    }
    
    
}
