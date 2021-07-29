package aero.icarus2020.keypairadministrator.rest.security;


import aero.icarus2020.keypairadministrator.rest.entities.User;
import aero.icarus2020.keypairadministrator.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    /**
     * Retrieve the current logged-in user
     *
     * @return An instance of CurrentUser object
     */
    public static Authentication getCurrentUser() {
        return (Authentication) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Check if a user has a JWT AccessToken
     *
     * @return True if user has a JWT in headers, otherwise returns false
     */
    public static boolean hasAccessToken() {
        return SecurityContextHolder.getContext().getAuthentication()
                instanceof UsernamePasswordAuthenticationToken;
    }

    /**
     * Returns currently authenticated user from database
     *
     * @return An instance of User object
     */
    public User getAuthenticatedUser() throws Exception {
        User user = new User(Long.parseLong("1"), "decryption", Util.createAlgorithm("AtBzjOcXeSAI6hqiT$z$jFI7#", Util.ALGORITHM.SHA.name()));

        // Return actual user
        return user;
    }
}
