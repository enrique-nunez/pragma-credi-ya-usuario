package co.com.pragma.model.user;

import co.com.pragma.model.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("EMPLOYEE");

        testUser = User.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Pérez")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("Calle 123")
                .phone("1234567890")
                .email("juan@test.com")
                .baseSalary(new BigDecimal("1000000"))
                .roleId(1L)
                .role(testRole)
                .build();
    }

    @Test
    void getBirthDate_ShouldReturnCorrectDate() {
        LocalDate expectedDate = LocalDate.of(1990, 1, 1);
        assertEquals(expectedDate, testUser.getBirthDate());
    }

    @Test
    void getAddress_ShouldReturnCorrectAddress() {
        assertEquals("Calle 123", testUser.getAddress());
    }

    @Test
    void getPhone_ShouldReturnCorrectPhone() {
        assertEquals("1234567890", testUser.getPhone());
    }

    @Test
    void getCreationDate_ShouldReturnCreationDate() {
        assertNotNull(testUser.getCreationDate());
        assertTrue(testUser.getCreationDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void toBuilder_ShouldCreateBuilderWithSameValues() {
        User copiedUser = testUser.toBuilder().build();

        assertEquals(testUser.getId(), copiedUser.getId());
        assertEquals(testUser.getFirstName(), copiedUser.getFirstName());
        assertEquals(testUser.getLastName(), copiedUser.getLastName());
        assertEquals(testUser.getBirthDate(), copiedUser.getBirthDate());
        assertEquals(testUser.getAddress(), copiedUser.getAddress());
        assertEquals(testUser.getPhone(), copiedUser.getPhone());
        assertEquals(testUser.getEmail(), copiedUser.getEmail());
        assertEquals(testUser.getBaseSalary(), copiedUser.getBaseSalary());
        assertEquals(testUser.getCreationDate(), copiedUser.getCreationDate());
        assertEquals(testUser.getRoleId(), copiedUser.getRoleId());
        assertEquals(testUser.getRole(), copiedUser.getRole());
    }

    @Test
    void toBuilder_ModifyField_ShouldCreateNewUserWithModification() {
        User modifiedUser = testUser.toBuilder()
                .firstName("Carlos")
                .build();

        assertEquals("Carlos", modifiedUser.getFirstName());
        assertEquals(testUser.getLastName(), modifiedUser.getLastName());
        assertEquals(testUser.getEmail(), modifiedUser.getEmail());
    }

    @Test
    void constructor_WithNullCreationDate_ShouldSetCurrentTime() {
        User user = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "1234567890", "juan@test.com",
                new BigDecimal("1000000"), null, 1L, testRole);

        assertNotNull(user.getCreationDate());
        assertTrue(user.getCreationDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void constructor_WithSpecificCreationDate_ShouldUseProvidedDate() {
        LocalDateTime specificDate = LocalDateTime.of(2023, 1, 1, 10, 0);

        User user = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "1234567890", "juan@test.com",
                new BigDecimal("1000000"), specificDate, 1L, testRole);

        assertEquals(specificDate, user.getCreationDate());
    }
}