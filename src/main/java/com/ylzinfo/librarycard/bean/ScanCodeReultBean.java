package com.ylzinfo.librarycard.bean;

import java.io.Serializable;

public class ScanCodeReultBean implements Serializable {
    private String code;
    private String result;

    public ScanCodeReultBean(String var1, String var2) {
        this.code = var1;
        this.result = var2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String var1) {
        this.code = var1;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String var1) {
        this.result = var1;
    }
}
