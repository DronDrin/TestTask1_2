package ru.drondrin.service;

import jakarta.servlet.http.Part;
import ru.drondrin.dto.FileReadDto;
import ru.drondrin.entity.FileInfo;
import ru.drondrin.repository.FileInfoRepository;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;
import static ru.drondrin.Main.CONFIG;

public class FileService implements Closeable {
    private final File bucket;
    private final FileInfoRepository fileInfoRepository;
    private Thread removeOldTask;

    public FileService(File bucket, FileInfoRepository fileInfoRepository) {
        this.bucket = bucket;
        this.fileInfoRepository = fileInfoRepository;
        startRemoveOldTask();
    }

    private void startRemoveOldTask() {
        removeOldTask = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    removeOldFiles();
                    Thread.sleep(Duration.of(CONFIG.intProperty("file.auto-remove.check-interval"), SECONDS));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        removeOldTask.start();
    }

    private void removeOldFiles() {
        long removeThreshold = CONFIG.intProperty("file.auto-remove.lifetime") * 1000L;
        for (String s : fileInfoRepository.getOldFiles(removeThreshold)) {
            System.out.println("Removing old file: " + s);
            deleteFile(s);
        }
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

    @Override
    public void close() {
        if (removeOldTask != null)
            removeOldTask.interrupt();
    }

    public Optional<FileReadDto> getFileAndUpdateLastDownload(String id) {
        fileInfoRepository.updateLastDownload(id);
        return getFile(id);
    }
}
