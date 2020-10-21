/*Table structure for table `t_dictionary` */

DROP TABLE IF EXISTS `t_dictionary`;

CREATE TABLE `t_dictionary` (
  `idt_dic` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����������',
  `dic_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '����key',
  `dic_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '����value',
  PRIMARY KEY (`idt_dic`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `t_dictionary` */

insert  into `t_dictionary`(`idt_dic`,`dic_key`,`dic_value`) values (1,'CASH_HELP_DESC','<question>�ֽ�ȯ��ʲô? <answer> ��ȯ�ǰ�������к��ϿɵĹ���ȯ���������ڰ�����������ʱ��ѡ���ֽ�ȯ��ȵֿ���Ӧ��ֵ�Ľ� <question>�ֽ�ȯ����� <answer>������˻����ж���ֽ�ȯ��ȡ��¼�������ۼƳ�ֵ���ֽ�ȯ����С� ��������Ķ���δ֧������������ռ���ֽ�ȯ��Ƚ���ʱ���ᣬ֧�������󣬶�Ƚ����۳���ȡ�������󣬶�Ȼָ��� <question> �ֽ�ȯһ���ò�����ô�� <answer>�ֽ�ȯ��Ȳ�����Ч�ڣ����������ƣ�ֻҪ��ʣ���ȣ��´ο��Լ���ʹ�á� <question> �����˿�ʱ�İ취 <answer>��������˿ʹ���ֽ�ȯ���ǲ��ֶ�Ƚ����˿����ֽ�ȯ�����ಿ���˿��������˻��� <question>�ֽ�ȯ��������� <answer>�ֽ�ȯ�����㣬�����֡��ڸ���ʱ��������ʹ�á�')
,(2,'ACTION_TYPE_SHOPPING','����������'),(3,'ACTION_TYPE_APPLYING','ԤԼ����ɹ�');
