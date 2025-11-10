package persistence;

import entities.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class JsonFileReaderTest {

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
