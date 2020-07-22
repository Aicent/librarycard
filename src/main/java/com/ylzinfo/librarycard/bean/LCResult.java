package com.ylzinfo.librarycard.bean;

public class LCResult {


    /**
     * resultCode : 1
     * resultMsg : 绑定图书证成功
     * resultBody : null
     */

    private int resultCode;
    private String resultMsg;
    private Object resultBody;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Object getResultBody() {
        return resultBody;
    }

    public void setResultBody(Object resultBody) {
        this.resultBody = resultBody;
    }
}
