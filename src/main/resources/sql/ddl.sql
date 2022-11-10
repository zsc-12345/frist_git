//学员表
CREATE TABLE `t_student`(
    `id`          INT(8) NOT NULL AUTO_INCREMENT,
    `descrption`  VARCHAR(128) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `status`      VARCHAR(1) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `modify_time` DATETIME NULL DEFAULT NULL,
    `create_time` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) COLLATE='utf8_bin'
ENGINE=InnoDB
;

//用戶学员表
CREATE TABLE `t_user_stu_relation`(
    `id`      INT(8) NOT NULL AUTO_INCREMENT,
    `user_id` INT(8) NULL DEFAULT NULL,
    `stu_id`  INT(8) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) COLLATE='utf8_bin'
ENGINE=InnoDB
;



//角色表
CREATE TABLE `t_role` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `status` INT(1) NULL DEFAULT '1',
    `create_time` DATETIME NULL DEFAULT NULL,
    `modify_time` DATETIME NULL DEFAULT NULL,
    `type` INT(1) NULL DEFAULT NULL,
    `remark` VARCHAR(128) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=2
;

//权限表
CREATE TABLE `t_auth` (
    `id` INT(8) NOT NULL AUTO_INCREMENT,
    `data_level` INT(1) NULL DEFAULT NULL,
    `url` VARCHAR(128) NULL DEFAULT NULL,
    `status` INT(1) NULL DEFAULT '1',
    `create_time` DATETIME NULL DEFAULT NULL,
    `modify_time` DATETIME NULL DEFAULT NULL,
    `remark` VARCHAR(128) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=2
;

//用户_角色_关系表
CREATE TABLE `t_user_role_relation` (
    `id` INT(8) NOT NULL AUTO_INCREMENT,
    `user_id` INT(8) NULL DEFAULT NULL,
    `role_id` VARCHAR(128) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=2
;

//角色_权限_关系表
CREATE TABLE `t_role_auth_relation` (
    `id` INT(8) NOT NULL AUTO_INCREMENT,
    `role_id` INT(8) NULL DEFAULT NULL,
    `auth_id` VARCHAR(128) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=2
;