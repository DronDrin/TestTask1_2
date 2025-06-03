package ru.drondrin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.drondrin.Main.fileService;

public class FileRemoveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else
            fileService.deleteFile(id);
    }
}
