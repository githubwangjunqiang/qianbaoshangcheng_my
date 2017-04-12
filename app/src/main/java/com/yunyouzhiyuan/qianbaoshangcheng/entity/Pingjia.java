package com.yunyouzhiyuan.qianbaoshangcheng.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/1.
 */

public class Pingjia implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功!
     * data : {"content":"不错 环境优雅 声音洪亮 嗨的郑老师抱着旭老板 么么哒呢 两个人欲仙欲死的拼命折腾 翻滚吧我的乖","add_time":"2017-04-01 16:44:44","user_id":"31","img":["/Uploads/Picture/2017-04-01/20170401164441321.jpg","/Uploads/Picture/2017-04-01/20170401164442696.jpg","/Uploads/Picture/2017-04-01/20170401164442769.jpg","/Uploads/Picture/2017-04-01/20170401164442119.jpg","/Uploads/Picture/2017-04-01/20170401164443423.jpg","/Uploads/Picture/2017-04-01/20170401164444733.jpg","/Uploads/Picture/2017-04-01/20170401164444449.jpg"],"head_pic":null,"nickname":"新用户~~15066666666"}
     */

    private int retcode;
    private String msg;
    private DataBean data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public  class DataBean implements Serializable{
        /**
         * content : 不错 环境优雅 声音洪亮 嗨的郑老师抱着旭老板 么么哒呢 两个人欲仙欲死的拼命折腾 翻滚吧我的乖
         * add_time : 2017-04-01 16:44:44
         * user_id : 31
         * img : ["/Uploads/Picture/2017-04-01/20170401164441321.jpg","/Uploads/Picture/2017-04-01/20170401164442696.jpg","/Uploads/Picture/2017-04-01/20170401164442769.jpg","/Uploads/Picture/2017-04-01/20170401164442119.jpg","/Uploads/Picture/2017-04-01/20170401164443423.jpg","/Uploads/Picture/2017-04-01/20170401164444733.jpg","/Uploads/Picture/2017-04-01/20170401164444449.jpg"]
         * head_pic : null
         * nickname : 新用户~~15066666666
         */

        private String content;
        private String add_time;
        private String user_id;
        private Object head_pic;
        private String nickname;
        private List<String> img;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(Object head_pic) {
            this.head_pic = head_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
