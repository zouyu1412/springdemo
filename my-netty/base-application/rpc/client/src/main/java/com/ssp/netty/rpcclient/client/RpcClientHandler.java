package com.ssp.netty.rpcclient.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends ChannelInboundHandlerAdapter {
    private Object result;

    public Object getResult() {
        return result;
    }

    // msg就是服务端传递来的远程调用的计算结果
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
