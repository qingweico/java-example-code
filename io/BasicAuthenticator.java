package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * {@link Authenticator} {@link PasswordAuthentication} 用于处理 HTTP 认证相关
 *
 * @author zqw
 * @date 2025/9/6
 */
class BasicAuthenticator {
    public static void main(String[] args) throws MalformedURLException {
        Authenticator.setDefault(new SimpleAuthenticator("admin", "admin"));

        URL url = new URL("https://httpbin.org/basic-auth/admin/admin");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static class SimpleAuthenticator extends Authenticator {
        private final String username;
        private final String password;

        public SimpleAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }

}
