package aero.icarus2020.keypairadministrator.rest.security;

import aero.icarus2020.keypairadministrator.rest.entities.Role;
import aero.icarus2020.keypairadministrator.rest.entities.User;
import aero.icarus2020.keypairadministrator.rest.entities.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final AccountStatusUserDetailsChecker detailsChecker =
            new AccountStatusUserDetailsChecker();

    private final String USER_NOT_FOUND = "User: %s has not been found";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt to fetch user from database
        if (username.equals("decryption")) {
            //create demo user
            User user = new User(Long.parseLong("1"), "decryption", "2cd80b3aee678eebb8d3d7ec08f0d52e7d70e22d");
            UserRole userRole = new UserRole();
            userRole.setId(Long.parseLong("1"));
            Role role = new Role();
            role.setId(Long.parseLong("1"));
            role.setName("SUPERADMIN");
            userRole.setRole(role);
            List<UserRole> userRoles = new ArrayList<>();
            userRoles.add(userRole);
            user.setUserRoles(userRoles);
            //end of create demo user
            Set<GrantedAuthority> roles =
                    user.getRolesAsStringList()
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());
            // Create a new authenticated user
            org.springframework.security.core.userdetails.User authenticatedUser =
                    new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            true,
                            true,
                            true,
                            true,
                            roles);
            // Check Details of current user
            detailsChecker.check(authenticatedUser);
            return authenticatedUser;
        } else {
            logger.error(String.format(USER_NOT_FOUND, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }


    }
}
