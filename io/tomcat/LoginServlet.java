package io.tomcat;

import java.io.IOException;

/**
 * @author zqw
 * @date 2022/7/5
 */
@SuppressWarnings("unused")
class LoginServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            response.write("[get] login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            response.write("[post] login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
