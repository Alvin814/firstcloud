package com.fit2cloud.ticket.exception;

public enum ReturnCode {

    S200("成功"), S201("成功，无有效数据"),

    // 参数错误
    E400000("参数错误"), E401000("参数格式错误"), E402000("参数值错误"),

    // 服务错误
    E500000("服务异常：未知错误"), E500001("请求失败"),

    // 数据错误
    E600000("验证失败");

    private String desc;

    ReturnCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}