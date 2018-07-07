package cn.sharehe.handle.async;

import cn.sharehe.handle.configure.PackageNameConfigure;
import cn.sharehe.handle.AutoMaticApp;
import cn.sharehe.handle.configure.ClassNameConfigure;
import cn.sharehe.handle.configure.MethodNameConfigure;
import cn.sharehe.handle.configure.OpenConfigure;
import cn.sharehe.handle.utils.CodeMatcher;

import java.io.*;

/**
 * 生成编码异步类的基类
 */
abstract class BaseJavaCode {
    //实体类名
    protected String className;
    //存放生成代码
    protected StringBuffer buf=null;
    //功能开关
    protected OpenConfigure openConfigure;
    //关于各层包的命名
    protected PackageNameConfigure packageNameConfigure;
    protected BaseJavaCode(String className){
        this.className =className;
        packageNameConfigure=PackageNameConfigure.getInstance();
        openConfigure=OpenConfigure.getInstance();
    }

    /**
     * 在该类中生成编码 生成主体部分public class----}
     * @param name 类名
     * @param status  状态 true表示imp状态 false相反
     */
    protected void  code(String name,boolean status){
        if(status){
            String var =removeJava(ClassNameConfigure.className.get(ClassNameConfigure.DAO));
            if(openConfigure.isService()){
                buf.append("public class " + name + " ");
                buf.append("implements "+removeJava(ClassNameConfigure.className.get(ClassNameConfigure.SERVICE))+" {\n");
            }else{
                buf.append("public class " + name + " {\n");
            }
            buf.append("\t@Autowired\n\tprivate "+var+" "+belowCase(var)+";\n");
        }
        else
            buf.append("public interface ").append(name).append(" {");
        codeMethodFormat(status);
        buf.append("\n}");
        System.out.println(Thread.currentThread().getName()+"->"+name+"生成完毕");
    }

    /**
     * 代码前缀 关于包
     * @param PackName 直接父包
     * @return 父包string
     */
    public StringBuffer appendPackage(String PackName){
        StringBuffer buf=new StringBuffer();
        //生成包package
        buf.append("package ").append(packageNameConfigure.getRootPackage())
                .append(PackName).append(";\n");
        // 生成bean包import
        buf.append("import ").append(packageNameConfigure.getRootPackage())
                .append(packageNameConfigure.getBeans());
        // 判断是否是独立的类 若不是则需要加类名
        if (packageNameConfigure.getBeans().lastIndexOf(".") < 0)
            buf.append("."+className);
        buf.append(";\n");

        //生成list包import
        buf.append("import java.util.List;\n");
        //生成map包import
        buf.append("import java.util.Map;\n");
        //生成hashmap包import
        buf.append("import java.util.HashMap;\n");
        return buf;
    }
    /***
     * 生成编码文件
     * @param filePath 编码所在目录
     * @param fileName  文件名
     */
    protected void createFile(String filePath,String fileName){
        filePath=(AutoMaticApp.codePath+filePath).replaceAll("\\.","/");
        fileName=filePath+"/"+fileName;
        File file=new File(filePath);
        if(!file.exists())
            file.mkdirs();
        try {
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(fileName));
            out.write(buf.toString());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母转换为大写
     * @param str 字符
     * @return 首字母转换为大写
     */
    protected String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母转换为小写
     * @param str 字符串
     * @return 首字母转换为小写
     */
    protected String belowCase(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

    /**
     * 将编码格式中的{}转换为类名
     * @param input 对应格式
     * @return {}转换为类名
     */
   protected String codeReplace(String input){
        return input.replaceAll("\\{\\}",className);
   }

    /**
     * 清除后缀 .java
     * @param input 对应格式
     * @return 清除后缀 .java
     */
   protected String removeJava(String input){
       return codeReplace(input).replaceAll("\\.java","");
   }

    /**
     * 生成方法部分
     * @param status true imp
     */
   private void codeMethodFormat(boolean status){
           int j=0;
           String tem;
           for (Integer i: MethodNameConfigure.MethodType.keySet()){
               tem=MethodNameConfigure.MethodType.get(i);
               j=tem.lastIndexOf("/");
               if(j!=-1){
                   if(tem.charAt(0)!='/')
                       throw new RuntimeException("方法格式不正确//最多只能出现一对并且用来标明注解");
                  if(!status)
                   buf.append("\n\t/**\n\t*"+tem.substring(1,j)+"\n\t*/");
                   //清除注解
                   tem=tem.replaceAll("/[\\w\\W]*/","");
               }
               if(status&&openConfigure.isService()){
                 buf.append("\n\t@Override");
               }
               buf.append("\n\t"+codeReplace(tem));
               if(status){
                   buf.append("{"+"\n\t\treturn ");
                   buf.append(belowCase(removeJava(ClassNameConfigure.className.get(ClassNameConfigure.DAO))));
                   buf.append("."+ codeReplace(CodeMatcher.MethodName(tem)));
                   buf.append("("+ tem.substring(tem.lastIndexOf(' ')+1,tem.length()-1)+")");
                   buf.append(";\n\t}");
               }else {
                   buf.append(";");
               }
       }
   }
}
