package com.nju.campusqa.campusqa.vo;

/**
 * Created by xiongzehua on 2018/12/15.
 */
public enum ResponseStatus {

    SUCCESS(0,"SUCCESS"),
    NEED_AUTHORITY(1,"NEED_AUTHORITY"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    BAN(3,"BAN");

    private final int status;
    private final String desc;


    ResponseStatus(int code,String desc){
        this.status = code;
        this.desc = desc;
    }

    public int getCode(){
        return status;
    }
    public String getDesc(){
        return desc;
    }
}
