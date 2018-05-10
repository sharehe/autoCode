package cn.sharehe.handle.configure;
/**
 * 数据库连接属性与数据库连接池大小设置
 */

public class JdbcConfigure {
    private String url;
    private String user;
    private String password;
    private int concurrencyPool;
    private JdbcConfigure(){
        concurrencyPool=5;
    }
    private static class Instance{
        private static JdbcConfigure instance=new JdbcConfigure();
    }
    public static JdbcConfigure getInstance(){
        return Instance.instance;
    }

    public int getConcurrencyPool() {
        return concurrencyPool;
    }

    public JdbcConfigure setConcurrencyPool(int concurrencyPool) {
        this.concurrencyPool = concurrencyPool;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public JdbcConfigure setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUser() {
        return user;
    }

    public JdbcConfigure setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JdbcConfigure setPassword(String password) {
        this.password = password;
        return this;
    }
}
