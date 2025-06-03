package ru.drondrin.service;

import jakarta.servlet.http.Part;
import ru.drondrin.entity.FileInfo;
import ru.drondrin.repository.FileInfoRepository;

import java.io.File;
import java.io.IOException;

public class FileService {
    private final File bucket;
    private final FileInfoRepository fileInfoRepository;

    public FileService(File bucket, FileInfoRepository fileInfoRepository) {
        this.bucket = bucket;
        this.fileInfoRepository = fileInfoRepository;
    }

    /**
     * @return id of the saved file
     */
    public String saveFile(Part file) throws IOException {
        FileInfo fileInfo = fileInfoRepository.save(file.getSubmittedFileName());
        String id = fileInfo.getId();
        String filePath = bucket.getAbsolutePath() + File.separator + id;

        bucket.mkdirs();
        new File(filePath).createNewFile();
        file.write(filePath);

        return id;
    }
}
