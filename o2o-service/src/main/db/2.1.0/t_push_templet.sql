DROP TABLE IF EXISTS `t_push_templet`;

CREATE TABLE `t_push_templet` (
  `templet_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `templet_content` TEXT COMMENT '模板内容',
  `templet_title` VARCHAR(255) NOT NULL,
  `notice_type` INT(3) NOT NULL DEFAULT '0' COMMENT '通知大类：1订单类通知,2咨询回复类通知,3卡券到账通知,4整体套装推送,5经典案例推送,6攻略推送,7营销活动类推送',
  `notice_sub_type` INT(3) NOT NULL DEFAULT '0' COMMENT '通知小类：1用户订单配送,2用户订单签收,3用户订单退款,4咨询回复,5现金券到账通知 ,6套装推送,7案例主动通知,8攻略主动通知, 9营销活动',
  `notice_way` SMALLINT(1) NOT NULL DEFAULT '0' COMMENT '提醒方式：1 短信,2APP推送(消息盒子)',
  `notice_status` SMALLINT(1) NOT NULL DEFAULT '0' COMMENT '通知状态:1生效0失效',
  `to_url` VARCHAR(255) DEFAULT NULL COMMENT '跳转后URL',
  `photo_url` VARCHAR(255) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`templet_id`)
) ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_push_templet` */

INSERT  INTO `t_push_templet`(`templet_id`,`templet_content`,`templet_title`,`notice_type`,`notice_sub_type`,`notice_way`,`notice_status`,`to_url`,`photo_url`) VALUES (1,'尊敬的客户，您订购的[orderType][productName]已经出库配送，请耐心等待保持电话畅通。有任何问题可以咨询艾佳客服4009-699-360。','',1,1,1,1,'',NULL),(2,'您订购的[orderType][productName]已经出库配送，请耐心等待。','您的商品已出库',1,1,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_chuku.png'),(3,'尊敬的客户，您订购的[orderType][productName]已经完成签收，请耐心等待保持电话畅通。有任何问题可以咨询艾佳客服4009-699-360。','',1,2,1,1,'',NULL),(4,'您订购的[orderType][productName]已经完成签收。','您的商品已签收',1,2,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_qianshou.png'),(5,'尊敬的客户，您的订单[orderType][productName]已退款金额[cashAmount]元，现金券 [couponAmount]元至付款账户。','',1,3,1,1,'',NULL),(6,'您订购的[orderType][productName]已退款金额[cashAmount]元，现金券 [couponAmount]元至付款账户。','订单退款成功',1,3,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_dingdan.png'),(7,'您的咨询已收到回复。点击查看。[consultQuestion]','收到咨询回复',2,4,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_zixun.png'),(9,'您的[couponAmount]元[couponType]已经到账。','收到一笔现金券',3,5,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_quan.png'),(10,'[productType][productName]上新，等您来看~','精美套装',4,6,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_suit.png'),(11,'[caseName]','案例赏析',5,7,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_anli.png'),(12,'[strategyType]：[strategyName]','攻略指南',6,8,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_gonglue.png'),(13,'','',7,9,2,1,'',NULL);
