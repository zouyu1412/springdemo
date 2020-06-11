package com.ssp.netty.rpcserver.entity;

import lombok.Data;

import java.io.Serializable;

// 客户端发送给服务端的服务调用信息
@Data
public class InvokeMessage implements Serializable {
    /**
     * 接口名，即服务名称
     */
    private String className;
    /**
     * 要调用的方法名
     */
    private String methodName;
    /**
     * 方法参数类型列表
     */
    private Class<?>[] paramTypes;
    /**
     * 方法参数值
     */
    private Object[] paramValues;
}
