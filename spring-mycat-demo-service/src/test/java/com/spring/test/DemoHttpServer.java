package com.spring.test;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * 测试1.6 轻量级http server
 */
public class DemoHttpServer {
    public static void main(String[] args) {
        try {
            HttpServer hs = HttpServer.create(new InetSocketAddress(8888), 1);//设置httpserver的端口号8888

            HttpHandler httpHandler = (t) -> {
                System.out.println("come in");
                InputStream iStream = t.getRequestBody();
                String response = "<h3>test1</h3>";
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            };

            HttpHandler httpHandler2 = (t) -> {
                System.out.println("come in");
                Headers responseHeaders = t.getResponseHeaders();
                URI requestURI = t.getRequestURI();
                String decode = URLDecoder.decode(requestURI.toString(), "utf-8");
                System.out.println(decode);

                // responseHeaders.set("Content-Type", "text/plain");
                //解决中文异常
                responseHeaders.set("Content-Type", "text/html; charset=UTF-8");

                InputStream iStream = t.getRequestBody();
                String response = "<h3>test2!! 危常焕</h3>";

                // t.sendResponseHeaders(200, response.length());
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            };

            //设置多个httpContext
            hs.createContext("/test1", httpHandler);
            HttpContext context = hs.createContext("/test2", httpHandler2);


//            hs.createContext("/china", new HttpHandler() {
//                @Override
//                public void handle(HttpExchange t) throws IOException {
//                    System.out.println("come in");
//                    // TODO Auto-generated method stub
//                    InputStream iStream = t.getRequestBody();
//                    String response = "<h3>Happy day!!</h3>";
//                    t.sendResponseHeaders(200, response.length());
//                    OutputStream os = t.getResponseBody();
//                    os.write(response.getBytes());
//                    os.close();
//                }
//            });//用MyHandler类内处理到/china的请求


            hs.setExecutor(null);//创建默认一个executor
            hs.start();


        } catch (IOException e) {
            // TODO: handle exception
        }

    }
}




