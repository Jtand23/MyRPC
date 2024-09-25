package server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{

    /**
     * 启动服务器
     *
     * @param port
     */
    public void doStart(int port) {

        //创建Vert.x示例
        Vertx vertx = Vertx.vertx();
        //创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        //监听端口并处理请求
        server.requestHandler(request -> {
            //处理HTTP请求
            System.out.println("接收到请求："+request.method()+" "+request.uri());

            //发送HTTP响应
            request.response()
                    .putHeader("content-type","text/plain")
                    .end("Hello from Vert.x HTTP server!");
        });

        //启动HTTP服务器并监听制定端口
        server.listen(port, result->{
            if(result.succeeded()) {
                System.out.println("HTTP服务器启动成功，监听端口："+port);
            }else {
                System.out.println("HTTP服务器启动失败："+result.cause());
            }
        });
    }
}
