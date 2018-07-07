package cn.sharehe.handle.async;

import cn.sharehe.handle.AutoMaticApp;
import cn.sharehe.handle.annotation.TableName;
import cn.sharehe.handle.configure.PackageNameConfigure;
import cn.sharehe.handle.configure.VarTypeConfigure;
import cn.sharehe.handle.configure.ClassNameConfigure;
import cn.sharehe.handle.configure.MethodNameConfigure;
import cn.sharehe.handle.utils.CodeMatcher;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MapperCodeYnc implements Runnable {
    private Class clazz;
    private String className;
    public MapperCodeYnc(Class clazz){
        this.clazz=clazz;
    }
    public void run() {
        String tem=code();
        createFile(ClassNameConfigure.className.get(ClassNameConfigure.DAO).replaceAll("\\{\\}",className),tem);
        System.out.println(Thread.currentThread().getName()+ClassNameConfigure.className.get(ClassNameConfigure.DAO).replaceAll("\\{\\}",className).replaceAll("java","xml")+"创建成功");
    }
    private String code(){
        int tem=0;
        String tableName=null;
        String temName=clazz.getName();
        tem=temName.lastIndexOf(".");
        className=temName.substring(tem+1);//类名
        String packageName=temName.substring(0, tem);//bean包名
        //获得表明
        TableName tableName1= (TableName) clazz.getAnnotation(TableName.class);
        if(tableName1 != null)
            tableName=tableName1.value();
        else
            tableName=CodeMatcher.BigTo_(className);

        StringBuffer head=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"
                +"<mapper namespace=\""+ PackageNameConfigure.getInstance().getRootPackage()+PackageNameConfigure.getInstance().getDao()+"."+ClassNameConfigure.className.get(ClassNameConfigure.DAO).replaceAll("\\{\\}",className).replaceAll("\\.java","")+"\">\n");
        head.append("<resultMap type=\""+packageName+"."+className+"\" id=\"baseMap\">\n");
        Field fields[]=clazz.getDeclaredFields();
        List<String> fieldl=new ArrayList();
        for(Field i:fields){
            if(getSqlType(i.getType())==null)
                continue;
            fieldl.add(i.getName());  //将bean中所有属性提取出来
        }
        for(String i:fieldl){  //增加属性与map映射
            head.append("<result column=\""+CodeMatcher.BigTo_(i)+"\" property=\""+i+"\"/>\n");
        }
        head.append("</resultMap>\n<sql id=\"base\">\n");  //结束
        for(int i=0;i<fieldl.size();i++){
            if(i==0)
                head.append(CodeMatcher.BigTo_(fieldl.get(i)));
            else
                head.append(","+CodeMatcher.BigTo_(fieldl.get(i)));
        }
        head.append("</sql>\n");  //sql结束
        //insert开始
        head.append("<insert id=\""+codeReplace(MethodNameConfigure.INSERT)+"\" parameterType=\""+packageName+"."+className+"\">\n");
        head.append("insert into "+tableName+"(\n");
        head.append("<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">\n");
        for(String i:fieldl){
            head.append("<if test=\""+ i +" != null and "+ i +" != '' \"> "+CodeMatcher.BigTo_(i)+",</if>\n");
        }
        head.append("</trim> )\n VALUES(");
        head.append("<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">\n");
        for(String i:fieldl){
            head.append("<if test=\"" + i + " != null and " + i + " != '' \"> #{" + i + "},</if>\n");
        }
        head.append("</trim>\n)\n</insert>");  //insert结束

        //select all开始
        head.append("<select id=\""+codeReplace(MethodNameConfigure.SELECTALL)+"\" parameterType=\"");
                if(CodeMatcher.MethodFieldType(MethodNameConfigure.MethodType.get(MethodNameConfigure.SELECTALL)))
                    head.append("java.util.Map");
                else
                    head.append(packageName+"."+className);
        head.append("\"\nresultMap=\"baseMap\">\nselect\n<include refid=\"base\" />\nfrom "+tableName+"\n<where>\n");
        for(String i:fieldl){
            head.append("<if test=\"" + i + " != null and "+i+" != '' \"> and "+CodeMatcher.BigTo_(i)+"= #{"+i+"}</if>\n");
        }
        head.append("</where>\n");
        head.append("<!--Order by 这里写入列名  DESC/ASC-->\n<!--分页设置-->\n");
        head.append("<!--<if test=\"startRecord != null and startRecord !='' \">\nlimit ${startRecord},${pageSize}\n</if>-->");
        head.append("\n</select>\n");// all 结束

        //select by id 开始
        head.append("<select id=\""+codeReplace(MethodNameConfigure.SELECTBYID)+"\" parameterType=\"java.lang.String\"\nresultMap=\"baseMap\">\nselect\n<include refid=\"base\" />\nfrom "+tableName+"\n<where>\n");
        head.append(codeReplace(MethodNameConfigure.SELECTBYID,0)+"= #{_parameter}\n");
        head.append("</where>\n");
        head.append("</select>\n");// by id 结束

        //updata 开始
        head.append("<update id=\""+codeReplace(MethodNameConfigure.UPDATE)+"\" parameterType=\""+packageName+"."+className+"\">\nUPDATE "+tableName+"\n<set>\n");
        for(String i:fieldl){
            if(i.equals("id"))
                continue;  //不更新id
            head.append("<if test=\""+i+" != null and "+i+" != '' \"> "+CodeMatcher.BigTo_(i)+"=#{"+i+"},</if>");
        }
        head.append("</set>\nWHERE id=#{id}\n</update>\n");  //updata结束
        //delete 开始
        head.append("<delete id=\""+codeReplace(MethodNameConfigure.DELETE)+"\" parameterType=\"String\">"+
                "DELETE\nFROM "+tableName+"\n<where>\n"+
                codeReplace(MethodNameConfigure.DELETE,0)+" =#{_parameter}"+
                "</where>\n</delete>\n"); //jies

        head.append("</mapper>");
        return head.toString();
    }
    /**
     * 将编码格式中的{}转换为类名
     * @return
     */
    private String codeReplace(Integer i){
        String input= MethodNameConfigure.MethodType.get(i);
        input= CodeMatcher.MethodName(input);
        return input.replaceAll("\\{\\}",className);
    }
    /**
     * 将编码格式中的{}转换为类名
     * @return
     */
    private String codeReplace(Integer i,int tem){
        String input= MethodNameConfigure.MethodType.get(i);
        input=CodeMatcher.MethodFieldName(input);
        return input.replaceAll("\\{\\}",className);
    }

    /**
     * 生成编码文件
     * @param fileName 文件名
     * @param code 编码字符
     */
    protected void createFile(String fileName,String code){
        String filePath=null;
            if(PackageNameConfigure.getInstance().getMapper()==null)
                filePath=PackageNameConfigure.getInstance().getRootPackage()+PackageNameConfigure.getInstance().getDao();
            else
                filePath=PackageNameConfigure.getInstance().getRootPackage()+PackageNameConfigure.getInstance().getMapper();
        filePath=(AutoMaticApp.projectPath+PackageNameConfigure.getInstance().getMiddleMapp()+filePath).replaceAll("\\.","/");
        fileName=filePath+"/"+fileName.replaceAll("java","xml");
        File file=new File(filePath);
        if(!file.exists())
            file.mkdirs();
        try {
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(fileName));
            out.write(code);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将java数据类型转换为sql数据类型
     * @param i
     * @return
     */
    private String getSqlType(Class i){
        return VarTypeConfigure.TYPEMAP.get(i.getName().substring(i.getName().lastIndexOf('.')+1));
    }
}
