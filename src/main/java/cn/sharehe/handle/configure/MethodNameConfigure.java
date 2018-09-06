package cn.sharehe.handle.configure;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置添加方法的对象 单列模式
 */
public class MethodNameConfigure {
    // 标记用来查询全部数据
    public final static int SELECTALL=1;
    // 标记更新的id
    public final static int UPDATE=2;
    //标记删除的id
    public final static int DELETE=3;
    // 标记删除多个
    public final static int DELETEMANY = 6;
    //标记插入的id
    public final static int INSERT=4;
    //标记根据id查询的
    public final static int  SELECTBYID=5;
    //保存方法格式
    public static Map<Integer,String> MethodType=new HashMap<Integer,String>(10);
//    public static Map<String,String>
    /**
     * 构造方法私有化
     */
    private MethodNameConfigure(){ }
    static {
        MethodType.put(SELECTALL,"/查询全部数据/ public List<{}> qry{}All({} data)");
        MethodType.put(SELECTBYID,"/根据id查询一条数据/ public {} qry{}ById(String id)");
        MethodType.put(UPDATE,"/更新数据/ public boolean edit{}({} data)");
        MethodType.put(INSERT,"/添加数据/ public String add{}({} data)");
        MethodType.put(DELETE,"/删除数据/ public boolean del{}ById(String id)");
        MethodType.put(DELETEMANY,"/删除多个数据/ public boolean del{}ManyByIds(List<String> ids)");
    }
}
