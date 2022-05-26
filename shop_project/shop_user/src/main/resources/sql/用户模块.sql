-- 租户表、用户表、角色表、权限表、角色权限表、用户角色表
CREATE TABLE `user`
(
    `id`              int(11)  NOT NULL AUTO_INCREMENT,
    `username`        varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
    `password`        varchar(64)          DEFAULT NULL COMMENT '密码',
    `email`           varchar(255)         DEFAULT NULL COMMENT '电子邮箱',
    `nickname`        varchar(50)          DEFAULT NULL COMMENT '昵称',
    `real_name`       varchar(50)          DEFAULT NULL COMMENT '真实姓名',
    `phone`           varchar(20)          DEFAULT NULL COMMENT '手机号',
    `id_card`         varchar(20)          DEFAULT NULL COMMENT '身份证号',
    `register_time`   varchar(50)          DEFAULT NULL COMMENT '注册时间',
    `register_ip`     varchar(50)          DEFAULT NULL COMMENT '注册ip',
    `last_login_time` varchar(50)          DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(50)          DEFAULT NULL COMMENT '最后登录ip',
    `sex`             varchar(10)          DEFAULT NULL COMMENT '性别',
    `birthday`        varchar(20)          DEFAULT NULL COMMENT '出生日期',
    `province`        varchar(50)          DEFAULT NULL COMMENT '省',
    `city`            varchar(50)          DEFAULT NULL COMMENT '城市',
    `level`           int(11)              DEFAULT NULL COMMENT '用户级别',
    `login_count`     int(11)              DEFAULT NULL COMMENT '登录次数',

    `tenant_id`       int(11)           DEFAULT NULL COMMENT '登录次数',
    `remark`          varchar(50)          DEFAULT NULL COMMENT '备注',
    `is_deleted`      int(3)               DEFAULT NULL COMMENT '是否删除',
    `create_by`       int(11)     NOT NULL COMMENT '创建人',
    `create_time`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`       int(11)     NOT NULL COMMENT '更新人',
    `update_time`     timestamp            DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 租户表
CREATE TABLE `tenant`
(
    `id`          int(11)  NOT NULL AUTO_INCREMENT,
    `tenant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
    `remark`      varchar(50)          DEFAULT NULL COMMENT '备注',
    `is_deleted`  int(3)               DEFAULT NULL COMMENT '是否删除',
    `create_by`   int(11)     NOT NULL COMMENT '创建人',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   int(11)     NOT NULL COMMENT '更新人',
    `update_time` timestamp            DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;