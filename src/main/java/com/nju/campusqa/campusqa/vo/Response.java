package com.nju.campusqa.campusqa.vo;

/**
 * Created by xiongzehua on 2018/12/15.
 */
public class Response<T> {
    private int status;
    private T data;

    private Response(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> Response<T> createBySuccess(T data){
        return new Response<T>(ResponseStatus.SUCCESS.getCode(), data);
    }

    public static <T> Response<T> createByNeedAuthority(T data){
        return new Response<T>(ResponseStatus.NEED_AUTHORITY.getCode(), data);
    }

    public static <T> Response<T> createByIllegalArgument(T data){
        return new Response<T>(ResponseStatus.ILLEGAL_ARGUMENT.getCode(), data);
    }

    public static <T> Response<T> createByBan(T data){
        return new Response<T>(ResponseStatus.BAN.getCode(), data);
    }

}
