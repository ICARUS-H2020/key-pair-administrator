package aero.icarus2020.keypairadministrator.rest.security;

import aero.icarus2020.keypairadministrator.util.Util;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public final class TokenHandler {

    private final String signerSecret;
    private final String TOKEN_ISSUER = "icarus-keypairmanager-app";
    private final String CLAIM_ROLE_NAME = "role";
    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // One day
    private static final String ROLES_DELIMITER = ",";
    private static final Logger logger = LogManager.getLogger(TokenHandler.class);


    public TokenHandler(String signerSecret) {
        this.signerSecret = signerSecret;
    }

    /**
     * Returns a UserAuthantication object containing the principal and the role
     * of the authenticated user.
     *
     * @param token The encrypted token to be parsed
     * @return UserAuthentication object
     */
    public UsernamePasswordAuthenticationToken parseUserFromToken(
            String token, HttpServletRequest httpRequest) {
        // Trim "Bearer " prefix from token
        if (token.startsWith("Bearer")) {
            token = token.substring(7, token.length());
        }
        try {
            // Parse JWT
            SignedJWT jwt = JWTSecurityHandler.parseSignedJWT(token, signerSecret, httpRequest);
            // Check if the token has not exprired yet
            if (Date.from(Instant.ofEpochMilli(System.currentTimeMillis())).getTime()
                    < jwt.getJWTClaimsSet().getExpirationTime().getTime()) {
                // Set the roles of current user
                Set<GrantedAuthority> roles
                        = Arrays.asList(
                        ((String) jwt.getJWTClaimsSet().getClaim(CLAIM_ROLE_NAME))
                                .split(ROLES_DELIMITER))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(toSet());
                aero.icarus2020.keypairadministrator.rest.entities.User user =
                        new aero.icarus2020.keypairadministrator.rest.entities.User(Long.parseLong("1"), "decryption",
                                Util.createAlgorithm("vsauhsdavuhasiudvh", Util.ALGORITHM.SHA.name()));
                if (!user.getUsername().equals(jwt.getJWTClaimsSet().getSubject())) {
                    return new UsernamePasswordAuthenticationToken(
                            jwt.getJWTClaimsSet().getSubject(), "", roles);
                }

                if (null != user) {
                    // Token is not expired, return valid Authentication
                    return new UsernamePasswordAuthenticationToken(
                            jwt.getJWTClaimsSet().getSubject(), "", roles);
                }
            } else {// Token is expired
                logger.warn("Token has been expired for user: " + jwt.getJWTClaimsSet().getSubject());
            }
        } catch (ParseException | JOSEException | SignatureNotVerifiedException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Creates a signed & encrypted JWT.
     *
     * @param user The user object which the ClaimSet will be build on
     * @return An encrypted JWT for the specific user
     */
    public String createTokenForUser(User user) {
        // Try to create a JWT for specific user
        try {
            logger.info(
                    String.format(
                            "Trying to create JWT for user: %s and role(s): %s",
                            user.getUsername(),
                            user.getAuthorities()
                                    .stream()
                                    .map(auth -> auth.getAuthority())
                                    .collect(Collectors.joining(ROLES_DELIMITER))));
            // Prepare JWT with claims set
            JWTClaimsSet jwtClaims
                    = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issueTime(new Date())
                    .issuer(TOKEN_ISSUER)
                    .jwtID(UUID.randomUUID().toString())
                    .expirationTime(
                            Date.from(
                                    Instant.ofEpochMilli(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)))
                    .claim(
                            CLAIM_ROLE_NAME,
                            user.getAuthorities()
                                    .stream()
                                    .map(auth -> "ROLE_".concat(auth.getAuthority()))
                                    .collect(Collectors.joining(ROLES_DELIMITER)))
                    .build();
            return JWTSecurityHandler.createSignedToken(signerSecret, jwtClaims);
        } catch (NoSuchAlgorithmException | JOSEException | ParseException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
}
