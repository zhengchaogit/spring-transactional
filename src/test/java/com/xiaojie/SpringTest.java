package com.xiaojie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.xiaojie.gitlab.api.GitlabAPI;
import com.xiaojie.gitlab.api.models.GitlabIssue;
import com.xiaojie.gitlab.api.models.GitlabProject;
import com.xiaojie.gitlab4j.api.Constants.ProjectOrderBy;
import com.xiaojie.gitlab4j.api.Constants.SortOrder;
import com.xiaojie.gitlab4j.api.GitLabApi;
import com.xiaojie.gitlab4j.api.GitLabApiException;
import com.xiaojie.gitlab4j.api.models.Branch;
import com.xiaojie.gitlab4j.api.models.Commit;
import com.xiaojie.gitlab4j.api.models.Diff;
import com.xiaojie.gitlab4j.api.models.Project;
import com.xiaojie.service.impl.CodeStatistics;
import com.xiaojie.util.HttpClientUtil;
import com.xiaojie.util.Transform;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SpringTest {

    
    @Autowired
    private CodeStatistics codeStatistics;
    
    
//    @Test
//    public void getGroupProjects(){
//
//        GitlabAPI gitlabAPIIgnoreSSL=GitlabAPI.connect("https://gitlab.com","glpat-ojqGLt-SqynCymmyMkx4");
//        gitlabAPIIgnoreSSL.ignoreCertificateErrors(true);
//        GitlabGroup group =new GitlabGroup();
//        group.setId(id);
//        try {
//            List<GitlabIssue> listIssues=gitlabAPIIgnoreSSL.getGroupProjects(group);
//            System.out.println("List of issues: "+listIssues.size());
//            Transform.GitlabIssueToXMLYoutrack(listIssues);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        vtranInvalidCaseWithCatchException.add();
//    }
    
//    @Test
    public void createProject(){

        GitlabAPI gitlabAPIIgnoreSSL=GitlabAPI.connect("https://gitlab.com","glpat-ZUQgxZdzqBNnvhr8YW6n");
        gitlabAPIIgnoreSSL.ignoreCertificateErrors(true);
        try {
            GitlabProject project = new GitlabProject();
            gitlabAPIIgnoreSSL.createProject("232323");
            System.out.println("getAllProjects");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("getAllProjects: "+e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("getAllProjects: "+e.getMessage());
        }
    }
    
//    @Test
    public void getAllProjects(){

        GitlabAPI gitlabAPIIgnoreSSL=GitlabAPI.connect("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
        gitlabAPIIgnoreSSL.ignoreCertificateErrors(true);
        try {
            GitlabProject project = new GitlabProject();
            List<GitlabProject> pdsfdsf = gitlabAPIIgnoreSSL.getAllProjects();
            System.out.println("getAllProjects"+pdsfdsf.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("getAllProjects: "+e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("getAllProjects: "+e.getMessage());
        }
    }
    
//  @Test
  public void getUsers(){
      String result = "";
      try {
        Map<String, String> params = new HashMap<String, String>();
        params.put("PRIVATE-TOKEN", "glpat-oC9YFEF3MzoZJiUvnek3");
        params.put("accept", "application/json");
        //result = HttpClientUtil.doGet("https://gitlab.com/api/v4/metadata");
        result = HttpClientUtil.doGet("http://35.185.44.232/api/v4/metadata");
        // http://35.185.44.232/api/v4/metadata
        System.out.println(result);
    } catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      
  }
//第二步，获取当前用户可见的所有项目（即使用户不是成员）1
//  @Test
  public void test() {
      try {
          //https://gitlab.com/api/v4/projects?private_token=glpat-N3VJDTyNbzqCcuGxVa-m&membership=true
          GitLabApi gitLabApi = new GitLabApi("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
          List<Project> a = gitLabApi.getProjectApi().getProjects(false,null,ProjectOrderBy.ID,SortOrder.DESC,null,false,false,true,false,false);
          System.out.println(a.size());
          System.out.println(JSON.toJSONString(a));
          for(int i=0;i<a.size();i++) {
              System.out.println(a.get(i).getId());
          }
    } catch (GitLabApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  
  //第三步，遍历项目，根据项目id获取分支列表2
//  @Test
  public void project() {
      //https://gitlab.com/api/v4/projects/37620666/repository/branches?private_token=glpat-N3VJDTyNbzqCcuGxVa-m
      try {
          GitLabApi gitLabApi = new GitLabApi("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
          List<Branch> a = gitLabApi.getRepositoryApi().getBranches("37620666");
          System.out.println(JSON.toJSONString(a));
          System.out.println(a.size());
          for(int i=0;i<a.size();i++) {
              System.out.println(a.get(i).getName());
          }
    } catch (GitLabApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  //第四步，遍历分支，根据分支name获取commits3
//  @Test
  public void commit1() {
      //https://gitlab.com/api/v4/projects/37620666/repository/commits?ref_name=main&private_token=glpat-N3VJDTyNbzqCcuGxVa-m
      try {
          GitLabApi gitLabApi = new GitLabApi("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
          List<Commit> a = gitLabApi.getCommitsApi().getCommits("37620666","main",null,null);
          System.out.println(JSON.toJSONString(a));
          System.out.println(a.size());
          for(int i=0;i<a.size();i++) {
              System.out.println(a.get(i).getLastPipeline());
          }
    } catch (GitLabApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  
  //第五步，根据commits的id获取代码量 4
//  @Test
  public void commit() {
      http://gitlab地址/api/v4/projects/项目id/repository/commits/commits的id?private_token=xxx
      //http://gitlab.com/api/v4/projects/37620666/repository/commits/5c19dcd5f0c3c34f1939f3c51014a6314ba2d9c9&private_token=glpat-N3VJDTyNbzqCcuGxVa-m
      try {
          GitLabApi gitLabApi = new GitLabApi("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
          Commit a= gitLabApi.getCommitsApi().getCommit("37620666","5c19dcd5f0c3c34f1939f3c51014a6314ba2d9c9");
          System.out.println(JSON.toJSONString(a));
    } catch (GitLabApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  
//获取提交的差异  5
//  @Test
  public void commit2() {
      //curl --header "PRIVATE-TOKEN: <your_access_token>" "https://gitlab.example.com/api/v4/projects/37620666/repository/commits/master/diff"
      //http://gitlab.com/api/v4/projects/37620666/repository/commits/main&private_token=glpat-N3VJDTyNbzqCcuGxVa-m
      //https://gitlab.com/api/v4/projects/37620666/repository/commits/5c19dcd5f0c3c34f1939f3c51014a6314ba2d9c9/refs?type=all&private_token=glpat-N3VJDTyNbzqCcuGxVa-m
      try {
          GitLabApi gitLabApi = new GitLabApi("https://gitlab.com","glpat-N3VJDTyNbzqCcuGxVa-m");
          List<Diff> a= gitLabApi.getCommitsApi().getDiff("37620666","5c19dcd5f0c3c34f1939f3c51014a6314ba2d9c9");
          System.out.println(JSON.toJSONString(a));
    } catch (GitLabApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  
  
  
}
