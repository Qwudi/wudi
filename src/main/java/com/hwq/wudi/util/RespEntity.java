package com.hwq.wudi.util;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回实体
 */
@ApiModel("com.haowenqiang.util.respEntityUtil")
@Data
public class RespEntity<T> implements Serializable {
    @ApiModelProperty(value = "接口返回信息", name = "msg", example = "请求成功！！！")
    private String msg;
    @ApiModelProperty(value = "接口返回码", name = "code", example = "200")
    private String code;

    private T data;
    @ApiModelProperty(value = "接口返回错误信息", name = "error", example = "参数错误！！！")
    private String error;
    /**
     * ok
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> RespEntity<T> ok(T obj) {
        RespEntity respEntity = new RespEntity();
        respEntity.setCode(Constant.SUCCESS);
        respEntity.setMsg(Constant.SUCCESS_MSG);
        respEntity.setData(obj);
        return respEntity;
    }

    public static <T> RespEntity<T> ok(String code, String msg, T obj) {
        RespEntity respEntity = new RespEntity();
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(obj);
        return respEntity;
    }

    public static <T> RespEntity<T> fail(String code, String msg, String error) {
        RespEntity respEntity = new RespEntity();
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        respEntity.setError(error);
        return respEntity;
    }
}
