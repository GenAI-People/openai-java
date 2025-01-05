package com.genaipeople.openai.vector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.genaipeople.openai.FileTest;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.VectorStore;
import com.genaipeople.openai.VectorStoreFile;
import com.genaipeople.openai.assistant.StaticChunkingStrategy;
import com.genaipeople.openai.file.FileList;
import com.genaipeople.openai.file.FileObject;
import com.genaipeople.openai.file.FilePurpose;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class VectorStoreFileTest {
    private static VectorStore vectorStore;
    private static VectorStoreObject vectorStoreObject;
    private static VectorStoreFile vectorStoreFile;
    private static VectorStoreFileObject vectorStoreFileObject;
    @BeforeEach
    void setUp() {
        try {
            vectorStoreFile = new VectorStoreFile(OpenAI.API_KEY);
            vectorStore = new VectorStore(OpenAI.API_KEY);  
            vectorStoreObject = createVectorStore();
            vectorStoreFileObject = createVectorStoreFile();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to create vector store");
        }
    }

    @AfterEach
    void tearDown() {
        try {
            deleteAllVectorStoreFiles();
            deleteVectorStoreFile(vectorStoreFileObject.getId());
            VectorStoreTest.deleteVectorStore(vectorStoreObject.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VectorStoreObject createVectorStore() throws Exception {
        VectorCreateRequest request = new VectorCreateRequest();
        request.setName("Test Vector Store");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("purpose", "testing");
        request.setMetadata(metadata);

        return vectorStore.create(request).get();
    }

    private VectorStoreFileObject createVectorStoreFile() throws Exception {
        VectorStoreFileRequest request = new VectorStoreFileRequest();
        FileObject fileObject = listOrCreateFile();
        request.setFileId(fileObject.getId());
        StaticChunkingStrategy strategy = new StaticChunkingStrategy(); 
        strategy.setStaticSize(1000, 100);
        request.setChunkingStrategy(strategy);
        return vectorStoreFile.create(vectorStoreObject.getId(), request).get();
    }

    private FileObject listOrCreateFile() throws Exception {
        FileList fileList = FileTest.listFiles();
        if (fileList.getData().isEmpty()) {
            return FileTest.uploadFile(FilePurpose.ASSISTANTS);
        }
        return fileList.getData().get(0);
    }

    @Test
    void testCreateVectorStoreFile() throws Exception {
        VectorStoreFileRequest request = new VectorStoreFileRequest();
        FileObject fileObject = listOrCreateFile();
        request.setFileId(fileObject.getId());
        StaticChunkingStrategy strategy = new StaticChunkingStrategy(); 
        strategy.setStaticSize(1000, 100);
        request.setChunkingStrategy(strategy);

        VectorStoreFileObject result = vectorStoreFile.create(vectorStoreObject.getId(), request).get();
        
        assertNotNull(result);
        assertEquals(fileObject.getId(), result.getId());
        assertEquals("vector_store.file", result.getObject());
        assertEquals(vectorStoreObject.getId(), result.getVectorStoreId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getStatus());
        assertNotNull(result.getChunkingStrategy());
        FileTest.deleteFile(fileObject.getId());
    }

    @Test
    void testRetrieveVectorStoreFile() throws Exception {
        FileObject fileObject = listOrCreateFile();
        VectorStoreFileObject result = vectorStoreFile.retrieve(vectorStoreObject.getId(), fileObject.getId()).get();
        
        assertNotNull(result);
        assertEquals(fileObject.getId(), result.getId());
        assertEquals("vector_store.file", result.getObject());
        assertEquals(vectorStoreObject.getId(), result.getVectorStoreId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getStatus());
    }

    @Test
    void testListVectorStoreFiles() throws Exception {
        VectorStoreListResponse<VectorStoreFileObject> result = vectorStoreFile.list(5, "desc", 
            null, null, null, vectorStoreObject.getId()).get();

        assertNotNull(result);
        assertEquals(1, result.getData().size());   
        assertTrue(result.getData().contains(vectorStoreFileObject));
        if (!result.getData().isEmpty()) {
            VectorStoreFileObject file = result.getData().get(0);
            assertEquals("vector_store.file", file.getObject());
            assertEquals(vectorStoreObject.getId(), file.getVectorStoreId());
            assertNotNull(file.getCreatedAt());
        }
    }

    @Test
    void testDeleteVectorStoreFile() throws Exception {
        VectorStoreFileObject file = createVectorStoreFile();
        VectorStoreDeleteResponse result = vectorStoreFile.delete(vectorStoreObject.getId(), file.getId()).get();
        
        assertNotNull(result);
        assertEquals(file.getId(), result.getId());
        assertEquals("vector_store.file.deleted", result.getObject());
        assertTrue(result.getDeleted());
    }

    @Test
    void testCreateVectorStoreFileWithoutChunking() throws Exception {
        FileTest.listFiles().getData().forEach(file -> {
            try {
                FileTest.deleteFile(file.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        FileObject fileObject = listOrCreateFile();
        VectorStoreFileRequest request = new VectorStoreFileRequest();
        request.setFileId(fileObject.getId());  
            VectorStoreFileObject result = vectorStoreFile.create(vectorStoreObject.getId(), request).get();
        
        assertNotNull(result);
        assertEquals(fileObject.getId(), result.getId());
        assertEquals("vector_store.file", result.getObject());
        assertEquals(vectorStoreObject.getId(), result.getVectorStoreId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getStatus());
        deleteVectorStoreFile(fileObject.getId());
    }

    private void deleteVectorStoreFile(String fileId) {
        VectorStoreDeleteResponse result;
        try {
            result = vectorStoreFile.delete(vectorStoreObject.getId(), fileId).get();
            assertNotNull(result);
            assertEquals(fileId, result.getId());
            assertEquals("vector_store.file.deleted", result.getObject());
            assertTrue(result.getDeleted());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        }
    }

    private void deleteAllVectorStoreFiles() {
        VectorStoreListResponse<VectorStoreFileObject> response;
        try {
            response = vectorStoreFile.list(10, null, null, null, null, vectorStoreObject.getId()).get();
            response.getData().forEach(file -> {
                deleteVectorStoreFile(file.getId());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        }
    }
} 