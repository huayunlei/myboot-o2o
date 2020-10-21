CREATE TABLE `t_visit_log` (
  `idt_visit_log` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mobile` varchar(11) DEFAULT '' COMMENT '访问用户的手机号码',
  `visit_type` tinyint(4) DEFAULT '0' COMMENT '用户访问类型 \r\n0 打开APP  \r\n1 访问首页主模块\r\n2 访问套装、空间、单品详情\r\n3 访问搜索页\r\n4 搜索内容\r\n5 在商品详情的页面点击 到店有礼\r\n6 阅读灵感\r\n7 立即预定 ',
  `action` varchar(50) DEFAULT '' COMMENT '用户操作动作',
  `target_type` tinyint(4) DEFAULT '0' COMMENT '访问的目标类型 \r\n0:未确定 1：客厅 2：主卧 3：次卧 4：儿童房 5：书房  6：餐厅 7：玄关 8：厨房 9：老人房\r\n17:单品 18: 套装  19: 更多套装 	\r\n20 案例  21 攻略  22美图',
  `target_id` bigint(20) DEFAULT '0' COMMENT '操作目标的ID 如：商品ID、案例D等',
  `search_word` varchar(50) DEFAULT '' COMMENT '搜索词',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`idt_visit_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;