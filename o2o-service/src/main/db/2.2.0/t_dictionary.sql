/*Table structure for table `t_dictionary` */

DROP TABLE IF EXISTS `t_dictionary`;

CREATE TABLE `t_dictionary` (
  `idt_dic` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `dic_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '配置key',
  `dic_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '配置value',
  PRIMARY KEY (`idt_dic`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `t_dictionary` */

insert  into `t_dictionary`(`idt_dic`,`dic_key`,`dic_value`) values (1,'CASH_HELP_DESC','<question>现金券是什么? <answer> 本券是艾佳生活发行和认可的购物券，您可以在艾佳生活消费时，选择现金券额度抵扣相应面值的金额。 <question>现金券的余额 <answer>·如果账户中有多笔现金券领取记录，将被累计充值到现金券余额中。 ·如果您的订单未支付，订单中所占的现金券额度将暂时冻结，支付订单后，额度将被扣除；取消订单后，额度恢复。 <question> 现金券一次用不完怎么办 <answer>现金券额度不设有效期，无消费限制，只要有剩余额度，下次可以继续使用。 <question> 产生退款时的办法 <answer>如果产生退款，使用现金券的那部分额度将被退款至现金券余额，其余部分退款至付款账户。 <question>现金券找零兑现吗 <answer>现金券不找零，不兑现。在付款时将被优先使用。')
,(2,'ACTION_TYPE_SHOPPING','点击到店大礼'),(3,'ACTION_TYPE_APPLYING','预约到店成功');
