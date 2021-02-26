package com.mmd.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回前端的实体类
 * code: 约定200成功 404失败
 * message: 带给前端的消息
 * data: 实际带给前端的某个数据
 * 其实原本是返回一个data即可，但是将data封装成规范的CommonResult
 * 方便前端，方便后端，较为规范
 * @param <T>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResult<T> implements Serializable {
    private  Integer code;
    private String message;
    private T data;

    private static final Integer SUCCESS_STATE = 200;
    private static final Integer FAIL_STATE = 404;

    public CommonResult(Integer code, String message) {
       this(code,message,null);
    }

    public static <T> CommonResult<T> SUCCESS_DATA(String message, T t){
        return new CommonResult<>(SUCCESS_STATE,message,t);
    }

    public static <T>  CommonResult<T> SUCCESS_NODATA(String message){
        return new CommonResult<>(SUCCESS_STATE,message);
    }

    public static <T>  CommonResult<T> FAIL( String message){
        return new CommonResult<>(FAIL_STATE,message);
    }
}
