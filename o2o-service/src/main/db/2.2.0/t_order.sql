alter table t_order add voucher_pay  decimal(18,2) DEFAULT '0.00' COMMENT '����ȯ���';
alter table t_order add fid_voucher_detail bigint(20) DEFAULT NULL COMMENT '��������ȯid';
