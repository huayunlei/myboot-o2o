DROP TABLE IF EXISTS `t_push_templet`;

CREATE TABLE `t_push_templet` (
  `templet_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `templet_content` TEXT COMMENT 'ģ������',
  `templet_title` VARCHAR(255) NOT NULL,
  `notice_type` INT(3) NOT NULL DEFAULT '0' COMMENT '֪ͨ���ࣺ1������֪ͨ,2��ѯ�ظ���֪ͨ,3��ȯ����֪ͨ,4������װ����,5���䰸������,6��������,7Ӫ���������',
  `notice_sub_type` INT(3) NOT NULL DEFAULT '0' COMMENT '֪ͨС�ࣺ1�û���������,2�û�����ǩ��,3�û������˿�,4��ѯ�ظ�,5�ֽ�ȯ����֪ͨ ,6��װ����,7��������֪ͨ,8��������֪ͨ, 9Ӫ���',
  `notice_way` SMALLINT(1) NOT NULL DEFAULT '0' COMMENT '���ѷ�ʽ��1 ����,2APP����(��Ϣ����)',
  `notice_status` SMALLINT(1) NOT NULL DEFAULT '0' COMMENT '֪ͨ״̬:1��Ч0ʧЧ',
  `to_url` VARCHAR(255) DEFAULT NULL COMMENT '��ת��URL',
  `photo_url` VARCHAR(255) DEFAULT NULL COMMENT 'ͼƬ',
  PRIMARY KEY (`templet_id`)
) ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_push_templet` */

INSERT  INTO `t_push_templet`(`templet_id`,`templet_content`,`templet_title`,`notice_type`,`notice_sub_type`,`notice_way`,`notice_status`,`to_url`,`photo_url`) VALUES (1,'�𾴵Ŀͻ�����������[orderType][productName]�Ѿ��������ͣ������ĵȴ����ֵ绰��ͨ�����κ����������ѯ���ѿͷ�4009-699-360��','',1,1,1,1,'',NULL),(2,'��������[orderType][productName]�Ѿ��������ͣ������ĵȴ���','������Ʒ�ѳ���',1,1,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_chuku.png'),(3,'�𾴵Ŀͻ�����������[orderType][productName]�Ѿ����ǩ�գ������ĵȴ����ֵ绰��ͨ�����κ����������ѯ���ѿͷ�4009-699-360��','',1,2,1,1,'',NULL),(4,'��������[orderType][productName]�Ѿ����ǩ�ա�','������Ʒ��ǩ��',1,2,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_qianshou.png'),(5,'�𾴵Ŀͻ������Ķ���[orderType][productName]���˿���[cashAmount]Ԫ���ֽ�ȯ [couponAmount]Ԫ�������˻���','',1,3,1,1,'',NULL),(6,'��������[orderType][productName]���˿���[cashAmount]Ԫ���ֽ�ȯ [couponAmount]Ԫ�������˻���','�����˿�ɹ�',1,3,2,1,'[http]/o2o-web/my/orderDetails?fromApp[eq]1&orderId[eq][orderId]&islogin[eq]true','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_dingdan.png'),(7,'������ѯ���յ��ظ�������鿴��[consultQuestion]','�յ���ѯ�ظ�',2,4,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_zixun.png'),(9,'����[couponAmount]Ԫ[couponType]�Ѿ����ˡ�','�յ�һ���ֽ�ȯ',3,5,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_quan.png'),(10,'[productType][productName]���£���������~','������װ',4,6,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_suit.png'),(11,'[caseName]','��������',5,7,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_anli.png'),(12,'[strategyType]��[strategyName]','����ָ��',6,8,2,1,'native://id[eq][id]','http://7xidpx.com2.z0.glb.qiniucdn.com/jpush_gonglue.png'),(13,'','',7,9,2,1,'',NULL);
