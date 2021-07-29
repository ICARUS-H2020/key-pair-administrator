package aero.icarus2020.keypairadministrator.exception.user;

public class UsernameNotFoundException extends Exception {

    private final String username;

    public UsernameNotFoundException(String email) {
        this.username = (null == email ? "" : email);
    }

    @Override
    public String getMessage() {
        return "Invalid username: '" + username + "'";
    }

}
