package cn.sharehe.handle.configure;

import java.util.HashMap;
import java.util.Map;

public class VarTypeConfigure {
    private VarTypeConfigure(){}
    public static Map<String, String> TYPEMAP=new HashMap<String, String>();
    static {
        TYPEMAP.put("String","VARCHAR");
        TYPEMAP.put("byte","BLOB");
        TYPEMAP.put("Long","INTEGER");
        TYPEMAP.put("Integer","int");
        TYPEMAP.put("Boolean","BIT");
        TYPEMAP.put("Float","FLOAT");
        TYPEMAP.put("Double","DOUBLE");
        TYPEMAP.put("float","FLOAT");
        TYPEMAP.put("double","DOUBLE");
        TYPEMAP.put("int","INT");
        TYPEMAP.put("boolean","INT");
        TYPEMAP.put("long","INT");

    }
}
