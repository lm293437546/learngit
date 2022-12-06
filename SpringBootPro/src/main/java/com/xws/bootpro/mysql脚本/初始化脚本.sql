-- 用户信息表
DROP TABLE IF EXISTS `pa_user`;
create table `pa_user`(
  `userCode` VARCHAR(50) not null COMMENT '登录用户名',
  `userName` VARCHAR(50) COMMENT '用户名称',
  `passWord` VARCHAR(512) COMMENT '密码',
  `state` int(1) COMMENT '状态：0-有效，1-无效  2-锁定',
	`userLevel` int(11) COMMENT '级别：0-普通用户，1-超级用户',
	`createTime` datetime COMMENT '创建时间',
	`loginErrorCount` int(1) COMMENT '登陆错误次数',
	`lastLoginErrorTime` datetime COMMENT '最后登陆错误时间',
	`ischange` int(1) COMMENT '是否强制修改密码：0-否，1-是',
	PRIMARY KEY (`userCode`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户信息表';
insert into pa_user values ('admin','超级管理员','5fd4b2b3db3592e8f757f83247808a4a4e78287fcc411f17c331022f69f613b7e249beaab882778ada40d2aea192568594513ba8d243060f77f7274cbd9e6052f48cda8b8c9ccae6d075a745f5568edaf3f0d60895b99267a8afccface9381fb8dec63bf882539e4c2ef694c791486a2d35f9165ed11bba3508570fc9e44866dc1824af2e18e0427e145507137ea5d32b63a5344a25194872d5e4c8f66fd9efcfb54fd91df0ad75c35f1b3567b69206ec779f8e9e7a068c17c48940b9ea69b31d78381f7e610353ef279e8558bcb5747f57691646e8acb531b417ccc6bdf6793fa21a542f4816e96cc0284678db91f3314b78c8ed4bcbdd826ddcee1581556bc',0,1,now(),null,null,0);

-- 用户登录session信息表
DROP TABLE IF EXISTS `pa_user_session`;
create table `pa_user_session`(
  `userCode` VARCHAR(50) not null COMMENT '登录用户名',
  `sessionid` VARCHAR(200) COMMENT '当前登录session',
	PRIMARY KEY (`userCode`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户登录session信息表';
insert into pa_user_session values ('admin',null);


-- 角色信息表
DROP TABLE IF EXISTS `pa_role`;
create table `pa_role`(
	`roleId` int(11) not null COMMENT '角色id',
	`roleName` VARCHAR(100) COMMENT '角色名称',
	`createTime` datetime COMMENT '创建时间',
	`remark` VARCHAR(200) COMMENT '备注',
	`reserve` VARCHAR(200) COMMENT '预留字段',
	PRIMARY KEY (`roleId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色信息表';
insert into pa_role values (0,'管理员',now(),'拥有所有权限',null);

-- 菜单信息表
DROP TABLE IF EXISTS `pa_menu`;
create table `pa_menu`(
	`menuId` int(11) not null COMMENT '菜单id',
	`parentId` int(11) COMMENT '父菜单id',
	`menuName` VARCHAR(100) COMMENT '菜单名称',
	`addr` VARCHAR(500) COMMENT '菜单地址',
	`sort` int(11) COMMENT '排序',
	`icon` VARCHAR(30) COMMENT '图标',
	`createTime` datetime COMMENT '创建时间',
	`remark` VARCHAR(200) COMMENT '备注',
	`reserve` VARCHAR(200) COMMENT '预留字段',
	PRIMARY KEY (`menuId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '菜单信息表';
insert into pa_menu values(1,0,'顶级菜单',null,1,'',now(),'顶级菜单',null);
insert into pa_menu values(2,1,'系统设置',null,2,'',now(),'一级菜单',null);
insert into pa_menu values(3,2,'用户管理','../../ModulPage/systemManage/userManage/userList.jsp',1,'',now(),'二级菜单',null);
insert into pa_menu values(4,2,'角色管理','../../ModulPage/systemManage/roleManage/roleList.jsp',2,'',now(),'二级菜单',null);
insert into pa_menu values(5,2,'菜单管理','../../ModulPage/systemManage/menuManage/menuList.jsp',3,'',now(),'二级菜单',null);
insert into pa_menu values(6,2,'登录信息','../../ModulPage/systemManage/logManage/logList.jsp',4,'',now(),'二级菜单',null);
insert into pa_menu values(7,1,'商品处理',null,1,'',now(),'一级菜单',null);
insert into pa_menu values(8,7,'商品类型管理','../../ModulPage/waresManage/waresTypeManage/waresTypeList.jsp',1,'',now(),'二级菜单',null);
insert into pa_menu values(9,7,'商品浏览信息','../../ModulPage/waresManage/waresActionManage/waresActionList.jsp',2,'',now(),'二级菜单',null);
insert into pa_menu values(10,7,'交易记录','../../ModulPage/waresManage/waresBuysManage/waresBuysList.jsp',3,'',now(),'二级菜单',null);

-- 用户和角色关联表
DROP TABLE IF EXISTS `pa_user_role`;
create table `pa_user_role`(
	`userCode` VARCHAR(50) COMMENT '用户code',
	`roleId` int(11) COMMENT '角色ID',
	PRIMARY KEY (`userCode`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户和角色关联表';
insert into pa_user_role values('admin',0);

-- 菜单和角色关联表
DROP TABLE IF EXISTS `pa_menu_role`;
create table `pa_menu_role`(
	`roleId` int(11) COMMENT '角色id',
	`menuId` int(11) COMMENT '菜单id'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '菜单和角色关联表';
insert into pa_menu_role values (0,1);
insert into pa_menu_role values (0,2);
insert into pa_menu_role values (0,3);
insert into pa_menu_role values (0,4);
insert into pa_menu_role values (0,5);
insert into pa_menu_role values (0,6);
insert into pa_menu_role values (0,7);
insert into pa_menu_role values (0,8);
insert into pa_menu_role values (0,9);
insert into pa_menu_role values (0,10);

-- 用户登录记录表
DROP TABLE IF EXISTS `pa_user_log`;
create table `pa_user_log`(
  `userCode` VARCHAR(50) not null COMMENT '用户id',
  `userName` VARCHAR(100) COMMENT '用户名称',
  `ip` VARCHAR(100) COMMENT '用户ip',
  `addr` varchar(100) COMMENT '用户地址',
	`logtime` datetime COMMENT '登录时间',
  `reserve1` varchar(100) COMMENT '预留字段1',
	`reserve2` VARCHAR(100) COMMENT '预留字段2',
  `reserve3` VARCHAR(100) COMMENT '预留字段3'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户登录记录表';

-- 用户操作记录表
DROP TABLE IF EXISTS `pa_user_action`;
create table `pa_user_action`(
  `userCode` VARCHAR(50) not null COMMENT '用户id',
  `requestUrl` varchar(300) COMMENT '操作url',
	`actiontime` datetime COMMENT '操作时间',
  `reserve1` varchar(100) COMMENT '预留字段1',
	`reserve2` VARCHAR(100) COMMENT '预留字段2',
  `reserve3` VARCHAR(100) COMMENT '预留字段3'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户操作记录表';

-- 商品类型表
DROP TABLE IF EXISTS `pa_wares_type`;
create table `pa_wares_type`(
  `warescode` VARCHAR(50) COMMENT '商品code',
  `waresname` varchar(100) COMMENT '商品名称',
  `reserve1` varchar(100) COMMENT '预留字段1',
	`reserve2` int(11) COMMENT '序号',
  `reserve3` VARCHAR(100) COMMENT '预留字段3',
	PRIMARY KEY (`warescode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品类型表';
insert into pa_wares_type values ('A1','学生选课系统',null,1,null);
insert into pa_wares_type values ('A2','快递收发系统',null,2,null);
insert into pa_wares_type values ('A3','酒店管理系统',null,3,null);
insert into pa_wares_type values ('A4','二手车交易管理系统',null,4,null);
insert into pa_wares_type values ('A5','人力资源管理系统',null,5,null);
insert into pa_wares_type values ('A6','疫情信息管理系统',null,6,null);
insert into pa_wares_type values ('A7','共享单车管理系统',null,7,null);
insert into pa_wares_type values ('A8','药店管理系统',null,8,null);
insert into pa_wares_type values ('A9','堂食点餐管理系统',null,9,null);
insert into pa_wares_type values ('A10','医院挂号管理系统',null,10,null);
insert into pa_wares_type values ('A11','房屋租赁管理系统',null,11,null);
insert into pa_wares_type values ('A12','城市公交管理系统',null,12,null);
insert into pa_wares_type values ('A13','洗衣店管理系统',null,13,null);
insert into pa_wares_type values ('A14','旅游管理系统',null,14,null);
insert into pa_wares_type values ('A15','花店销售系统',null,15,null);

-- 商品浏览记录表
DROP TABLE IF EXISTS `pa_wares_action`;
create table `pa_wares_action`(
  `userip` VARCHAR(100) COMMENT '用户ip',
  `useraddr` varchar(100) COMMENT '用户地址',
  `warescode` varchar(50) COMMENT '商品code',
	`actiontime` datetime COMMENT '浏览时间',
  `reserve1` varchar(100) COMMENT '预留字段1',
	`reserve2` VARCHAR(100) COMMENT '预留字段2',
  `reserve3` VARCHAR(100) COMMENT '预留字段3'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品浏览记录表';

-- 成交记录表
DROP TABLE IF EXISTS `pa_buys`;
create table `pa_buys`(
	`buyno` VARCHAR(50) COMMENT '购买编号',
  `buyer` VARCHAR(100) COMMENT '购买人',
  `warescode` varchar(50) COMMENT '商品编码',
	`buytime` DATE COMMENT '购买时间',
	`gmv` int(11) COMMENT '成交金额',
  `reserve1` varchar(100) COMMENT '预留字段1',
	`reserve2` VARCHAR(100) COMMENT '预留字段2',
  `reserve3` VARCHAR(100) COMMENT '预留字段3',
	PRIMARY KEY (`buyno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '成交记录表';

-- 系统常量配置项表
DROP TABLE IF EXISTS `pa_cfg_system`;
create table `pa_cfg_system`(
	`fldName` VARCHAR(100) COMMENT '栏位名称',
  `fldVal` VARCHAR(2000) COMMENT '栏位常量值',
  `fldDesc` varchar(256) COMMENT '栏位说明',
	PRIMARY KEY (`fldName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '系统常量配置项表';
insert into pa_cfg_system values ('SESSION.MAX.INTERVAL','30','session有效时间(单位：分钟)');
insert into pa_cfg_system values ('RSA.PUBLICKEY','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rqGUdSLB3+31bMDfdEVlUZYEjQm4WjCN4XBXybKWer/16/1FQ5XNCxdNrYx5p+0t1n7Q5LzzI16pSH3P+S/N5aOGf5jU6Xfv5C0AMKmTgVB0Q79TUvkB41cYNrtUZsGr8ZzjTaRthiAClp7y0bLkR93k5Fy6qxkBHCh2IGExzCpiJxEuMIOr0PpizOCZAbLJLuXXt9eQPrgday7e1lj4tfk6czogp6ui7xAr7TCZoArwyE5yrthYtqOb2ClCPqpK3aw31NF00iYCGedDxOQDXYu/b4KoBwrylQqxtIhksLuO4dK48HufN2Mu9WCh2/WCAX1fJPS5PqPRAg+tWZpAQIDAQAB','RSA加密公钥');
insert into pa_cfg_system values ('RSA.PRIVATEKEY','MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDiuoZR1IsHf7fVswN90RWVRlgSNCbhaMI3hcFfJspZ6v/Xr/UVDlc0LF02tjHmn7S3WftDkvPMjXqlIfc/5L83lo4Z/mNTpd+/kLQAwqZOBUHRDv1NS+QHjVxg2u1RmwavxnONNpG2GIAKWnvLRsuRH3eTkXLqrGQEcKHYgYTHMKmInES4wg6vQ+mLM4JkBssku5de315A+uB1rLt7WWPi1+TpzOiCnq6LvECvtMJmgCvDITnKu2Fi2o5vYKUI+qkrdrDfU0XTSJgIZ50PE5ANdi79vgqgHCvKVCrG0iGSwu47h0rjwe583Yy71YKHb9YIBfV8k9Lk+o9ECD61ZmkBAgMBAAECggEAbxDAS8W6dWnzPBP9Wdzanc1fx0sU4MbYnSpAl8QAzBt6SMZBYJct0LkK0Ipf14HUnRzPIUMTetizg8EnxXzgzvJJmiIrtgZDnSvdgaHYpc+ddjPJkdvjUG/HGQslxhwTIngskrhwmKtXzbqVefasMeMgSIGvsZXRSPuDzPNlf81CrBhrpwwQ//C6UIyIMGDMOELXNgitKjUI6gg7yWqby4A7WxlztUQdbty1ReJKphl6bHezwBADBTizu0XiXKBezy2MvcHtZ/GuRR+GhCpC5fDBfZeNFq/uIejmAT1Equ5jxR6h6ms4QY5S3zZayRu7DNBLZdc6xXzmn3UHGtpgAQKBgQD1NwkUkQW/BFyFh/e1ARavqUYWF/C8K6zV+wzPRBxd1hXZnSZAlJYWpbOKlaz9OwV3g3oFPQ/Bv5XKQgQ5MVJ63xd5V83HhffaYwQJVuMORiz77p4Opem3OhvF98D/D0Et9dxU3vYwkk4uvZqPR84w1g4S8mCyJL34X04ivnEQ4QKBgQDss1gukIyYClLtWRuUw2JzcaR16j4HjetUKr9DJuuVJuGAx2ne/j0CaJm+LsQ1ohnMgZkKpLu9EXkBQkWqh9Sd8Gl0zsnamFfFsYbz9AVTp52aCJwae/mkM6ozWjyH0JuY8qeGHjVMIsXPkE5DcLMnte29fb078+lOpTljGC68IQKBgC/WMbZKaFWQU0BVexRbhwJzwlFzECqVVp1T4XbZsbL4ncCbMKgulG0MnE7vzhSEnBdplbdJ5zYD2wPfBxXlMlL4DItGfsVqtCRtZo8v3RGezQ3Eyh7PbR2qf1qKb6MSZcPCj94atOpa0Fe781f6SRYr3AkEMarvEgRPC14pysLhAoGAFYENp91WCJIXipyn1tIRZa+TY2sOi50nHhRsH+uvR3Oq1QpI4gty+38JSK/y/3Rkp6G2h7MDo1+tAKJGtgF1HYwz6HrI9+UTRFCmlA89VKZLuSzDEdzlhzdyZQvzp9sZ58FT2ulvqiUWl47irVnVzOvIV4jO4l18erqkBg6yYEECgYBmgsxoz9qP5JOQNe4PomxV3S6EM8Up8Rtq81SEe5CXfV4dUxTZOfxA0KZ4XinlQNCbbCCflaeX9TkY0SIZjSVgW/Clk0RBmVs3Suhc9vtKu07oUXNUjFSrKCBlyBig4txmwo6OVo6FmTiW16joRQg/0iV45lV4EG/yHlnocYi+og==','RSA加密私钥');
insert into pa_cfg_system values ('DEFAULT.PASSWORD','123456','默认密码');
insert into pa_cfg_system values ('USER.LOCK.NUMBER','5','用户输错密码次数将锁定');
insert into pa_cfg_system values ('USER.UNLOCK.DATE','30','自动解锁时间：分钟');

-- 定时任务配置表
DROP TABLE IF EXISTS `pa_scheduled`;
CREATE TABLE `pa_scheduled` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_key` varchar(127) NOT NULL COMMENT '任务key值（使用bean名称）',
  `name` varchar(127) DEFAULT NULL COMMENT '任务名称',
  `cron` varchar(63) NOT NULL COMMENT '任务表达式',
  `remark` varchar(200) COMMENT '备注',
  `status` int(2) DEFAULT '0' COMMENT '状态(0.禁用; 1.启用)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqu_task_key` (`task_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='定时任务配置表';
insert into pa_scheduled values (1,'scheduledTaskJob01','测试01','0 */1 * * * ?','每隔1分钟执行一次',1,now(),null);