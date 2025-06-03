package ru.drondrin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.drondrin.dto.FileReadDto;
import ru.drondrin.entity.FileInfo;

import java.io.*;
import java.util.Optional;

import static ru.drondrin.Main.fileService;

public class FileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part userFile = req.getPart("userFile");
        String fileId = fileService.saveFile(userFile);
        req.setAttribute("fileId", fileId);
        var writer = resp.getWriter();
        writer.write(fileId);
        writer.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Optional<FileReadDto> dtoOptional = fileService.getFileAndUpdateLastDownload(id);
        if (dtoOptional.isPresent()) {
            var fileReadDto = dtoOptional.get();
            resp.setContentType("multipart/form-data");
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileReadDto.fileName());

            ServletOutputStream out = resp.getOutputStream();
            File file = fileReadDto.file();
            try (var in = new FileInputStream(file)) {
                byte[] bytes = new byte[128];
                int cnt;
                while ((cnt = in.read(bytes)) > 0)
                    out.write(bytes, 0, cnt);
                out.flush();
            }
        } else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
