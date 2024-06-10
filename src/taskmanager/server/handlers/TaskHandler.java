package taskmanager.server.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        switch (exchange.getRequestMethod()) {
            case "GET": {
//                handleGetPosts(exchange);
                break;
            }
            case "POST": {
//                handleGetComments(exchange);
                break;
            }
            case "DELETE": {
//                handlePostComments(exchange);
                break;
            }
            default:
                sendNotFound(exchange);
        }
    }
}
