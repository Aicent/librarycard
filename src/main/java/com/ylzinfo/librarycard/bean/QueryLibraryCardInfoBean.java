package com.ylzinfo.librarycard.bean;

public class QueryLibraryCardInfoBean {


    /**
     * resultCode : 1
     * resultMsg :
     * resultBody : {"idCard":"35042719850511001X","libraryCardNo":"11111","realName":"洪一鸣"}
     */

    private int resultCode;
    private String resultMsg;
    private ResultBodyBean resultBody;

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

    public ResultBodyBean getResultBody() {
        return resultBody;
    }

    public void setResultBody(ResultBodyBean resultBody) {
        this.resultBody = resultBody;
    }

    public static class ResultBodyBean {
        /**
         * idCard : 35042719850511001X
         * libraryCardNo : 11111
         * realName : 洪一鸣
         */

        private String idCard;
        private String libraryCardNo;
        private String realName;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getLibraryCardNo() {
            return libraryCardNo;
        }

        public void setLibraryCardNo(String libraryCardNo) {
            this.libraryCardNo = libraryCardNo;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}
