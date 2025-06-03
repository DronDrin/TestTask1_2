package ru.drondrin.service;

import jakarta.servlet.http.Part;
import ru.drondrin.dto.FileReadDto;
import ru.drondrin.entity.FileInfo;
import ru.drondrin.repository.FileInfoRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
        String filePath = getFilePath(id);

        bucket.mkdirs();
        new File(filePath).createNewFile();
        file.write(filePath);

        return id;
    }

    public Optional<FileReadDto> getFile(String id) {
        return fileInfoRepository.findById(id).map(fi -> new FileReadDto(fi.getName(),
                        new File(getFilePath(id))));
    }

    private String getFilePath(String id) {
        return bucket.getAbsolutePath() + File.separator + id;
    }

    public void deleteFile(String id) {
        fileInfoRepository.findById(id).ifPresent(fi -> {
            new File(getFilePath(id)).delete();
            fileInfoRepository.deleteById(id);
        });
    }
}
