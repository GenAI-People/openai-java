package com.genaipeople.openai.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDetails {
    @JsonProperty("file")
    private byte[] content;

    @JsonProperty("purpose")
    private String purpose;

    public FileDetails(Path filePath, FilePurpose purpose) throws IOException {
        this.content = Files.readAllBytes(filePath);
        this.purpose = purpose.getValue();
    }
}
