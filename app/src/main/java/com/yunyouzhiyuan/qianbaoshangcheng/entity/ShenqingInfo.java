package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by wangjunqiang on 2016/11/26.
 */
public class ShenqingInfo {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : {"sid":"15","stroname":"旭老板美食","connecter":"旭老板","cphone":"15088888888","addr":"山西 阳泉市 矿区","province_id":"3102","city_id":"3379","district":"3388","xx_addr":"从你的全世界路过","introducetext":"从记得阿宁的从你的全世界路过","outpicture":"/Uploads/Picture/2017-03-21/20170321164528627.jpg","insidepicture":"/Uploads/Picture/2017-03-21/20170321164534575.jpg","slogo":"/Uploads/Picture/2017-03-21/20170321164542945.jpg","stype":"0","idname":"旭老板","idcard":"123452555555555","hand_card":"/Uploads/Picture/2017-03-21/20170321164555485.jpg","lic_name":"旭老板","lic_code":"95555555555","lic_addr":"哦里咯啦咯啦咯","lic_endtime":"2017-3-24","lic_picture":"/Uploads/Picture/2017-03-21/20170321164620550.jpg","per_name":"空军建军节","per_code":"5555555","per_addr":"啦咯啦咯啦咯","per_endtime":"2017-3-31","per_picture":"/Uploads/Picture/2017-03-21/20170321164644441.jpg","bank_name":"中国农业银行","bank_num":"6228480402564890018","bank_uname":"旭老板","gpicture":"/Uploads/Picture/2017-03-21/20170321164733558.jpg","gprice":"100.00","avgprice":"80.00","jingdu":"","weidu":""}
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

    public static class DataBean {
        /**
         * sid : 15
         * stroname : 旭老板美食
         * connecter : 旭老板
         * cphone : 15088888888
         * addr : 山西 阳泉市 矿区
         * province_id : 3102
         * city_id : 3379
         * district : 3388
         * xx_addr : 从你的全世界路过
         * introducetext : 从记得阿宁的从你的全世界路过
         * outpicture : /Uploads/Picture/2017-03-21/20170321164528627.jpg
         * insidepicture : /Uploads/Picture/2017-03-21/20170321164534575.jpg
         * slogo : /Uploads/Picture/2017-03-21/20170321164542945.jpg
         * stype : 0
         * idname : 旭老板
         * idcard : 123452555555555
         * hand_card : /Uploads/Picture/2017-03-21/20170321164555485.jpg
         * lic_name : 旭老板
         * lic_code : 95555555555
         * lic_addr : 哦里咯啦咯啦咯
         * lic_endtime : 2017-3-24
         * lic_picture : /Uploads/Picture/2017-03-21/20170321164620550.jpg
         * per_name : 空军建军节
         * per_code : 5555555
         * per_addr : 啦咯啦咯啦咯
         * per_endtime : 2017-3-31
         * per_picture : /Uploads/Picture/2017-03-21/20170321164644441.jpg
         * bank_name : 中国农业银行
         * bank_num : 6228480402564890018
         * bank_uname : 旭老板
         * gpicture : /Uploads/Picture/2017-03-21/20170321164733558.jpg
         * gprice : 100.00
         * avgprice : 80.00
         * jingdu :
         * weidu :
         */

        private String sid;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String stroname;
        private String connecter;
        private String cphone;
        private String addr;
        private String province_id;
        private String city_id;
        private String district;
        private String xx_addr;
        private String introducetext;
        private String outpicture;
        private String insidepicture;
        private String slogo;
        private String stype;
        private String idname;
        private String idcard;
        private String hand_card;
        private String lic_name;
        private String lic_code;
        private String lic_addr;
        private String lic_endtime;
        private String lic_picture;
        private String per_name;
        private String per_code;
        private String per_addr;
        private String per_endtime;
        private String per_picture;
        private String bank_name;
        private String bank_num;
        private String bank_uname;
        private String gpicture;
        private String gprice;
        private String avgprice;
        private String jingdu;
        private String weidu;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getStroname() {
            return stroname;
        }

