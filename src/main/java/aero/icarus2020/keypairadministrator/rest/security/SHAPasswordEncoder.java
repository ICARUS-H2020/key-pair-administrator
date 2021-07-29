package aero.icarus2020.keypairadministrator.rest.security;

import aero.icarus2020.keypairadministrator.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SHAPasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LogManager.getLogger(SHAPasswordEncoder.class);

    @Override
    public String encode(CharSequence cs) {
        return cs.toString();
    }

    /**
     * @return True if the password is matched otherwise false
     */
    @Override
    public boolean matches(CharSequence cs, String string) {

        return Util.createAlgorithm(cs.toString(), Util.ALGORITHM.SHA.toString()).equals(string);
    }

}
