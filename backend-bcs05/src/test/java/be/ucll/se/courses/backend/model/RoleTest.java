package be.ucll.se.courses.backend.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    void testToGrantedAuthority() {
        for (Role role : Role.values()) {
            GrantedAuthority authority = role.toGrantedAuthority();
            assertThat(authority).isInstanceOf(SimpleGrantedAuthority.class);
            assertThat(authority.getAuthority()).isEqualTo("ROLE_" + role.name());
        }
    }

    @Test
    void testToString() {
        assertThat(Role.ADMIN.toString()).isEqualTo("admin");
        assertThat(Role.STUDENT.toString()).isEqualTo("student");
        assertThat(Role.LECTURER.toString()).isEqualTo("lecturer");
        assertThat(Role.GUEST.toString()).isEqualTo("guest");
    }
}
