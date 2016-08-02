# SmallJavaCrawler
java for crawler
测试网站："http://www.chinacaipu.com/"

1、配置好数据库，本篇默认是MySQL,环境需要自己安装哦！<br/>
caixi表创建SQL：<br/>
DROP TABLE IF EXISTS `caixi`;
CREATE TABLE `caixi` (
  `caixiID` varchar(36) NOT NULL,
  `caixiName` varchar(36) NOT NULL,
  `caixiSrc` varchar(100) NOT NULL,
  PRIMARY KEY (`caixiID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
<br/>
cai表创建SQL：<br/>
DROP TABLE IF EXISTS `cai`;
CREATE TABLE `cai` (
  `caiID` varchar(36) NOT NULL,
  `caixiID` varchar(36) NOT NULL,
  `caiName` varchar(60) NOT NULL,
  `caiImage` varchar(512) DEFAULT NULL,
  `caiDescrip` varchar(512) DEFAULT NULL,
  `caiDetail` text,
  `caiDetailDescrip` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`caiID`),
  KEY `caixiID` (`caixiID`),
  CONSTRAINT `cai_ibfk_1` FOREIGN KEY (`caixiID`) REFERENCES `caixi` (`caixiID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

2、本篇开发IDE是eclipse 4.2,工程结构也是从eclipse导出，可以直接导入到eclipse IDE中，
   打开 com.china.caipu.ChinaCaipuMain，直接运行即可。
   
3、执行顺序是：
      A、初始化菜系；<br/>
      B、获取菜系的所有菜；<br/>
      C、处理菜的详细；<br/>
      D、处理菜的图片;<br/>
      
4、执行结果：
  如图
  <img src="https://github.com/MichaelKoo/SmallJavaCrawler/blob/master/cai.png" alt="cai"/>
  <img src="https://github.com/MichaelKoo/SmallJavaCrawler/blob/master/caixi.png" alt="caixi"/>


