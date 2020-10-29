/*
 Navicat Premium Data Transfer

 Source Server         : My-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : eesy

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 29/10/2020 18:03:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'aaa', 900);
INSERT INTO `account` VALUES (2, 'bbb', 1100);
INSERT INTO `account` VALUES (3, 'cccc', 0.99);
INSERT INTO `account` VALUES (5, 'test', 4000.5);
INSERT INTO `account` VALUES (6, 'test2', 4000.989990234375);
INSERT INTO `account` VALUES (7, 'JdbcTemplate测试添加', 999.99);
INSERT INTO `account` VALUES (8, '测试', 444.44);

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(0) NOT NULL DEFAULT 0 COMMENT '权限所在的权限模块id',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求的url, 可以填正则表达式',
  `type` int(0) NOT NULL DEFAULT 3 COMMENT '类型，1：菜单，2：按钮，3：其他',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `seq` int(0) NOT NULL DEFAULT 0 COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES (1, '20201022222849_59', '进入产品管理页面', 1, '/sys/product/product.page', 1, 1, 1, '', 'admin', '2020-10-22 22:28:50', '220.165.241.80');
INSERT INTO `sys_acl` VALUES (2, '20201022222930_71', '查询产品列表', 1, '/sys/product/page.json', 2, 1, 2, '', 'admin', '2020-10-22 22:29:31', '220.165.241.80');
INSERT INTO `sys_acl` VALUES (3, '20201022223002_65', '产品上架', 1, '/sys/product/online.json', 2, 1, 3, 'online', 'admin', '2020-10-22 22:45:45', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (4, '20201022223029_85', '产品下架', 1, '/sys/product/offline.json', 2, 1, 4, '', '青霖', '2020-10-24 15:33:01', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (15, '20201025201555_69', '进入订单页', 2, '/sys/order/order.page', 1, 1, 1, '', 'admin', '2020-10-25 20:15:56', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (16, '20201025201650_87', '查询订单列表', 2, '/sys/order/list.json', 2, 1, 2, '', 'admin', '2020-10-25 20:16:51', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (17, '20201025201914_35', '进入公告管理页', 3, '/sys/notice.page', 1, 1, 1, '', 'admin', '2020-10-25 20:19:14', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (18, '20201025202305_28', '进入权限管理页', 7, '/sys/aclModule/acl.page', 1, 1, 1, '', 'admin', '2020-10-25 20:23:06', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (19, '20201025202338_29', '进入角色管理页', 8, '/sys/role/role.page', 1, 1, 1, '', 'admin', '2020-10-25 20:23:39', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (20, '20201025202421_66', '进入用户管理页', 9, '/sys/dept/dept.page', 1, 1, 1, 'user', 'admin', '2020-10-25 21:15:58', '192.168.0.113');
INSERT INTO `sys_acl` VALUES (21, '20201028190625_45', '进入权限更新记录页面', 11, '/sys/log/log.page', 1, 1, 1, '', 'admin', '2020-10-28 19:07:06', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` int(0) NOT NULL DEFAULT 0 COMMENT '上级权限模块的id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(0) NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态 1：正常，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
INSERT INTO `sys_acl_module` VALUES (1, '产品管理', 0, '0', 1, 1, '', '青霖', '2020-10-24 15:32:34', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (2, '订单管理', 0, '0', 1, 1, '', 'admin', '2020-10-22 15:55:47', '220.165.241.80');
INSERT INTO `sys_acl_module` VALUES (3, '公告管理', 0, '0', 1, 1, '', 'admin', '2020-10-25 21:16:42', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (4, '出售中产品管理', 1, '0.1', 1, 1, '', 'admin', '2020-10-22 16:11:41', '220.165.241.80');
INSERT INTO `sys_acl_module` VALUES (5, '下架产品管理', 1, '0.1', 1, 1, '', 'admin', '2020-10-22 16:00:49', '220.165.241.80');
INSERT INTO `sys_acl_module` VALUES (6, '权限管理', 0, '0', 4, 1, '', 'admin', '2020-10-25 20:20:57', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (7, '权限管理', 6, '0.6', 1, 1, '', 'admin', '2020-10-25 20:21:28', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (8, '角色管理', 6, '0.6', 2, 1, '', 'admin', '2020-10-25 20:21:40', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (9, '用户管理', 6, '0.6', 3, 1, '', 'admin', '2020-10-25 20:22:07', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (10, '运维管理', 0, '0', 6, 1, '', 'admin', '2020-10-28 19:04:46', '192.168.0.113');
INSERT INTO `sys_acl_module` VALUES (11, '权限更新记录管理', 6, '0.6', 4, 1, '', 'admin', '2020-10-28 19:05:32', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(0) NOT NULL DEFAULT 0 COMMENT '上级部门的id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(0) NOT NULL DEFAULT 0 COMMENT '部门在当前层级下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 0, '0', 1, '技术', '青霖', '2020-10-24 15:31:50', '192.168.0.113');
INSERT INTO `sys_dept` VALUES (2, '后端开发', 1, '0.1', 1, '后端', 'system-update', '2020-10-16 18:55:33', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (3, '前端开发', 1, '0.1', 1, '前端', 'system-update', '2020-10-15 21:46:40', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (4, 'python开发', 2, '0.1.2', 2, '', 'admin', '2020-10-20 15:36:15', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (5, 'java开发', 2, '0.1.2', 1, '', 'system-update', '2020-10-16 18:56:18', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (6, '营销部', 0, '0', 2, NULL, 'system-update', '2020-10-15 22:14:58', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (7, 'UI设计', 1, '0.1', 1, '', 'system-update', '2020-10-16 18:55:43', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (8, '市场部', 6, '0.6', 1, '', 'system', '2020-10-19 20:30:30', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (10, '产品部', 0, '0', 3, '', 'admin', '2020-10-28 18:59:10', '192.168.0.113');
INSERT INTO `sys_dept` VALUES (11, '客服部', 0, '0', 4, 'customer service', 'admin', '2020-10-29 17:52:06', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `type` int(0) NOT NULL DEFAULT 0 COMMENT '权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系',
  `target_id` int(0) NOT NULL COMMENT '基于type后指定的主键id，比如部门、用户、权限、角色等表的主键(用户角色关联表和角色权限关联表的角色id、对角色做调整)',
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '旧值',
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '新值',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '当前是否复原过，0：没有，1：复原过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (26, 1, 10, '', '{\"id\":10,\"name\":\"产品部\",\"parentId\":0,\"level\":\"0\",\"seq\":3,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 18:59:09\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 18:59:10', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (27, 1, 11, '', '{\"id\":11,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 18:59:43\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 18:59:44', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (28, 1, 11, '{\"id\":11,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 18:59:44\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":11,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"remark\":\"customer service\",\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:00:44\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:00:45', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (29, 2, 5, '', '{\"id\":5,\"username\":\"Kate\",\"telephone\":\"13133331111\",\"mail\":\"Kate@163.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:03:04\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:03:05', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (30, 2, 5, '{\"id\":5,\"username\":\"Kate\",\"telephone\":\"13133331111\",\"mail\":\"Kate@163.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:03:05\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":5,\"username\":\"Kate\",\"telephone\":\"13133331111\",\"mail\":\"Kate@163.com\",\"deptId\":1,\"status\":1,\"remark\":\"Kate\",\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:03:37\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:03:37', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (31, 3, 10, '', '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:04:13\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:04:14', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (32, 3, 10, '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:04:14\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:04:46\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:04:47', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (33, 3, 11, '', '{\"id\":11,\"name\":\"权限更新记录管理\",\"parentId\":6,\"level\":\"0.6\",\"seq\":4,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:05:31\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:05:32', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (34, 4, 21, '', '{\"id\":21,\"code\":\"20201028190625_45\",\"name\":\"进入权限更新记录页面\",\"aclModuleId\":1,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:06:25\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:06:26', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (35, 4, 21, '{\"id\":21,\"code\":\"20201028190625_45\",\"name\":\"进入权限更新记录页面\",\"aclModuleId\":1,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:06:25\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":21,\"name\":\"进入权限更新记录页面\",\"aclModuleId\":11,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:07:05\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:07:06', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (36, 5, 10, '', '{\"id\":10,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:07:42\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:07:42', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (37, 5, 10, '{\"id\":10,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:07:42\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":10,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:07:59\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-28 19:08:00', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (38, 6, 3, '[]', '[17]', 'admin', '2020-10-29 16:51:48', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (39, 6, 3, '[17]', '[15,16,17]', 'admin', '2020-10-29 16:53:17', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (40, 7, 1, '[1]', '[1,5]', 'admin', '2020-10-29 16:54:07', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (41, 2, 6, '', '{\"id\":6,\"username\":\"服务员A\",\"telephone\":\"17877778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":11,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:49:35\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:49:36', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (42, 2, 6, '{\"id\":6,\"username\":\"服务员A\",\"telephone\":\"17877778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":11,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:49:35\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":6,\"username\":\"服务员B\",\"telephone\":\"17877778888\",\"mail\":\"service@qq.com\",\"deptId\":11,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:50:05\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:50:07', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (43, 2, 6, '{\"id\":6,\"username\":\"服务员B\",\"telephone\":\"17877778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":11,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:50:05\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":6,\"username\":\"服务员A\",\"telephone\":\"17877778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":11,\"status\":1,\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:51:02\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:51:03', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (44, 1, 11, '{\"id\":11,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"remark\":\"customer service\",\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:00:45\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":11,\"name\":\"客服部A\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"remark\":\"customer service\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:51:52\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:51:53', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (45, 1, 11, '{\"id\":11,\"name\":\"客服部A\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"remark\":\"customer service\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:51:52\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":11,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"remark\":\"customer service\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:52:06\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:52:07', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (46, 5, 10, '{\"id\":10,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"admin\",\"operateTime\":\"2020-10-28 19:07:59\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":10,\"name\":\"运维管理员A\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:52:41\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:52:42', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (47, 5, 10, '{\"id\":10,\"name\":\"运维管理员A\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:52:42\",\"operateIp\":\"192.168.0.113\"}', '{\"id\":10,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"admin\",\"operateTime\":\"2020-10-29 17:52:50\",\"operateIp\":\"192.168.0.113\"}', 'admin', '2020-10-29 17:52:51', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (48, 7, 9, '[1,4]', '[1,4,2,3,5,6]', 'admin', '2020-10-29 17:53:31', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (49, 7, 9, '[1,4,2,3,5,6]', '[1,4]', 'admin', '2020-10-29 17:54:13', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (50, 6, 10, '[]', '[18,19,20,21]', 'admin', '2020-10-29 17:55:27', '192.168.0.113', 1);
INSERT INTO `sys_log` VALUES (51, 6, 10, '[18,19,20,21]', '[]', 'admin', '2020-10-29 17:56:17', '192.168.0.113', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT '角色的类型，1：管理员角色，2：其他',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '产品管理员', 1, 1, 'product', '青霖', '2020-10-24 15:35:48', '192.168.0.113');
INSERT INTO `sys_role` VALUES (2, '订单管理员', 1, 1, '', 'admin', '2020-10-24 15:24:55', '192.168.0.113');
INSERT INTO `sys_role` VALUES (3, '公告管理员', 1, 1, '', '青霖', '2020-10-24 15:36:02', '192.168.0.113');
INSERT INTO `sys_role` VALUES (9, '权限管理员', 1, 1, '', 'admin', '2020-10-26 10:47:34', '192.168.0.113');
INSERT INTO `sys_role` VALUES (10, '运维管理员', 1, 1, '运维', 'admin', '2020-10-29 17:52:51', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `acl_id` int(0) NOT NULL COMMENT '权限id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
INSERT INTO `sys_role_acl` VALUES (20, 1, 1, 'admin', '2020-10-26 10:51:48', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (21, 1, 2, 'admin', '2020-10-26 10:51:49', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (22, 1, 3, 'admin', '2020-10-26 10:51:49', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (23, 1, 4, 'admin', '2020-10-26 10:51:49', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (27, 9, 18, 'admin', '2020-10-27 19:50:13', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (28, 9, 20, 'admin', '2020-10-27 19:50:13', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (29, 9, 19, 'admin', '2020-10-27 20:00:27', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (31, 3, 15, 'admin', '2020-10-29 16:53:16', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (32, 3, 16, 'admin', '2020-10-29 16:53:16', '192.168.0.113');
INSERT INTO `sys_role_acl` VALUES (33, 3, 17, 'admin', '2020-10-29 16:53:16', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (23, 3, 1, 'admin', '2020-10-27 20:15:47', '192.168.0.113');
INSERT INTO `sys_role_user` VALUES (24, 2, 1, 'admin', '2020-10-27 20:15:53', '192.168.0.113');
INSERT INTO `sys_role_user` VALUES (25, 1, 1, 'admin', '2020-10-29 16:54:07', '192.168.0.113');
INSERT INTO `sys_role_user` VALUES (26, 1, 5, 'admin', '2020-10-29 16:54:07', '192.168.0.113');
INSERT INTO `sys_role_user` VALUES (33, 9, 1, 'admin', '2020-10-29 17:54:12', '192.168.0.113');
INSERT INTO `sys_role_user` VALUES (34, 9, 4, 'admin', '2020-10-29 17:54:12', '192.168.0.113');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名称',
  `telephone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `dept_id` int(0) NOT NULL DEFAULT 0 COMMENT '用户所在部门的id',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结，2：删除(不允许恢复正常)',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '18688886666', 'admin@qq.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, 'admin', '青霖', '2020-10-24 15:32:09', '192.168.0.113');
INSERT INTO `sys_user` VALUES (2, 'past', '14744447777', 'past@sohu.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, 'past', '青霖', '2020-10-20 17:46:02', '116.52.177.138');
INSERT INTO `sys_user` VALUES (3, '青霖', '110', '88888@qq.com', '25D55AD283AA400AF464C76D713C07AD', 5, 1, '雨', 'admin', '2020-10-22 22:43:56', '192.168.0.113');
INSERT INTO `sys_user` VALUES (4, 'jimmy', '13433334444', 'jimmy@qq.com', '25D55AD283AA400AF464C76D713C07AD', 2, 1, '', 'admin', '2020-10-27 15:34:27', '192.168.0.113');
INSERT INTO `sys_user` VALUES (5, 'Kate', '13133331111', 'Kate@163.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, 'Kate', 'admin', '2020-10-28 19:03:37', '192.168.0.113');
INSERT INTO `sys_user` VALUES (6, '服务员A', '17877778888', 'service@qq.com', '25D55AD283AA400AF464C76D713C07AD', 11, 1, '', 'admin', '2020-10-29 17:51:03', '192.168.0.113');

SET FOREIGN_KEY_CHECKS = 1;
