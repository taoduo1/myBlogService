CREATE TABLE `f_attachchunk`
(
    `ID`                    int(11)     NOT NULL AUTO_INCREMENT,
    `chunk_guid`            varchar(50) NOT NULL COMMENT 'Guid',
    `file_md5`              varchar(100)  DEFAULT NULL COMMENT '文件MD5唯一标识文件',
    `file_name`             varchar(200)  DEFAULT NULL COMMENT '文件名称',
    `chunk_size`            int(11)       DEFAULT NULL COMMENT '分片大小',
    `chunk_count`           int(11)       DEFAULT NULL COMMENT '分片总数量',
    `chunk_index`           int(11)       DEFAULT NULL COMMENT '分片对应序号',
    `chunk_file_path`       varchar(500)  DEFAULT NULL COMMENT '分片存储路径（本地存储文件方案使用）',
    `upload_by`             varchar(50)   DEFAULT NULL COMMENT '上传人',
    `upload_date`           datetime      DEFAULT NULL COMMENT '上传日期',
    `upload_oss_id`         varchar(200)  DEFAULT NULL COMMENT '分片上传批次ID（云存储方案使用）',
    `upload_oss_chunk_info` varchar(1000) DEFAULT NULL COMMENT '分片上传单片信息（云存储方案使用）',
    `chunk_type`            varchar(50)   DEFAULT NULL COMMENT '分片存储方式（本地存储，阿里云，华为云，Minio标识）',
    `merge_status`          int(11)       DEFAULT NULL COMMENT '分片合并状态（未合并，已合并）',
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

