package io.tomcat;

/**
 * @author zqw
 * @date 2022/7/5
 */
abstract class Servlet {
    /**
     * doGet
     * @param request {@link Request}
     * @param response {@link Response}
     */
    public abstract void doGet(Request request, Response response);
    /**
     * doPost
     * @param request {@link Request}
     * @param response {@link Response}
     */
    public abstract void doPost(Request request, Response response);

    public void service(Request request, Response response) {
        HttpRequestType method = request.getMethod();
        switch (method) {
            case HTTP_REQUEST_GET: {
                doGet(request, response);
                break;
            }
            case HTTP_REQUEST_POST: {
                doPost(request, response);
                break;
            }
            default:break;
        }
    }
}
