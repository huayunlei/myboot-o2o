

insert  into `t_dictionary`(`idt_dic`,`dic_key`,`dic_value`) values (4,'ACTION_TYPE_HEADIMG','http://7vilis.com2.z0.glb.qiniucdn.com/banner.png');

/* Create table for free design */
CREATE TABLE `t_free_design` (
  `id` bigint(20) NOT NULL,
  `mobile` varchar(11) DEFAULT NULL COMMENT '�ֻ�����',
  `style` varchar(255) DEFAULT NULL COMMENT '���',
  `budget` varchar(64) DEFAULT NULL COMMENT 'ȫ��Ԥ��',
  `cityCode` varchar(6) DEFAULT NULL COMMENT '������������',
  `city` varchar(64) DEFAULT NULL COMMENT '������Ϣ',
  `suitId` bigint(20) DEFAULT NULL COMMENT '��װid',
  `roomId` bigint(20) DEFAULT NULL COMMENT '�ռ�id',
  `appellation` varchar(64) DEFAULT NULL COMMENT '�û���ν',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

