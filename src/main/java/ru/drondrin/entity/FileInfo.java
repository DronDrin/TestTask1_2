package ru.drondrin.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class FileInfo {
    String id, name;
    Timestamp createdTimestamp, lastDownloadTimestamp;
}
