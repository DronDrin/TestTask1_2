package ru.drondrin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

import static ru.drondrin.Main.fileService;

public class FileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part userFile = req.getPart("userFile");
        String fileId = fileService.saveFile(userFile);
        System.out.println(fileId);
    }
}
