package com.ssp.netty;

import com.ssp.netty.servlet.CustomHttpRequest;
import com.ssp.netty.servlet.CustomHttpResponse;
import com.ssp.netty.servlet.CustomHttpServlet;

// 业务Servlet
public class SomeServlet extends CustomHttpServlet {
    @Override
    public void doGet(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        String uri = request.getUri();
        String path = request.getPath();
        String param = request.getParameter("name");
        String method = request.getMethod();
        String content = "method = " + method + "\n" +
                         "uri = " + uri + "\n" +
                         "path = " + path + "\n" +
                         "param = " + param;

        response.write(content);
    }

    @Override
    public void doPost(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        doGet(request, response);
    }
}
