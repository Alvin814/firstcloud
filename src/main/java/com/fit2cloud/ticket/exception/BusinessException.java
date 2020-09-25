package com.fit2cloud.ticket.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -819216796235914046L;

    protected String code = null;

    public BusinessException(ReturnCode returnCode) {
        super(returnCode.getDesc());
        this.code = returnCode.name();
    }

    public BusinessException(ReturnCode returnCode, String msg) {
        super(msg);
        this.code = returnCode.name();
    }

    public BusinessException(ReturnCode returnCode, Throwable ex) {
        super(ex);
        this.code = returnCode.name();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

