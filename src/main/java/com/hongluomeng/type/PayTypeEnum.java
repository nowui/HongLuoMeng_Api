package com.hongluomeng.type;

public enum PayTypeEnum {

    ALI_PAY("ALI_PAY"),
    WECHAT_PAY("WECHAT_PAY");

    private String key;

    private PayTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
