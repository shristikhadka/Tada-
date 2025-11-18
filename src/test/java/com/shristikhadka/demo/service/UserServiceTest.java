package com.shristikhadka.demo.service;

import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for UserService
 * 
 * This test class demonstrates how to test a service that depends on other components.
 * We use Mockito to mock the dependencies (UserRepository and PasswordEncoder).
 * 
 * Testing Pattern: Arrange -> Act -> Assert (AAA Pattern)
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // @Mock: Creates a mock object for UserRepository
    // We don't use the real database, we mock it!
    @Mock
    private UserRepository userRepository;

    // @Mock: Creates a mock object for PasswordEncoder
    // We don't use the real password encoder, we mock it!
    @Mock
    private PasswordEncoder passwordEncoder;

    // @InjectMocks: Creates an instance of UserService and injects the mocks
    // This is the class we're testing!
    @InjectMocks
    private UserService userService;

    // Test data - reusable test objects
    private User testUser;
    private User savedUser;

    /**
     * Setup method runs before each test
     * This is where we prepare test data
     */
    @BeforeEach
    void setUp() {
        // Create a test user (input)
        testUser = new User();
        testUser.setUserName("testuser");
        testUser.setPassword("plainpassword123");

        // Create a saved user (what the repository returns)
        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUserName("testuser");
        savedUser.setPassword("encodedpassword123");
        savedUser.setRoles(Arrays.asList("USER"));
    }

    /**
     * Test: saveNewUser() - Success Case
     * 
     * This test verifies that:
     * 1. Password is encoded before saving
     * 2. User role is set to "USER"
     * 3. User is saved to repository
     * 4. Saved user is returned
     * 
     * Testing Pattern: Arrange -> Act -> Assert
     */
    @Test
    void testSaveNewUser_Success() {
        // ========== ARRANGE ==========
        // Setup: Tell mocks what to return when called
        
        // When passwordEncoder.encode() is called with any string,
        // return "encodedpassword123"
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword123");

        // When userRepository.save() is called with any User,
        // return the savedUser object
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // ========== ACT ==========
        // Execute: Call the method we're testing
        User result = userService.saveNewUser(testUser);

        // ========== ASSERT ==========
        // Verify: Check that everything happened correctly

        // 1. Verify the result is not null
        assertNotNull(result, "Result should not be null");

        // 2. Verify the user was saved with correct username
        assertEquals("testuser", result.getUserName(), "Username should match");

        // 3. Verify the password was encoded
        // We check that passwordEncoder.encode() was called with the plain password
        verify(passwordEncoder, times(1)).encode("plainpassword123");

        // 4. Verify the user was saved with "USER" role
        assertNotNull(result.getRoles(), "Roles should not be null");
        assertEquals(1, result.getRoles().size(), "Should have one role");
        assertTrue(result.getRoles().contains("USER"), "Should have USER role");

        // 5. Verify the user was saved to repository
        // We check that userRepository.save() was called exactly once
        verify(userRepository, times(1)).save(any(User.class));

        // 6. Verify the saved user has an ID (meaning it was saved)
        assertNotNull(result.getId(), "Saved user should have an ID");
        assertEquals(1L, result.getId(), "User ID should be 1");
    }

    /**
     * Test: saveNewUser() - Exception Case
     * 
     * This test verifies that:
     * 1. If repository.save() throws an exception, it's caught and rethrown
     * 2. A RuntimeException is thrown with message "Failed to register user"
     * 
     * Testing Pattern: Arrange -> Act -> Assert (with exception)
     */
    @Test
    void testSaveNewUser_RepositoryException() {
        // ========== ARRANGE ==========
        // Setup: Make the repository throw an exception
        
        when(passwordEncoder.encode(anyString())).thenReturn("encodedpassword123");
        
        // When userRepository.save() is called, throw an exception
        when(userRepository.save(any(User.class)))
                .thenThrow(new RuntimeException("Database error"));

        // ========== ACT & ASSERT ==========
        // Execute and verify: Check that exception is thrown
        
        // Assert that saveNewUser() throws a RuntimeException
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.saveNewUser(testUser),
            "Should throw RuntimeException when repository fails"
        );

        // Verify the exception message
        assertEquals("Failed to register user", exception.getMessage(), 
            "Exception message should match");

        // Verify that passwordEncoder was called (password encoding attempted)
        verify(passwordEncoder, times(1)).encode(anyString());

        // Verify that repository.save() was called (save was attempted)
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Test: getUserByUsername() - Success Case
     * 
     * This test verifies that:
     * 1. User is retrieved from repository by username
     * 2. User is returned correctly
     * 
     * Testing Pattern: Arrange -> Act -> Assert
     */
    @Test
    void testGetUserByUsername_Success() {
        // ========== ARRANGE ==========
        // Setup: Tell repository what to return
        
        when(userRepository.findByUserName("testuser")).thenReturn(savedUser);

        // ========== ACT ==========
        // Execute: Call the method we're testing
        User result = userService.getUserByUsername("testuser");

        // ========== ASSERT ==========
        // Verify: Check that everything happened correctly

        // 1. Verify the result is not null
        assertNotNull(result, "Result should not be null");

        // 2. Verify the username matches
        assertEquals("testuser", result.getUserName(), "Username should match");

        // 3. Verify the repository was called with correct username
        verify(userRepository, times(1)).findByUserName("testuser");
    }

    /**
     * Test: getUserByUsername() - User Not Found
     * 
     * This test verifies that:
     * 1. If user is not found, RuntimeException is thrown
     * 2. Exception message is "User not found"
     * 
     * Testing Pattern: Arrange -> Act -> Assert (with exception)
     */
    @Test
    void testGetUserByUsername_UserNotFound() {
        // ========== ARRANGE ==========
        // Setup: Make repository return null (user not found)
        
        when(userRepository.findByUserName("nonexistent")).thenReturn(null);

        // ========== ACT & ASSERT ==========
        // Execute and verify: Check that exception is thrown
        
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.getUserByUsername("nonexistent"),
            "Should throw RuntimeException when user not found"
        );

        // Verify the exception message
        assertEquals("User not found", exception.getMessage(), 
            "Exception message should match");

        // Verify that repository was called
        verify(userRepository, times(1)).findByUserName("nonexistent");
    }
}

