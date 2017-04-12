package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class User {
    private String sid;
    private String flag;
    private String stroName;
    private String slogo;
    private String addr;

    public User(String addr, String slogo, String stroName, String sid, String flag) {
        this.addr = (addr == null?"":addr);
        this.slogo = (slogo == null?"":slogo);
        this.stroName = (stroName == null?"":stroName);
        this.sid = (sid == null?"":sid);
        this.flag = (flag == null?"":flag);
    }

    public String getSid() {

        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStroName() {
        return stroName;
    }

    public void setStroName(String stroName) {
        this.stroName = stroName;
    }

    public String getSlogo() {
        return slogo;
    }

    public void setSlogo(String slogo) {
        this.slogo = slogo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
