package co.com.pragma.model.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void defaultConstructor_ShouldCreateEmptyRole() {
        Role role = new Role();

        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
    }

    @Test
    void parameterizedConstructor_ShouldSetAllFields() {
        Long id = 1L;
        String name = "ADMIN";
        String description = "Administrator role";

        Role role = new Role(id, name, description);

        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void setId_ShouldUpdateIdField() {
        Role role = new Role();
        Long testId = 123L;

        role.setId(testId);

        assertEquals(testId, role.getId());
    }

    @Test
    void setName_ShouldUpdateNameField() {
        Role role = new Role();
        String testName = "MANAGER";

        role.setName(testName);

        assertEquals(testName, role.getName());
    }

    @Test
    void setDescription_ShouldUpdateDescriptionField() {
        Role role = new Role();
        String testDescription = "Manager role description";

        role.setDescription(testDescription);

        assertEquals(testDescription, role.getDescription());
    }

    @Test
    void builder_ShouldReturnRoleBuilderInstance() {
        Role.RoleBuilder builder = Role.builder();

        assertNotNull(builder);
        assertInstanceOf(Role.RoleBuilder.class, builder);
    }

    @Test
    void builder_WithAllFields_ShouldCreateRoleCorrectly() {
        Long id = 2L;
        String name = "EMPLOYEE";
        String description = "Employee role";

        Role role = Role.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void builder_WithPartialFields_ShouldCreateRoleCorrectly() {
        String name = "USER";

        Role role = Role.builder()
                .name(name)
                .build();

        assertNull(role.getId());
        assertEquals(name, role.getName());
        assertNull(role.getDescription());
    }

    @Test
    void builder_WithNoFields_ShouldCreateEmptyRole() {
        Role role = Role.builder().build();

        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
    }

    @Test
    void builderMethods_ShouldReturnBuilderInstance() {
        Role.RoleBuilder builder = Role.builder();

        assertSame(builder, builder.id(1L));
        assertSame(builder, builder.name("TEST"));
        assertSame(builder, builder.description("Test description"));
    }

    @Test
    void roleBuilder_ChainedCalls_ShouldWork() {
        Role role = Role.builder()
                .id(5L)
                .name("SUPERVISOR")
                .description("Supervisor role")
                .build();

        assertEquals(5L, role.getId());
        assertEquals("SUPERVISOR", role.getName());
        assertEquals("Supervisor role", role.getDescription());
    }

    @Test
    void role_WithNullValues_ShouldHandleCorrectly() {
        Role role = new Role(null, null, null);

        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
    }

    @Test
    void builder_WithNullValues_ShouldHandleCorrectly() {
        Role role = Role.builder()
                .id(null)
                .name(null)
                .description(null)
                .build();

        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
    }
}