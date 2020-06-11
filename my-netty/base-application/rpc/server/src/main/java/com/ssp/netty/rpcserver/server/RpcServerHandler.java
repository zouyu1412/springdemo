package com.ssp.netty.rpcserver.server;


import com.ssp.netty.rpcserver.entity.InvokeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.PrintWriter;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<InvokeMessage> {
    private Map<String, Object> registerMap;

    public RpcServerHandler(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokeMessage msg) throws Exception {
        Object result = "没有你调用的方法";
        if(registerMap.containsKey(msg.getClassName())) {
            // 从注册表中获取接口名称的服务提供者
            Object provider = registerMap.get(msg.getClassName());
            // 完成客户端的远程调用执行（真正的执行是在这里完成的）
            result = provider.getClass()
                    .getMethod(msg.getMethodName(), msg.getParamTypes())
                    .invoke(provider, msg.getParamValues());
        }
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
