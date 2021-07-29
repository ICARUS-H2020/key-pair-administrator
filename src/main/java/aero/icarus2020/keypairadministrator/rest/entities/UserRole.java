package aero.icarus2020.keypairadministrator.rest.entities;

import java.io.Serializable;

public class UserRole implements Serializable {

    private Long id;

    private User user;

    private Role role;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}//EoC
