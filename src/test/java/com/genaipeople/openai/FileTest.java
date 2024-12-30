package com.genaipeople.openai;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.genaipeople.openai.file.FileDeleteResponse;
import com.genaipeople.openai.file.FileDetails;
import com.genaipeople.openai.file.FileList;
import com.genaipeople.openai.file.FileListQuery;
import com.genaipeople.openai.file.FileObject;
import com.genaipeople.openai.file.FilePurpose;

public class FileTest {
    private static final String API_KEY = "API_KEY";

    @Test
    public void testFileUpload() throws Exception {
        FileObject fileObject = uploadFile();
        assertNotNull(fileObject);
    }

    private FileObject uploadFile() throws Exception {
        File file = new File(API_KEY);
        FileDetails fileDetails = new FileDetails(
            java.nio.file.Path.of("src/test/resources/file/file.jsonl"), 
            FilePurpose.FINE_TUNE
        );
        return file.upload(fileDetails).get();
    }   

    private FileList listFiles() throws Exception {
        File file = new File(API_KEY);
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
        FileObject fileObject = uploadFile();
        if (fileObject == null) {
            assert false;
        }
        File file = new File(API_KEY);
        FileObject retrievedFileObject = file.retrieve(fileObject.getId()).get();
        assertNotNull(retrievedFileObject);
    }


    @Test
    public void testFileRetrieveContent() throws Exception {
        FileObject fileObject = uploadFile();
        if (fileObject == null) {
            assert false;
        }
        File file = new File(API_KEY);
        FileObject retrievedFileObject = file.retrieve(fileObject.getId()).get();
        String content = retrievedFileObject.getStatus();
        assertNotNull(content);
    }

    @Test
    public void testFileDelete() throws Exception {
        FileObject fileObject = uploadFile();
        if (fileObject == null) {
            assert false;
        }
        FileList fileList = listFiles();
        if (fileList == null || fileList.getData().isEmpty()) {
            assert false;
        }
        for (FileObject fileObject2 : fileList.getData()) {
            File file = new File(API_KEY);
            FileDeleteResponse fileDeleteResponse = file.delete(fileObject2.getId()).get();
            assertNotNull(fileDeleteResponse);
        }
    }
}
