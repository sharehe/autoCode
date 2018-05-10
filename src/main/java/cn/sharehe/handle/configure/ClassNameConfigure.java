package cn.sharehe.handle.configure;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名格式规范
 */
public class ClassNameConfigure {
    public final static int SERVICE=1;
    public final static int DAO=2;
    public final static int SERVICEIMP=3;
    public final static int MAPPER=4;
    public static Map<Integer,String> className=new HashMap<Integer, String>();
    static {
        className.put(SERVICE,"I{}Service.java");
        className.put(DAO,"{}Dao.java");
        className.put(SERVICEIMP,"{}ServiceImp.java");
        className.put(MAPPER,"{}Mapper.xml");
    }
}
