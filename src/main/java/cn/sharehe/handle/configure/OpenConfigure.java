package cn.sharehe.handle.configure;

/**
 * 配置是否开启自动装载系统架构
 */
public class OpenConfigure {
    private OpenConfigure(){}
    private static class Instance{
        private static OpenConfigure instance = new OpenConfigure();
    }
    public static OpenConfigure getInstance(){
        return Instance.instance;
    }

    //开启扫描  扫描实体类生成对应层文档
    private  boolean scan;
    //#开启扫描后 一下属性均有默认值 默认为true 线程池默认大小为10
    //#是否生成dao
    private  boolean dao;
    //是否生成 service接口
    private  boolean service;
    //是否生成 serviceimp 服务实现 一一对应service接口 若service=false 则没有实现接口
    private  boolean serviceImp;
    //是否生成 mybaits mapper文件
    private  boolean mapper;
    //是否创建数据库表
    private  boolean createTab;
    //线程池数量 用于生成代码时使用
    private  int threadPool=10;
    // 是否开启主键为uuid 默认开启
    private boolean primaryKeyUUID = true;

    public boolean isPrimaryKeyUUID() {
        return primaryKeyUUID;
    }

    public OpenConfigure setPrimaryKeyUUID(boolean primaryKeyUUID) {
        this.primaryKeyUUID = primaryKeyUUID;
        return this;
    }

    public boolean isScan() {
        return scan;
    }

    /**
     * 扫描实体类生成对应层文档
     * 开启扫描后 属性均有默认值 默认为true
     * @param scan
     * @return
     */
    public OpenConfigure setScan(boolean scan) {
        this.scan = scan;
        return this;
    }

    public boolean isDao() {
        return dao;
    }

    /**
     * 是否生成dao
     * @param dao
     * @return
     */
    public OpenConfigure setDao(boolean dao) {
        this.dao = dao;
        return this;
    }

    public boolean isService() {
        return service;
    }

    /**
     * 是否生成 service接口
     * @param service
     * @return
     */
    public OpenConfigure setService(boolean service) {
        this.service = service;
        return this;
    }
    public boolean isServiceImp() {
        return serviceImp;
    }
    /**
     *是否生成 serviceimp 服务实现 一一对应service接口 若service=false 则没有实现接口
     * @return
     */
    public OpenConfigure setServiceImp(boolean serviceImp) {
        this.serviceImp = serviceImp;
        return this;
    }

    public boolean isMapper() {
        return mapper;
    }

    /**
     * 是否生成 mybaits mapper文件
     * @param mapper
     * @return
     */
    public OpenConfigure setMapper(boolean mapper) {
        this.mapper = mapper;
        return this;
    }

    public boolean isCreateTab() {
        return createTab;
    }

    /**
     * 是否创建数据库表
     * @param createTab
     * @return
     */
    public OpenConfigure setCreateTab(boolean createTab) {
        this.createTab = createTab;
        return this;
    }

    public int getThreadPool() {
        return threadPool;
    }

    /**
     * 线程池数量 用于生成代码时使用 默认为10
     * @param threadPool
     * @return
     */
    public OpenConfigure setThreadPool(int threadPool) {
        this.threadPool = threadPool;
        return this;
    }
}
