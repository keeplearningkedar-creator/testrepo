package com.example.demo.model;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        //User user = new User("John Doe", "john.doe@example.com");
        //assertEquals("John Doe", user.getName());
        //assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(1L);
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");

        assertEquals(1L, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
    }

    @Test
    void testDefaultValues() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
    }
}
