package com.sei.bean;

public class ResetPassword {
    private Integer id;
    private String loginacct;
    private long failuretime;
    private String mkey;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getFailuretime() {
        return failuretime;
    }

    public void setFailuretime(long failuretime) {
        this.failuretime = failuretime;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }


    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
