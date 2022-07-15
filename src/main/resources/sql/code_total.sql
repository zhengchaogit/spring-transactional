

CREATE TABLE `user_info` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` VARCHAR(64) NOT NULL COMMENT '用户姓名',
  `access_token` VARCHAR(64) NOT NULL COMMENT 'token',
  PRIMARY KEY (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8mb3 COMMENT='用户信息表';





CREATE TABLE `project_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `project_id` varchar(10) NOT NULL COMMENT '项目id',
  `project_name` varchar(64) NOT NULL COMMENT '项目名称',
  `project_url` varchar(64) NOT NULL COMMENT '项目地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3 COMMENT='项目信息表';




CREATE TABLE `code_commit_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code_commit_title` text NOT NULL COMMENT '提交标题',
  `code_commit_total` int NOT NULL COMMENT '总提交量',
  `code_commit_additions` int NOT NULL COMMENT '代码增加量',
  `code_commit_deletions` int NOT NULL COMMENT '代码删除量',
  `commit_branch_name` varchar(10) NOT NULL COMMENT '提交分支名称',
  `project_id` int NOT NULL COMMENT '项目id',
  `project_name` varchar(64) NOT NULL COMMENT '项目名称',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_name` varchar(64) NOT NULL COMMENT '用户姓名',
  `commit_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3 COMMENT='代码提交记录表';


CREATE TABLE `code_commit_detail` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code_commit_id` int NOT NULL COMMENT '代码提交id',
  `code_commit_content` text NOT NULL COMMENT '提交内容差异对比',
  `commit_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3 COMMENT='代码提交明细表';


CREATE TABLE `code_statistics_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_status` int NOT NULL DEFAULT '1' COMMENT '处理状态  1 处理中 2 处理完成 3失败',
  `process_msg` text COMMENT '处理结果信息',
  `task_execution_time` varchar(10) NOT NULL COMMENT '执行任务时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='代码统计任务表';


