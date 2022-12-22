package jcip;

import annotation.ThreadSafe;

import javax.servlet.*;
import java.math.BigInteger;

/**
 * A stateless Servlet
 *
 * @author zqw
 * @date 2021/4/7
 */
@ThreadSafe
public class StatelessFactorizer extends GenericServlet implements Servlet {

    // It does not contain any fields, nor does it contain any references to fields in other classes,
    // so stateless objects are thread-safe!

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }
    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
