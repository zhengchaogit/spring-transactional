package com.xiaojie.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CodeCommitRecord  implements Serializable{

    private Integer id;//主键
    private String codeCommitTitle;//提交标题
    private Integer codeCommitTotal;//总提交量
    private Integer codeCommitAdditions;//代码增加量
    private Integer codeCommitDeletions;//代码删除量
    private String commitBranchName;//提交分支名称
    private Integer projectId;//项目id 
    private String projectName;//项目名称
    private Integer userId;//用户id
    private String userName;//用户姓名
    private Date commitTime;//提交时间
    
}
