package persistence;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFileDataStoreTest {

    private static final String USERS_FILE = "data/users.json";

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(Paths.get(USERS_FILE));
    }

    @Test
    void testSaveAndLoadUser() {
        DataStore store = new JsonFileDataStore();

        User u = new User("alice", "123456");

        store.saveUser(u);

        User loaded = store.loadUser("alice");

        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
        assertEquals("123456", loaded.getPassword());
    }
}
