package cn.sharehe.handle.configure;
/**
 * 用来设置对应包名的枚举  包均在cn.wugui.automatic 下
 */
public class PackageNameConfigure {
    private PackageNameConfigure(){}
    private static class Instance{
        private static PackageNameConfigure instance=new PackageNameConfigure();
    }
    public static PackageNameConfigure getInstance(){
        return Instance.instance;
    }
    private String dao="dao";
    private String service="service";
    private String serviceImp="service.imp";
    private String rootPackage="cn.wugui.automatic.";
    private String middle="/src/main/java/";
    private String middleMapp="/src/main/resources/";
    private String beans="beans";
    private String mapper;
    public String getMiddleMapp() {
        return middleMapp;
    }

    public PackageNameConfigure setMiddleMapp(String middleMapp) {
        this.middleMapp = middleMapp;
        return this;
    }

    public String getDao() {
        return dao;
    }

    /**
     * 设置持久层接口地址
     * @param dao
     * @return
     */
    public PackageNameConfigure setDao(String dao) {
        this.dao = dao;
        return this;
    }


    public String getService() {
        return service;
    }

    public String getMapper() {
        return mapper;
    }

    public PackageNameConfigure setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    /**
     * 设置service接口包地址
     * @param service
     * @return
     */
    public PackageNameConfigure setService(String service) {
        this.service = service;
        return this;
    }

    public String getServiceImp() {
        return serviceImp;
    }

    /**
     * 设置service实现包地址
     * @param serviceImp
     * @return
     */
    public PackageNameConfigure setServiceImp(String serviceImp) {
        this.serviceImp = serviceImp;
        return this;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    /**
     * 设置项目父包地址  注意后面的.
     * @param rootPackage
     * @return
     */
    public PackageNameConfigure setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
        return this;
    }

    public String getMiddle() {
        return middle;
    }
    /**
     * 设置项目到包之间的路径
     * @param middle
     * @return
     */
    public PackageNameConfigure setMiddle(String middle) {
        this.middle = middle;
        return this;
    }

    public String getBeans() {
        return beans;
    }

    /**
     * 设置实体类包名
     * @param beans
     * @return
     */
    public PackageNameConfigure setBeans(String beans) {
        this.beans = beans;
        return this;
    }
}
