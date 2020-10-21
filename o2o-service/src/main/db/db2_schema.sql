CREATE DATABASE IF NOT EXISTS `aijia` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `aijia`;

DROP TABLE t_category;
CREATE TABLE t_category (
category_id bigint NOT NULL AUTO_INCREMENT,
 first_contents bigint,
 first_contents_name varchar(255),
 second_contents bigint,
 second_contents_name varchar(255),
 PRIMARY KEY (category_id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_collection;
CREATE TABLE t_collection (
collection_id bigint NOT NULL AUTO_INCREMENT,
userId bigint,
productId bigint,
create_time timestamp DEFAULT CURRENT_TIMESTAMP,
update_time timestamp NULL,
status bigint,
 PRIMARY KEY (collection_id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_composite_product;
CREATE TABLE t_composite_product (
composite_product_id bigint NOT NULL AUTO_INCREMENT,
 name varchar(255),
 picture_url_original text,
 summary varchar(255),
 price decimal,
 sale_off decimal,
 design_features varchar(420),
 experience_address varchar(255),
 create_time timestamp DEFAULT CURRENT_TIMESTAMP,
 update_time timestamp NULL,
 thum_urm_url text,
 latitude double,
 longitude double,
 PRIMARY KEY (composite_product_id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_composite_single;
CREATE TABLE t_composite_single ( composite_single_id bigint NOT NULL AUTO_INCREMENT, composite_productId bigint, single_productId bigint, product_count bigint DEFAULT '1', PRIMARY KEY (composite_single_id), CONSTRAINT relation UNIQUE (composite_productId, composite_single_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_feedback;
CREATE TABLE t_feedback ( feedback_id bigint NOT NULL AUTO_INCREMENT, content varchar(420), phone_number varchar(14), userId bigint, type bigint, feedback_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (feedback_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_log;
CREATE TABLE t_log ( l_id int NOT NULL AUTO_INCREMENT, u_id int NOT NULL COMMENT '用户id', access_token varchar(128) NOT NULL COMMENT '判断用户是否有读写权限', refresh_token varchar(128) NOT NULL COMMENT '刷新access_token', expire int NOT NULL COMMENT '过期时间，如15天', log_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (l_id), CONSTRAINT u_id_2 UNIQUE (u_id), INDEX u_id (u_id, access_token) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table t_order;
drop table t_order_details;

CREATE TABLE  t_order  (
   order_id  bigint(20) NOT NULL AUTO_INCREMENT,
   purchaser_name  varchar(255) DEFAULT NULL,
   purchaser_tel  varchar(255) DEFAULT NULL,
   order_price  decimal(10,0) DEFAULT NULL,
   order_product_amount  bigint(20) DEFAULT NULL,
   order_status  bigint(20) DEFAULT NULL,
   create_time  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   update_time  timestamp NULL DEFAULT NULL,
   userId  bigint(20) DEFAULT NULL,
   delivery_address  varchar(255) DEFAULT NULL,
   invoice_information  varchar(255) DEFAULT NULL,
   product_id  bigint(20) DEFAULT NULL,
   product_type  smallint(6) DEFAULT NULL,
   product_name  text,
   picture_url_original  text,
   additional  text,
   order_source  bigint(20) DEFAULT NULL,
   order_num  varchar(255) DEFAULT NULL,
  PRIMARY KEY ( order_id )
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


CREATE TABLE  t_order_details  (
   order_details_id  bigint(20) NOT NULL AUTO_INCREMENT,
   order_id  bigint(20) DEFAULT NULL,
   product_id  bigint(20) DEFAULT NULL,
   product_amount  bigint(20) DEFAULT NULL,
   product_price  decimal(10,0) DEFAULT NULL,
   product_price_amount  decimal(10,0) DEFAULT NULL,
  PRIMARY KEY ( order_details_id )
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;



DROP TABLE t_register;
CREATE TABLE t_register ( r_id bigint NOT NULL AUTO_INCREMENT, mobile varchar(11), password varchar(128), activate_code varchar(6) COMMENT '手机注册短信验证', register_key varchar(128), reg_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (r_id), CONSTRAINT r_id UNIQUE (r_id), CONSTRAINT mobile UNIQUE (mobile) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_shopping_cart;
CREATE TABLE t_shopping_cart ( shopping_cart_id bigint NOT NULL, userId bigint, productId bigint, cart_status bigint, create_time timestamp DEFAULT CURRENT_TIMESTAMP, update_time timestamp NULL, PRIMARY KEY (shopping_cart_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_single_product;
CREATE TABLE t_single_product ( product_id bigint NOT NULL AUTO_INCREMENT, name varchar(255), picture_url_original text, price_current decimal, price_market decimal, category_id bigint, status bigint, create_time timestamp DEFAULT CURRENT_TIMESTAMP, update_time timestamp NULL, product_model varchar(255), standard_long decimal, standard_width decimal, standard_high decimal, feature varchar(255), brand varchar(255), made_in varchar(255), delivery_city varchar(255), graphic_description varchar(7000), first_contents bigint, second_contents bigint, first_contents_name varchar(255), second_contents_name varchar(255), thum_urm_url text, PRIMARY KEY (product_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_user;
CREATE TABLE t_user ( u_id bigint NOT NULL AUTO_INCREMENT, name varchar(64), mobile varchar(11), password varchar(128), reg_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (u_id), CONSTRAINT u_id UNIQUE (u_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_wish_list;
CREATE TABLE t_wish_list ( wish_list_id bigint NOT NULL AUTO_INCREMENT, userId bigint, wish_product_name varchar(255), wish_product_brand varchar(255), wish_product_url text, wish_product_request varchar(420), submit_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (wish_list_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE t_app_version;
CREATE TABLE t_app_version ( v_id bigint NOT NULL AUTO_INCREMENT, version varchar(64), partner_value varchar(64),download text, update_content text, update_time timestamp DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (v_id) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if EXISTS t_serial;
CREATE TABLE `t_serial` (
  `sn` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cdate` date NOT NULL DEFAULT '0000-00-00',
  `oid` bigint(20) NOT NULL,
  PRIMARY KEY (`cdate`,`sn`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;