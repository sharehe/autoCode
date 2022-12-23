package cn.sharehe.handle;

import cn.sharehe.handle.configure.*;

/**
 * 项目启动
 */
public class StartRun {
    private OpenConfigure openConfigure = null;
    private PackageNameConfigure packageNameConfigure = null;
    private JdbcConfigure jdbcConfigure = null;

    public StartRun() {
        openConfigure = OpenConfigure.getInstance();
        packageNameConfigure = PackageNameConfigure.getInstance();
        jdbcConfigure = JdbcConfigure.getInstance();
    }

    /**
     * 开始导入
     * 位于项目根路径下
     */
    public void run() {
        if (openConfigure.isScan()) {
            System.out.println("启动自动导入");
            AutoMaticApp.getInstance().startScan();
        } else {
            System.out.println("未启动自动导入");
        }
    }

    /**
     * 设置开启的功能
     *
     * @return
     */
    public OpenConfigure setOpen() {
        return openConfigure;
    }

    /**
     * 自定义java数据类型与sql数据类型对应关系
     * 提供常见数据类型对应
     *
     * @param javaType
     * @param sqlType
     * @return
     */
    public StartRun setTypeJavaToSql(String javaType, String sqlType) {
        VarTypeConfigure.TYPEMAP.put(javaType, sqlType);
        return this;
    }

    /**
     * 设置各个层次的包名与项目到包的路径 如/src/main/java/
     * 所有层次都有默认名称
     *
     * @return
     */
    public PackageNameConfigure setPageName() {
        return packageNameConfigure;
    }

    /**
     * 设置dao service serviceImp阶段的方法名称格式
     *
     * @param i      用MethodNameConfigure中的常量定义
     * @param format -用{}代替实体类类名
     * @return
     */
    public StartRun setMethodFormat(Integer i, String format) {
        MethodNameConfigure.MethodType.put(i, format);
        return this;
    }

    /**
     * 设置类名格式
     *
     * @param i      使用ClassNameConfigure里面的常量
     * @param format 参照 I{}Service.java
     *               -用{}代替实体类类名
     * @return
     */
    public StartRun setClassFormat(Integer i, String format) {
        ClassNameConfigure.className.put(i, format);
        return this;
    }

    public void setRootPath(String path) {
        AutoMaticApp.codePath = path;
    }

    /**
     * 设置数据库连接参数
     *
     * @return
     */
    public JdbcConfigure setJdbcField() {
        return jdbcConfigure;
    }

    public void xiha() {
        try {
            throw new RuntimeException("");
        } catch (RuntimeException e) {

        }
    }

    public static void main(String[] args) {
        StartRun start = new StartRun();
        start.setOpen().setScan(true).setPrimaryKeyUUID(true).setService(true).setServiceImp(true).setDao(true);
        start.setJdbcField().setConcurrencyPool(5).setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8").setUser("root").setPassword("15213497317");
        start.setMethodFormat(MethodNameConfigure.SELECTALL, "public List<{}> qry{}All(Map<String,String> map)");
        start.setPageName().setMiddle("/src/main/java/").setRootPackage("cn.sharehe.handle.");
        start.setPageName().setBeans("beans");
        start.setRootPath("D:\\IdeaYuanma1\\autoCode1");
        start.run();
    }

}
