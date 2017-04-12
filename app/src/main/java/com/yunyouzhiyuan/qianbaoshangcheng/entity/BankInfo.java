package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class BankInfo {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"store_money":"0.00","bank_name":"网络","account_bank":"中国农业银行","account_name":"6228486352995566885"}
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

    public  class DataBean {
        /**
         * store_money : 0.00
         * bank_name : 网络
         * account_bank : 中国农业银行
         * account_name : 6228486352995566885
         */

        private String store_money;
        private String bank_name;
        private String account_bank;
        private String account_name;

        public String getStore_money() {
            return store_money;
        }

        public void setStore_money(String store_money) {
            this.store_money = store_money;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAccount_bank() {
            return account_bank;
        }

        public void setAccount_bank(String account_bank) {
            this.account_bank = account_bank;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }
    }
}
