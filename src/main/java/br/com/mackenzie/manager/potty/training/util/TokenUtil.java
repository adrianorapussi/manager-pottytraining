package br.com.mackenzie.manager.potty.training.util;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String extrairToken(HttpServletRequest req) {
        return req.getHeader("Authorization").split(" ")[1];
    }
}
