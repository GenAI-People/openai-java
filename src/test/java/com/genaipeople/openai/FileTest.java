package com.genaipeople.openai;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.genaipeople.openai.file.FileDeleteResponse;
import com.genaipeople.openai.file.FileDetails;
import com.genaipeople.openai.file.FileList;
import com.genaipeople.openai.file.FileListQuery;
import com.genaipeople.openai.file.FileObject;
import com.genaipeople.openai.file.FilePurpose;

public class FileTest {

    @Test
    public void testFileUpload() throws Exception {
        FileObject fileObject = uploadFile(FilePurpose.FINE_TUNE);
        assertNotNull(fileObject);
    }

    public static FileObject uploadFile(FilePurpose purpose) throws Exception {
        File file = new File(OpenAI.API_KEY);
        Path path = Paths.get("src/test/resources/file/file.json");
        FileDetails fileDetails = new FileDetails(
            path, 
            purpose
        );
        return file.upload(fileDetails).get();
    }   

    public static FileList listFiles() throws Exception {
        File file = new File(OpenAI.API_KEY);
        FileListQuery fileListQuery = new FileListQuery();
        return file.list(fileListQuery).get();
    }

    @Test
    public void testFileList() throws Exception {
        FileList fileList = listFiles();
        assertNotNull(fileList);
    }

    @Test
    public void testFileRetrieve() throws Exception {
        FileObject fileObject = uploadFile(FilePurpose.ASSISTANTS);
        if (fileObject == null) {
            assert false;
        }
        File file = new File(OpenAI.API_KEY);
        FileObject retrievedFileObject = file.retrieve(fileObject.getId()).get();
        assertNotNull(retrievedFileObject);
    }


    @Test
    public void testFileRetrieveContent() throws Exception {
        FileObject fileObject = uploadFile(FilePurpose.ASSISTANTS);
        if (fileObject == null) {
            assert false;
        }
        File file = new File(OpenAI.API_KEY);
        FileObject retrievedFileObject = file.retrieve(fileObject.getId()).get();
        String content = retrievedFileObject.getStatus();
        assertNotNull(content);
    }

    @Test
    public void testFileDelete() throws Exception {
        FileObject fileObject = uploadFile(FilePurpose.ASSISTANTS);
        if (fileObject == null) {
            assert false;
        }
        FileList fileList = listFiles();
        if (fileList == null || fileList.getData().isEmpty()) {
            assert false;
        }
        for (FileObject fileObject2 : fileList.getData()) {
            File file = new File(OpenAI.API_KEY);
            FileDeleteResponse fileDeleteResponse = file.delete(fileObject2.getId()).get();
            assertNotNull(fileDeleteResponse);
        }
    }

    public static void deleteFile(String fileId) throws Exception {
        File file = new File(OpenAI.API_KEY);
        file.delete(fileId).get();
    }
}