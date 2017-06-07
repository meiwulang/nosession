/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/5/19 15:23:41                           */
/*==============================================================*/


/*==============================================================*/
/* Table: T_LOGISTICS                                           */
/*==============================================================*/
create table T_LOGISTICS
(
   LOGISTICS_ID         int(11) not null auto_increment comment '物流表主键',
   ORDER_ID             int(11) not null comment '订单主键',
   ORDER_NO             varchar(18) not null comment '订单号',
   LOGISTICS_NO         varchar(20) comment '物流单号',
   LOGISTICS_NOTE_SNAPSHOT varchar(200) comment '快递单快照',
   SENDER_NAME          varchar(50) comment '送货人姓名',
   SENDER_MOBILE        varchar(11) comment '送货人手机号',
   LOGISTICS_COMPANY    varchar(100) comment '物流公司',
   DELIVERY_DATE        datetime comment '发货日期',
   CREATE_DATE          datetime default CURRENT_TIMESTAMP comment '创建时间',
   CREATE_USER_ID       varchar(16) not null comment '操作人id',
   CREATE_USER_NAME     varchar(50) comment '操作人姓名',
   primary key (LOGISTICS_ID)
);

alter table T_LOGISTICS comment '物流模块--物流信息表';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_LOGISTICS
(
   ORDER_ID,
   ORDER_NO,
   LOGISTICS_NO
);

/*==============================================================*/
/* Table: T_ORDER                                               */
/*==============================================================*/
create table T_ORDER
(
   ORDER_ID             int(11) not null auto_increment,
   ORDER_NO             varchar(20) not null comment '订单号',
   ORDER_STATUS         int(2) not null comment '订单状态： (左边订单状态，右边状态码)
            
            未付款、等待买家付款      =1
            已付预付款                          =2
            买家已付款、卖家待发货  =3
            卖家已发货、用户待收货  =4
            买家确认收货                      =5
            交易成功                              =6
            
            交易关闭--买家取消订单    =7
            交易关闭--卖家取消订单    =8
            
            退款中                                  =9
            退货退款中                          =10
            
            交易关闭--退款                    =11
            交易关闭--退货且退款        =12
            交易关闭--异常订单            =13
            
            
            
            ',
   USER_ID              varchar(16) not null comment '买家ID',
   USER_NAME            varchar(25) not null comment '买家姓名',
   USER_MOBILE          varchar(11) not null comment '买家注册手机号',
   SHOP_ID              int(11) not null default 0 comment '店铺ID, 目前只有一家店铺，默认值为0',
   BRAND_ID             varchar(16) comment '品牌主键',
   BRAND_NAME           varchar(255) comment '品牌名称',
   TRANSACTION_TYPE     int(1) not null default 0 comment '目前只存在两种交易类型:
               普通(0)  默认值
               预付款(1) ',
   TRANSACTION_AMOUNT   decimal(15,2) not null comment '交易总金额(除邮费)。 用户最终需要支付的费用= 交易总金额+邮费',
   TRANSACTION_ACTUAL_PAY_AMOUNT decimal(15,2) comment '交易实际支付金额',
   TRANSACTION_DEPOSIT  decimal(15,2) default 0 comment '交易定金',
   TRANSACTION_ACTUAL_PAY_BALANCE decimal(15,2) default 0 comment '交易余额',
   POSTAGE              decimal(15,2) comment '邮费',
   PAY_DATE             datetime comment '支付时间',
   CHANNEL              varchar(20) default '手机端' comment '订单来源的渠道。目前只有手机端',
   CREATED_DATE         datetime not null default CURRENT_TIMESTAMP comment '订单生成时间',
   MODIFIED_DATE        datetime default CURRENT_TIMESTAMP,
   DEL_FLAG             int(1) not null comment '逻辑删除标记：
                默认(0) ， 表示未删除
                用户删除(1)  , 表示用户删除订单，用户对订单不可见，商家可见
                商户删除订单(2) , 表示该订单已经删除，用户、商家都不可见',
   CONSIGNEE_NAME       varchar(50) not null comment '收货人姓名',
   CONSIGNEE_MOBILE     varchar(11) not null comment '收货人手机',
   CONSIGNEE_TELEPHONE  varchar(20) comment '收货人电话',
   CONSIGNEE_ADDRESS    varchar(120) not null comment '收货人地址',
   CONSIGNEE_PROVINCE   varchar(50) comment '收货人所在省份',
   CONSIGNEE_CITY       varchar(50) comment '收货人所在城市',
   CONSIGNEE_DISTRICT   varchar(50) comment '收货人所在地区',
   CONSIGNEE_ZIP        varchar(20) comment '收货人地区邮编',
   BUYER_COMMENTS       varchar(500) comment '买家订单留言',
   ORDER_REMARK         varchar(500) comment '订单备注。记录订单相关信息 如 交易关闭、订单异常情况的原因',

   ESTIMATE_DELIVERY_DESC varchar(50) comment '预计发货时间',
   ACTUAL_DELIVERY_DATE  datetime comment '订单发货时间',
   DEPOSIT_PROOF_DATE    datetime comment '定金凭证上传时间',
   BALANCE_PROOF_DATE    datetime comment '余款或全款凭证上传时间',
   BALANCE_DATE_COUNT   int(10)  comment '余款到期时间段,单位天',
   INVITE_CODE          varchar(12)  comment '邀请码',
   REFUNDING_AMOUNT     decimal(15,2) comment '退款金额',
   REFUNDING_EXPLAIN    varchar(500) comment '退款说明',
   APPLY_REFUND_DATE    datetime comment '申请退款时间',
   REFUNDING_PATH    varchar(500) comment '退款路径',
   TERMINAL_DATE        datetime comment '订单状态不可更改时间,包括交易成功,退款成功,取消订单成功等',


   primary key (ORDER_ID)
);

