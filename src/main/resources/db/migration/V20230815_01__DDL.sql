SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `is_available` smallint(1) NOT NULL COMMENT '是否可以用：1-可用，2-不可用',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `is_available` smallint(1) NOT NULL COMMENT '是否可以用：1-可用，2-不可用',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_role_relevance
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relevance`;
CREATE TABLE `sys_user_role_relevance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

-- 初始化管理员用户
INSERT INTO `sys_user`(`user_id`, `username`, `password`, `is_available`) VALUES (1, 'admin', '$2a$10$zC7GADzuLMwsGyGFNZLas.xwRLjqafDadHu/hzYrapjdZDo.gp2Ce', 1);
INSERT INTO `sys_role`(`role_id`, `role_name`, `is_available`) VALUES (1, 'admin', 1);
INSERT INTO `sys_user_role_relevance`(`id`, `user_id`, `role_id`) VALUES (1, 1, 1);