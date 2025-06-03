package ru.drondrin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.drondrin.dto.FileReadDto;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static ru.drondrin.Main.fileService;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String removed = req.getParameter("removed");
        if (Objects.equals(removed, "true")) {
            getServletContext().getRequestDispatcher("/static/downloadRemoved.jsp").forward(req, resp);
            return;
        }

        String id = req.getParameter("id");
        if (id == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else {
            Optional<FileReadDto> fileOptional = fileService.getFileAndUpdateLastDownload(id);
            if (fileOptional.isPresent())
                getServletContext().getRequestDispatcher("/static/download.jsp").forward(req, resp);
            else
                getServletContext().getRequestDispatcher("/static/downloadNotFound.jsp").forward(req, resp);
        }
    }
}
