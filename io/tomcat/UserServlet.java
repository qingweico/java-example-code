package io.tomcat;

import util.Print;

import java.io.IOException;

/**
 * @author zqw
 * @date 2022/7/5
 */
public class UserServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            response.write("[get] user");
        } catch (IOException e) {
            Print.err(e.getMessage());
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            response.write("[post] user");
        } catch (IOException e) {
            Print.err(e.getMessage());
        }
    }
}
