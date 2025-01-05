package com.genaipeople.openai.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDetails {

    @JsonIgnore
    private String filename;

    @JsonProperty("file")
    private byte[] content;

    @JsonProperty("purpose")
    private String purpose;

    public FileDetails(Path filePath, FilePurpose purpose) throws IOException {
        this.content = Files.readAllBytes(filePath);
        this.purpose = purpose.getValue();
        this.filename = filePath.getFileName().toString();
    }

    public String getFilename() {
        return filename;
    }
}
