
## 工具包功能说明

该工具包主要完成在项目内根据提供的实体类包，自动生成spring mybatis，所需要的service层接口与实现，数据库表的创建包括主键，描述，长度等的设置，数据库操作接口与对应xml文件，支持jar与maven 

--->演示视频如下

<!-- more -->
<The rest of contents | 余下全文>

![演示图片gif](http://sharehe.cn/img/tool/%E6%BC%94%E7%A4%BA.gif)

## jar包下载地址

该jar包需要依赖mysql--connector

[点击下载jar包](http://sharehe.cn/file/autoCode-1.1.2-RELEASE.jar)

[点击下载mysql连接驱动](http://sharehe.cn/file/mysql-connector-java-5.1.7-bin.jar)

## maven坐标

是在惭愧 博主在上传到maven中央厂库的过程中出现了一些事故，目前还在解决中 所以使用maven管理项目需要自动把[点击下载项目包](http://sharehe.cn/file/cn.zip)放入本地厂库中 maven默认的厂库位置为  C:\Users\Administrator\.m2\repository

坐标为下

```
<dependency>
   <groupId>cn.sharehe.autoCode</groupId>
   <artifactId>autoCode</artifactId>
   <version>1.1.2.RELEASE</version>
</dependency>
```





## 开始使用

```
整个工具包的启动 需要StartRun startRun= new StartRun() 
所有的控制都是在该对象进行设置
```

## 设置工具包功能与执行线程池

所有的功能默认为--false

```
startRun.setOpen()  //进入工具包设置
.setScan(true)		//是否开启扫描 该扫描是扫描实体类包
.setMapper(true)	//是否开启生成mybatis xml文件 
.setDao(true)		//是否生成数据库操作接口
.setService(true)	//是否生成service的接口
.setServiceImp(true)//是否生成service的实现类  与是否生成接口没有关联
.setThreadPool(10)	//设置线程池大小  默认为10
.setCreateTab(true);//是否创建数据库表  若需要实现该功能需要设置数据库属性与数据库连接池等
```

## 设置程序源代码绝对路径

```
startRun.setRootPath("");
该属性若不知如何设置 可先开始导入 在控制台会打印出如下语句
//  代码目录为->D:\IdeaYuanma1\vote/src/..
截取D:\IdeaYuanma1\vote/src/设置即可 注意后面的/
```

## 设置生成代码的对应位置

```

startRun.setPageName()		 // 进入包名的设置
		.setRootPackage("cn.wugui.automatic.")  //设置所有包的父包 没有为空 注意注意最后要有一个点
		.setBeans("beans")		// 设置实体类的包名 所有生成代码都根据该包下的实体类生成该属性值如果有.则表明只添加实体包下的一个类不支持子包
		.setMapper("")// 该设置为mybatis xml的生成位置 如果为maven项目建议不设置该属性 注意是不设置，不是设置为空
		.setMiddleMapp("")	// maven建议不用设置，没使用maven建议设置为"src/"
		.setMiddle("")		//  maven建议不用设置，没使用maven建议设置为"src/"
		.setDao("dao")			// 设置dao接口的父包
		.setService("service")		// 设置service接口父包
		.setServiceImp("service.imp");	//设置service实现类父包
```



## 设置类名格式与方法格式

在设置格式中 存在{}符号 该符号表示生成的文件名将替换为实体类的类名

在下面的配置中的值为默认值 若相同则可以不用设置

```
startRun.setClassFormat(ClassNameConfigure.DAO,"{}Dao.java")// dao的类名格式
        .setClassFormat(ClassNameConfigure.SERVICE,"I{}Service.java")  //service接口的类名格式
        .setClassFormat(ClassNameConfigure.SERVICEIMP,"{}ServiceImp.java") //servoce实现的类格式
        .setClassFormat(ClassNameConfigure.MAPPER,"{}Mapper.xml");		//mybatis 文件名格式
        
        //注意文件后缀
```



## 设置方法名格式

该功能中 xml会根据方法中最后一个参数生成对应的parameterType

在方法格式中出现//字符表示未该方法写的注释 /这里写注释/

在下面的配置中的值为默认值 若相同则可以不用设置

```
startRun.setMethodFormat(MethodNameConfigure.SELECTALL,"/查询全部数据/ public List<{}> qry{}All({} data)") 
        .setMethodFormat(MethodNameConfigure.DELETE,"/删除数据/ public boolean del{}(String id)")
        .setMethodFormat(MethodNameConfigure.INSERT,"/添加数据/ public boolean add{}({} data)")
        .setMethodFormat(MethodNameConfigure.SELECTBYID,"/根据id查询一条数据/ public {} qry{}ById(String id)")
        .setMethodFormat(MethodNameConfigure.UPDATE,"/更新数据/ public boolean edit{}({} data)")
        .setMethodFormat(20,""); //可以设置自己的方法 但是导入到xml中的方法只有以上5个方法
```



## 创建表操作

若开启了数据库表的创建则需要设置一下属性

```
startRun.setJdbcField()
		.setConcurrencyPool(5)   //设置数据库连接词
		.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8")  //url
		.setUser("user")		//用户名
		.setPassword("pass");		//密码
```

在系统中我们提供了以下几类非必须注解用在实体类上作用于sql语句 

```
@TableName("")作用于类 表明实体类对应的表名 若不写表名默认为实体类名首字母小写
@NotNull 作用于属性 表明这个属性不可为空
@PrimaryKey 作用于属性 表明该属性为主键
@LengthAndNote(length=32,note="")  作用于属性 length数据类型长度，mote属性说明
@AutoIncrement 作用于int类型表明该属性自动递增
```

设置java数据类型与mysql数据类型对象关系

```
startRun.setTypeJavaToSql("String","varchar") //前面为java数据类型后面为mysql数据类型
		.setTypeJavaToSql("","")
		
以下为默认对应关系

		TYPEMAP.put("String", "VARCHAR");
        TYPEMAP.put("byte", "BLOB");
        TYPEMAP.put("Long", "INTEGER");
        TYPEMAP.put("Integer", "int");
        TYPEMAP.put("Boolean", "BIT");
        TYPEMAP.put("Float", "FLOAT");
        TYPEMAP.put("Double", "DOUBLE");
        TYPEMAP.put("float", "FLOAT");
        TYPEMAP.put("double", "DOUBLE");
        TYPEMAP.put("int", "INT");
        TYPEMAP.put("boolean", "INT");
        TYPEMAP.put("long", "INT");
```



## 开启导入

```
start.run();
```