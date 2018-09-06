package cn.sharehe.handle.async;

import cn.sharehe.handle.annotation.*;
import cn.sharehe.handle.configure.VarTypeConfigure;
import cn.sharehe.handle.utils.CodeMatcher;
import cn.sharehe.handle.utils.DataConnectPool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCodeYnc implements Runnable {
    private String className;
    private Class clazz;
    public SqlCodeYnc(String className,Class clazz){
        this.className= CodeMatcher.BigTo_(className);
        this.clazz=clazz;
    }

    /**
     * 生成sql语句
     */
    private void code(){
        StringBuffer buf =new StringBuffer();
        TableName tableName= (TableName) clazz.getAnnotation(TableName.class);
        LengthAndNote lan;
        NotNull nn;
        PrimaryKey primaryKey;
        int len;
        String note;
        String notnull;
        String key = null;
        if(tableName!=null)
            className=tableName.value();
        for(Field i:clazz.getDeclaredFields()){
            if(getSqlType(i.getType())==null)
                continue;
            len=11;
            note="";
            notnull="NULL";
            buf.append(",").append(CodeMatcher.BigTo_(i.getName()));
            if (i.getAnnotation(Char.class) != null){   // 是否是char类型
                buf.append(" char");
            } else {
                buf.append(" " + getSqlType(i.getType()));
            }
            lan=i.getAnnotation(LengthAndNote.class);
            if(lan != null){
                note=lan.note();
                len=lan.length();
            }
            nn=i.getAnnotation(NotNull.class);
            if(nn!=null&&nn.value())
                notnull="NOT NULL";
            primaryKey = i.getAnnotation(PrimaryKey.class);
            if(primaryKey != null)
                key = CodeMatcher.BigTo_(i.getName());
            buf.append(" (").append(len).append(") ");
            buf .append(notnull);
            if (i.getAnnotation(Unique.class) != null)
                buf.append(" unique");
            if (i.getAnnotation(AutoIncrement.class) != null)
                buf.append(" auto_increment");
            buf.append(" COMMENT '"+note+"' ");
        }
        if(key != null){
            buf.append(", PRIMARY KEY ("+key+")");
        }
        StringBuffer tem=new StringBuffer();
        tem.append("drop TABLE ").append(className);
        try {
            exe(tem.toString());
        } catch (SQLException e) {
        }
        tem=new StringBuffer();
        tem.append("  create table ").append(className).append("(").append(buf.toString().substring(1)).append(")");
        try {
            if(exe(tem.toString())>0){
                System.out.println("表"+className+"创建完成");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行sql语句
     * @param code
     */
    private int exe(String code) throws SQLException {
        int rel=0;
        int ad= DataConnectPool.getConnection();
        Connection connet=DataConnectPool.getConnection(ad);
            try{
                Statement state= connet.createStatement();
                rel=state.executeUpdate(code);
                state.close();
            }catch (SQLException e){
                throw  new SQLException();
            }finally {
                DataConnectPool.closeConnect(ad);
            }
            return rel;
    }

    /**
     * 将java数据类型转换为sql数据类型
     * @param i
     * @return
     */
    private String getSqlType(Class i){
        return VarTypeConfigure.TYPEMAP.get(i.getName().substring(i.getName().lastIndexOf('.')+1));
    }
    public void run() {
        code();
    }
}
