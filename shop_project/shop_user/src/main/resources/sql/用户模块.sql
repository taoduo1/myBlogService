-- 租户表、用户表、角色表、权限表、角色权限表、用户角色表
CREATE TABLE `user`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `username`        varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
    `password`        varchar(255)          DEFAULT NULL COMMENT '密码',
    `email`           varchar(255)          DEFAULT NULL COMMENT '电子邮箱',
    `nickname`        varchar(255)          DEFAULT NULL COMMENT '昵称',
    `real_name`       varchar(50)           DEFAULT NULL COMMENT '真实姓名',
    `phone`           varchar(20)           DEFAULT NULL COMMENT '手机号',
    `id_card`         varchar(20)           DEFAULT NULL COMMENT '身份证号',
    `register_time`   varchar(50)           DEFAULT NULL COMMENT '注册时间',
    `register_ip`     varchar(20)           DEFAULT NULL COMMENT '注册ip',
    `last_login_time` timestamp             DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`   varchar(50)           DEFAULT NULL COMMENT '最后登录ip',
    `sex`             varchar(2)            DEFAULT NULL COMMENT '性别',
    `birthday`        varchar(20)           DEFAULT NULL COMMENT '出生日期',
    `province`        varchar(50)           DEFAULT NULL COMMENT '省',
    `city`            varchar(50)           DEFAULT NULL COMMENT '城市',
    `level`           tinyint(4) DEFAULT NULL COMMENT '用户级别',
    `login_count`     int(11) DEFAULT NULL COMMENT '登录次数',
    `tenant_id`       int(11) DEFAULT 0 COMMENT '租户id',
    `remark`          varchar(50)           DEFAULT '' COMMENT '备注',
    `is_deleted`      int(3) DEFAULT 0 COMMENT '是否删除',
    `create_by`       int(11) NOT NULL COMMENT '创建人',
    `create_time`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`       int(11) NOT NULL COMMENT '更新人',
    `update_time`     timestamp             DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


-- 租户表
CREATE TABLE `tenant`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
    `code`        varchar(50) NOT NULL DEFAULT '' COMMENT '编号',
    `type`        tinyint(4) DEFAULT NULL COMMENT '类型',
    `remark`      varchar(50)          DEFAULT '' COMMENT '备注',
    `is_deleted`  int(3) DEFAULT 0 COMMENT '是否删除',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '更新人',
    `update_time` timestamp            DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='租户表';

-- 租户配置表
CREATE TABLE `tenant_config`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`        tinyint(4) DEFAULT 0 COMMENT '对应属性名',
    `value1`      varchar(50)        DEFAULT '' COMMENT '属性值1',
    `value2`      varchar(50)        DEFAULT '' COMMENT '属性值2',
    `value3`      varchar(50)        DEFAULT '' COMMENT '属性值3',
    `value4`      varchar(50)        DEFAULT '' COMMENT '属性值4',
    `value5`      varchar(50)        DEFAULT '' COMMENT '属性值5',
    `value6`      varchar(50)        DEFAULT '' COMMENT '属性值',
    `value7`      varchar(50)        DEFAULT '' COMMENT '属性值',
    `value8`      varchar(50)        DEFAULT '' COMMENT '属性值',
    `value9`      varchar(50)        DEFAULT '' COMMENT '属性值',
    `value10`     varchar(50)        DEFAULT '' COMMENT '属性值',
    `enable`      tinyint(4) DEFAULT 0 COMMENT '是否启用',
    `tenant_id`   int(11) DEFAULT 0 COMMENT '租户id',
    `remark`      varchar(50)        DEFAULT '' COMMENT '备注',
    `is_deleted`  int(3) DEFAULT 0 COMMENT '是否删除',
    `create_by`   int(11) NOT NULL COMMENT '创建人',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   int(11) NOT NULL COMMENT '更新人',
    `update_time` timestamp          DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='租户配置信息表';