        public void setStroname(String stroname) {
            this.stroname = stroname;
        }

        public String getConnecter() {
            return connecter;
        }

        public void setConnecter(String connecter) {
            this.connecter = connecter;
        }

        public String getCphone() {
            return cphone;
        }

        public void setCphone(String cphone) {
            this.cphone = cphone;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getXx_addr() {
            return xx_addr;
        }

        public void setXx_addr(String xx_addr) {
            this.xx_addr = xx_addr;
        }

        public String getIntroducetext() {
            return introducetext;
        }

        public void setIntroducetext(String introducetext) {
            this.introducetext = introducetext;
        }

        public String getOutpicture() {
            return outpicture;
        }

        public void setOutpicture(String outpicture) {
            this.outpicture = outpicture;
        }

        public String getInsidepicture() {
            return insidepicture;
        }

        public void setInsidepicture(String insidepicture) {
            this.insidepicture = insidepicture;
        }

        public String getSlogo() {
            return slogo;
        }

        public void setSlogo(String slogo) {
            this.slogo = slogo;
        }

        public String getStype() {
            return stype;
        }

        public void setStype(String stype) {
            this.stype = stype;
        }

        public String getIdname() {
            return idname;
        }

        public void setIdname(String idname) {
            this.idname = idname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getHand_card() {
            return hand_card;
        }

        public void setHand_card(String hand_card) {
            this.hand_card = hand_card;
        }

        public String getLic_name() {
            return lic_name;
        }

        public void setLic_name(String lic_name) {
            this.lic_name = lic_name;
        }

        public String getLic_code() {
            return lic_code;
        }

        public void setLic_code(String lic_code) {
            this.lic_code = lic_code;
        }

        public String getLic_addr() {
            return lic_addr;
        }

        public void setLic_addr(String lic_addr) {
            this.lic_addr = lic_addr;
        }

        public String getLic_endtime() {
            return lic_endtime;
        }

        public void setLic_endtime(String lic_endtime) {
            this.lic_endtime = lic_endtime;
        }

        public String getLic_picture() {
            return lic_picture;
        }

        public void setLic_picture(String lic_picture) {
            this.lic_picture = lic_picture;
        }

        public String getPer_name() {
            return per_name;
        }

        public void setPer_name(String per_name) {
            this.per_name = per_name;
        }

        public String getPer_code() {
            return per_code;
        }

        public void setPer_code(String per_code) {
            this.per_code = per_code;
        }

        public String getPer_addr() {
            return per_addr;
        }

        public void setPer_addr(String per_addr) {
            this.per_addr = per_addr;
        }

        public String getPer_endtime() {
            return per_endtime;
        }

        public void setPer_endtime(String per_endtime) {
            this.per_endtime = per_endtime;
        }

        public String getPer_picture() {
            return per_picture;
        }

        public void setPer_picture(String per_picture) {
            this.per_picture = per_picture;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_num() {
            return bank_num;
        }

        public void setBank_num(String bank_num) {
            this.bank_num = bank_num;
        }

        public String getBank_uname() {
            return bank_uname;
        }

        public void setBank_uname(String bank_uname) {
            this.bank_uname = bank_uname;
        }

        public String getGpicture() {
            return gpicture;
        }

        public void setGpicture(String gpicture) {
            this.gpicture = gpicture;
        }

        public String getGprice() {
            return gprice;
        }

        public void setGprice(String gprice) {
            this.gprice = gprice;
        }

        public String getAvgprice() {
            return avgprice;
        }

        public void setAvgprice(String avgprice) {
            this.avgprice = avgprice;
        }

        public String getJingdu() {
            return jingdu;
        }

        public void setJingdu(String jingdu) {
            this.jingdu = jingdu;
        }

        public String getWeidu() {
            return weidu;
        }

        public void setWeidu(String weidu) {
            this.weidu = weidu;
        }
    }
}
