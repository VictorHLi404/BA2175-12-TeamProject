package persistence;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFileDataStoreTest {

    @Test
    void testSaveAndLoadUser() {
        DataStore store = new JsonFileDataStore(true);

        User u = new User("alice", "123456");

        store.saveUser(u);

        User loaded = store.loadUser("alice");

        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
        assertEquals("123456", loaded.getPassword());
    }
    @Test
    void testCategoryMappingExists() {
        DataStore store = new JsonFileDataStore();
        Map<Integer, String> idMapping = store.getIdToCategoryMapping();
        assertNotNull(idMapping);
        assertEquals("General Knowledge", idMapping.get(9));
        Map<String, Integer> categoryMapping = store.getCategoryToIdMapping();
        assertNotNull(categoryMapping);
        assertEquals(9, categoryMapping.get("General Knowledge"));
    }
}
