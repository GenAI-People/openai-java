package com.genaipeople.openai.file;

public enum FileType {
    C("text/x-c", ".c"),
    CPP("text/x-c++", ".cpp"),
    CSHARP("text/x-csharp", ".cs"),
    CSS("text/css", ".css"),
    DOC("application/msword", ".doc"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"),
    GO("text/x-golang", ".go"),
    HTML("text/html", ".html"),
    JAVA("text/x-java", ".java"),
    JAVASCRIPT("text/javascript", ".js"),
    JSON("application/json", ".json"),
    MARKDOWN("text/markdown", ".md"),
    PDF("application/pdf", ".pdf"),
    PHP("text/x-php", ".php"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx"),
    PYTHON("text/x-python", ".py"),
    RUBY("text/x-ruby", ".rb"),
    SHELL("application/x-sh", ".sh"),
    TEX("text/x-tex", ".tex"),
    TYPESCRIPT("application/typescript", ".ts"),
    TEXT("text/plain", ".txt");

    private final String mimeType;
    private final String extension;

    FileType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType fromExtension(String extension) {
        for (FileType type : values()) {
            if (type.extension.equalsIgnoreCase(extension)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported file extension: " + extension);
    }

    public static FileType fromMimeType(String mimeType) {
        for (FileType type : values()) {
            if (type.mimeType.equalsIgnoreCase(mimeType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported MIME type: " + mimeType);
    }
} 