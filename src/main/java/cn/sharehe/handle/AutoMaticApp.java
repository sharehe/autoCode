package cn.sharehe.handle;

import cn.sharehe.handle.async.*;
import cn.sharehe.handle.configure.OpenConfigure;
import cn.sharehe.handle.configure.PackageNameConfigure;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class AutoMaticApp {
    OpenConfigure openConfigure = null;
    PackageNameConfigure packageNameConfigure;

    private AutoMaticApp() {
        openConfigure = OpenConfigure.getInstance();
        packageNameConfigure = PackageNameConfigure.getInstance();
    }

    private static class Instance {
        private static AutoMaticApp instance = new AutoMaticApp();
    }

    public static AutoMaticApp getInstance() {
        return Instance.instance;
    }

    //java编码位于磁盘目录
    public static String codePath;
    //项目编码位于磁盘目录
    public static String projectPath;
    //resouces位于磁盘目录
    public static String classPath;
    private ExecutorService exe;

    /**
     * 开始扫描
     */
    public void startScan() {
        addPath();
        exe = Executors.newFixedThreadPool(openConfigure.getThreadPool());
        String tem = packageNameConfigure.getBeans();
        if (tem.lastIndexOf(".") > 0) {
            String last = tem.substring(tem.lastIndexOf(".") + 1);
            scan(last, packageNameConfigure.getRootPackage() + packageNameConfigure.getBeans() + "." + last);
        } else {
            String beanPath = codePath + packageNameConfigure.getBeans();
            File beanFile = new File(beanPath);
            if (beanFile.isDirectory()) {
                String className = null;
                for (File f : beanFile.listFiles()) {
                    className = f.getName().substring(0, f.getName().lastIndexOf('.'));
                    scan(className, packageNameConfigure.getRootPackage() + packageNameConfigure.getBeans() + "." + className);
                }
                exe.shutdown();
            } else {
                throw new RuntimeException("实体类包不存在");
            }
        }
    }

    /**
     * 生成路径
     */
    private void addPath() {
        if (codePath == null) {
            //获得基于磁盘位置
            codePath = StartRun.class.getClassLoader().getResource("").getPath().substring(1);
            File rootFile = new File(codePath);
            //获取编码基于磁盘位置
            codePath = rootFile.getParentFile().getParent();
        }
        classPath = StartRun.class.getClassLoader().getResource("").getPath().substring(1);
        projectPath = codePath;
        String suffix = null;
        suffix = packageNameConfigure.getRootPackage();
        //将.替换为/
        suffix = suffix.replaceAll("\\.", "/");
        //拼接 生成磁盘位置
        codePath = codePath + packageNameConfigure.getMiddle() + suffix;
        System.out.println("代码目录为->" + codePath);
        System.out.println("编译后目录为->" + classPath);
    }

    /**
     * 检测哪些功能以开启 若开启则进行扫描
     * @param className 类名
     * @param classPath 类的全路径
     */
    private void scan(String className, String classPath) {
        Class clazz = null;
        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            System.out.println(className + "类不存在");
            e.printStackTrace();
        }
        if (openConfigure.isDao()) {
            exe.execute(new DaoCodeYnc(className));
        }
        if (openConfigure.isService()) {
            exe.execute(new ServiceCodeYnc(className));
        }
        if (openConfigure.isServiceImp()) {
            exe.execute(new ServiceImpCodeYnc(className));
        }
        if (openConfigure.isCreateTab()) {
            exe.execute(new SqlCodeYnc(className, clazz));
        }
        if (openConfigure.isMapper()) {
            exe.execute(new MapperCodeYnc(clazz));
        }
    }
}
