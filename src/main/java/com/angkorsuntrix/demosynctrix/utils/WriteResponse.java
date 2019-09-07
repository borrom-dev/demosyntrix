package com.angkorsuntrix.demosynctrix.utils;

import com.angkorsuntrix.demosynctrix.exception.ResponseException;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteResponse {
    public static void writeErrorMessage(HttpServletResponse response, final String message, int status) throws IOException {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new Gson().toJson(new ResponseException(message, status)));
        out.flush();
    }
}
