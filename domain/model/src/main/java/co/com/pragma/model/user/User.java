package co.com.pragma.model.user;

import co.com.pragma.model.role.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
    private LocalDateTime creationDate;
    private Long roleId;
    private Role role;

    public User() {
        this.creationDate = LocalDateTime.now();
    }

    public User(Long id, String firstName, String lastName, LocalDate birthDate,
                String address, String phone, String email, BigDecimal baseSalary,
                LocalDateTime creationDate, Long roleId, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.baseSalary = baseSalary;
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
        this.roleId = roleId;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public BigDecimal getBaseSalary() { return baseSalary; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public Long getRoleId() { return roleId; }
    public Role getRole() { return role; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public void setRole(Role role) { this.role = role; }

    // Builder pattern
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder toBuilder() {
        return new UserBuilder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .birthDate(this.birthDate)
                .address(this.address)
                .phone(this.phone)
                .email(this.email)
                .baseSalary(this.baseSalary)
                .creationDate(this.creationDate)
                .roleId(this.roleId)
                .role(this.role);
    }

    public static class UserBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String address;
        private String phone;
        private String email;
        private BigDecimal baseSalary;
        private LocalDateTime creationDate;
        private Long roleId;
        private Role role;

        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public UserBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public UserBuilder birthDate(LocalDate birthDate) { this.birthDate = birthDate; return this; }
        public UserBuilder address(String address) { this.address = address; return this; }
        public UserBuilder phone(String phone) { this.phone = phone; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder baseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; return this; }
        public UserBuilder creationDate(LocalDateTime creationDate) { this.creationDate = creationDate; return this; }
        public UserBuilder roleId(Long roleId) { this.roleId = roleId; return this; }
        public UserBuilder role(Role role) { this.role = role; return this; }

        public User build() {
            return new User(id, firstName, lastName, birthDate, address, phone, email,
                    baseSalary, creationDate, roleId, role);
        }
    }
}