package aero.icarus2020.keypairadministrator.rest.entities;


import java.util.ArrayList;
import java.util.List;

public class User {


    private Long id;

    private String username;

    private String password;

    private List<UserRole> userRoles;

    public User() {

    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<String> getRolesAsStringList() {

        List<String> userRoleSTR = new ArrayList<>();

        if (this.getUserRoles() != null && !this.getUserRoles().isEmpty()) {

            this.getUserRoles()
                    .stream()
                    .forEach(
                            userRole -> {
                                userRoleSTR.add(userRole.getRole().getName());
                            });

            return userRoleSTR;
        } else {

            return null;
        }
    }
}
