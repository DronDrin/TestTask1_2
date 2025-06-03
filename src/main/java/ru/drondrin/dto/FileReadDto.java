package ru.drondrin.dto;

import java.io.File;

public record FileReadDto(String fileName, File file) {
}