alter table T_ORDER comment '订单模块--订单主表';

/*==============================================================*/
/* Index: order_select                                          */
/*==============================================================*/
create index order_select on T_ORDER
(
   ORDER_NO,
   USER_ID,
   SHOP_ID,
   PAY_DATE,
   CREATED_DATE,
   CONSIGNEE_PROVINCE
);

/*==============================================================*/
/* Table: T_ORDER_ITEM                                          */
/*==============================================================*/
create table T_ORDER_ITEM
(
   ORDER_ITEM_ID        int(11) not null auto_increment comment '订单明细主键',
   ORDER_ID             int(11) not null comment '订单主键',
   ORDER_NO             varchar(18) not null comment '订单号',
   PRODUCT_ID           varchar(16) not null comment '商品主键',
   PRODUCT_ITEM_ID      varchar(16) not null comment '商品规格主键',
   CATEGORY_ID          varchar(16) comment '类目主键',
   BRAND_ID             varchar(16) comment '品牌主键',
   AMOUNT               decimal(15,2) comment '总金额',
   DEPOSIT              decimal(15,2) comment '定金',
   PRICE                decimal(15,2) comment '实际购买单价',
   QUANTITY             int comment '购买数量',
   UNIT                 varchar(50) comment '计量单位描述',
   PRODUCT_NAME         varchar(255) comment '商品名称',
   PRODUCT_DESC         text comment '商品描述',
   PICTURE_CODE         varchar(255) comment '商品图片',
   CATEGORY_NAME        varchar(50) comment '类目名称',
   BRAND_NAME           varchar(50) comment '品牌名称',
   PRODUCT_STANDARD_MUST varchar(50) comment '规格一(暂时字段，后会删除)',
   PRODUCT_OPTIONAL_FIRST varchar(50) comment '规格二(暂时字段，后会删除)',
   PRODUCT_OPTIONAL_SECOND varchar(50) comment '规格三(暂时字段，后会删除)',
   primary key (ORDER_ITEM_ID)
);

alter table T_ORDER_ITEM comment '订单模块--订单明细表，记录具体订单商品信息';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_ORDER_ITEM
(
   ORDER_ID,
   ORDER_NO,
   PRODUCT_ID
);

/*==============================================================*/
/* Table: T_ORDER_OPERATION_LOG                                 */
/*==============================================================*/
create table T_ORDER_OPERATION_LOG
(
   ID                   int(11) not null auto_increment comment '主键',
   ORDER_ID             int(11) not null comment '订单主键',
   ORDER_PRE_STATUS     int(2) not null comment '订单操作前状态',
   ORDER_STATUS         int(2) not null comment '订单当前状态',
   OPERATION_MSG        varchar(500) comment '操作描述',
   CREATE_TIME          datetime default CURRENT_TIMESTAMP comment '创建时间',
   OPERATOR_NAME		varchar(50) not null comment '操作人姓名',
   OPERATOR_ID			varchar(16) not null comment '操作人id',
   primary key (ID)
);

alter table T_ORDER_OPERATION_LOG comment '订单表操作记录';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_ORDER_OPERATION_LOG
(
   ORDER_ID
);

/*==============================================================*/
/* Table: T_ORDER_PAYMENT                                       */
/*==============================================================*/
create table T_ORDER_PAYMENT
(
   ID                   int(11) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: T_ORDER_PAYMENT_PROOF                                 */
/*==============================================================*/
create table T_ORDER_PAYMENT_PROOF
(
   ID                   int(11) not null auto_increment comment '主键',
   ORDER_ID             int(11) not null comment '订单主键',
   PAYMENT_USER_NAME    varchar(50) comment '付款人',
   BANK_ACCOUNT         varchar(50) comment '银行帐号',
   PAYMENT_ACCOUNT      varchar(50) comment '支付帐号',
   AMOUNT               decimal(15,2) comment '金额',
   PAYMENT_PROOF_SNAPSHOT varchar(200) comment '支付凭证快照',
   CREATE_DATE          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (ID)
);

alter table T_ORDER_PAYMENT_PROOF comment '订单模块--订单支付凭证表。 该表主要记录用户上传的支付凭证';

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create index Index_2 on T_ORDER_PAYMENT_PROOF
(
   ORDER_ID
);

/*==============================================================*/
/* Table: T_ORDER_REFUND                                        */
/*==============================================================*/
create table T_ORDER_REFUND
(
   ID                   int(11) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: 地区字典表                                                 */
/*==============================================================*/
create table 地区字典表
(
   ID                   int(11) not null,
   primary key (ID)
);

