package com.genaipeople.openai;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.file.FileDeleteResponse;
import com.genaipeople.openai.file.FileDetails;
import com.genaipeople.openai.file.FileList;
import com.genaipeople.openai.file.FileListQuery;
import com.genaipeople.openai.file.FileObject;
import com.genaipeople.openai.service.RestClient;
import com.genaipeople.openai.service.RestClient.HttpMethod;

public class File {
    private final String apiKey;
    private final String FILE_URL = "https://api.openai.com/v1/files";
    private final ObjectMapper mapper = new ObjectMapper();

    public File(String apiKey) {
        this.apiKey = apiKey;
    }

    public CompletableFuture<FileObject> upload(FileDetails fileDetails) {
        return CompletableFuture.supplyAsync(() -> 
            {
                try {
                    return RestClient.makeAsyncFileRequest(apiKey, FILE_URL, HttpMethod.POST, fileDetails);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(mapper.readValue(responseString, FileObject.class));
            } catch (InterruptedException e) {
                return CompletableFuture.failedFuture(new RuntimeException("Request interrupted", e));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(new RuntimeException(e.getMessage()));
            }
        });
    }

    public CompletableFuture<FileList> list(FileListQuery query) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, FILE_URL, HttpMethod.GET, query)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                System.out.println("Response: " + responseString);
                return CompletableFuture.completedFuture(
                    mapper.readValue(responseString, FileList.class)
                );
            } catch (Exception e) {
                e.printStackTrace();
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<FileObject> retrieve(String fileId) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, FILE_URL + "/" + fileId, HttpMethod.GET, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(mapper.readValue(responseString, FileObject.class));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<FileDeleteResponse> delete(String fileId) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, FILE_URL + "/" + fileId, HttpMethod.DELETE, null)
        ).thenCompose((res) -> {
            try {
                String responseString = res.get();
                return CompletableFuture.completedFuture(mapper.readValue(responseString, 
                    FileDeleteResponse.class));
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    public CompletableFuture<byte[]> retrieveContent(String fileId) {
        return CompletableFuture.supplyAsync(() -> 
            RestClient.makeAsyncRequest(apiKey, FILE_URL + "/" + fileId + "/content", HttpMethod.GET, null)
        ).thenCompose((res) -> {
            try {
                return CompletableFuture.completedFuture(res.get().getBytes());
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        });
    }
}
