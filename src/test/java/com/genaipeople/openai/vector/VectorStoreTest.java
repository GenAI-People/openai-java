package com.genaipeople.openai.vector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.genaipeople.openai.OpenAI;
import com.genaipeople.openai.VectorStore;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;

public class VectorStoreTest {
    private static VectorStore vectorStore;
    private static VectorStoreObject vectorStoreObject;

    @BeforeEach
    void setUp() {
        try {
            vectorStore = new VectorStore(OpenAI.API_KEY);  
            vectorStoreObject = createVectorStore();
        } catch (Exception e) {
            fail("Failed to create vector store");
        }
    }

    @AfterEach
    void tearDown() {
        if(vectorStoreObject != null) {
            deleteAllVectorStores();
        }
    }

    @Test
    void testListVectorStores() throws Exception {
        VectorStoreListResponse<VectorStoreObject> response = vectorStore.list(10, null, null, null).get();
        assertEquals(1, response.getData().size());
        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals("list", response.getObject());
        assertNotNull(response.getFirstId());
        assertNotNull(response.getLastId());
        assertNotNull(response.getHasMore());
        
        if (!response.getData().isEmpty()) {
            VectorStoreObject file = response.getData().get(0);
            assertEquals("vector_store", file.getObject());
            assertNotNull(file.getCreatedAt());
            assertNotNull(file.getStatus());
        }
    }

    @Test
    void testRetrieveVectorStore() throws Exception {
        VectorStoreObject retrievedStore = vectorStore.retrieve(vectorStoreObject.getId()).get();
        
        assertNotNull(retrievedStore);
        assertNotNull(retrievedStore.getId());
        assertEquals("vector_store", retrievedStore.getObject());
        assertNotNull(retrievedStore.getCreatedAt());
        assertNotNull(retrievedStore.getStatus());
        assertNotNull(retrievedStore.getFileCounts());
        
        VectorStoreObject.FileCounts counts = retrievedStore.getFileCounts();
        assertNotNull(counts.getCompleted());
        assertNotNull(counts.getInProgress());
        assertNotNull(counts.getFailed());
    }

    @Test
    void testCreateVectorStore() throws Exception {
        VectorStoreObject store = createVectorStore();
        
        assertNotNull(store);
        assertNotNull(store.getId());
        assertEquals("vector_store", store.getObject());
        assertEquals("Test Vector Store", store.getName());
        assertEquals("testing", store.getMetadata().get("purpose"));
        assertNotNull(store.getCreatedAt());
        deleteVectorStore(store.getId());
    }

    public static VectorStoreObject createVectorStore() throws Exception {
        VectorCreateRequest request = new VectorCreateRequest();
        request.setName("Test Vector Store");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("purpose", "testing");
        request.setMetadata(metadata);

        return vectorStore.create(request).get();
    }

    @Test
    void testUpdateVectorStore() throws Exception {
        VectorUpdateRequest request = new VectorUpdateRequest();
        request.setName("Updated Vector Store");
        
        VectorStoreObject updateResponse = vectorStore.update(vectorStoreObject.getId(), request).get();
        
        assertNotNull(updateResponse);
        assertEquals(vectorStoreObject.getId(), updateResponse.getId());
        assertEquals("Updated Vector Store", updateResponse.getName());
        assertNotNull(updateResponse.getCreatedAt());
    }

    @Test
    void testDeleteVectorStore() throws Exception {
        VectorStoreObject deleteStore = createVectorStore();
        VectorStoreDeleteResponse deleteResponse = deleteVectorStore(deleteStore.getId());
        assertNotNull(deleteResponse);
        assertEquals(deleteStore.getId(), deleteResponse.getId());
        assertEquals("vector_store.deleted", deleteResponse.getObject());
        assertTrue(deleteResponse.getDeleted());
    }

    @Test
    void testDeleteAllVectorStores() throws Exception {
        deleteAllVectorStores();
        VectorStoreListResponse<VectorStoreObject> response = vectorStore.list(10, null, null, null).get();
        assertEquals(0, response.getData().size());
    }

    public static VectorStoreDeleteResponse deleteVectorStore(String vectorStoreId) {
        try {
            return vectorStore.delete(vectorStoreId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            fail("Failed to delete vector store");
        }
        return null;
    }

    private void deleteAllVectorStores() {
        VectorStoreListResponse<VectorStoreObject> response;
        try {
            response = vectorStore.list(10, null, null, null).get();
            response.getData().forEach(store -> {
                deleteVectorStore(store.getId());
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