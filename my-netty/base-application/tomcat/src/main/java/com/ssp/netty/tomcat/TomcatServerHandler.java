package com.ssp.netty.tomcat;


import com.ssp.netty.SomeServlet;
import com.ssp.netty.servlet.CustomHttpRequest;
import com.ssp.netty.servlet.CustomHttpResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class TomcatServerHandler extends ChannelInboundHandlerAdapter {
    // 若需要将msg返回给客户端，则需要继承自ChannelInboundHandlerAdapter，否则
    // 继承自SimpleChannelInbountHandler
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

            SomeServlet servlet = new SomeServlet();
            servlet.doGet(new CustomHttpRequest(request, ctx), new CustomHttpResponse(request, ctx));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
