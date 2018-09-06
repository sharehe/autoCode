package cn.sharehe.handle.beans;

import cn.sharehe.handle.annotation.PrimaryKey;

/**
 * @Author: wugui
 * @Date 2018-9-7 00:02
 */
public class TestO {
    @PrimaryKey
    private String uwJs;

    public String getUwJs() {
        return uwJs;
    }

    public void setUwJs(String uwJs) {
        this.uwJs = uwJs;
    }
}
