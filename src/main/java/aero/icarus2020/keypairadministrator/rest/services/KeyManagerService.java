package aero.icarus2020.keypairadministrator.rest.services;


import aero.icarus2020.keypairadministrator.util.KeyManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class KeyManagerService {

    private static final Logger logger = LogManager.getLogger(KeyManagerService.class);

    @Value("${icarus.pae.url}")
    String paeURL;

    @Value("${icarus.server.url}")
    String serverURL;

    @Autowired
    KeyManagerUtil keyManagerUtil;


    public ResponseEntity getKey(Long did, Long uid, HttpServletRequest request) {
        try {
            ResponseEntity<String> response = keyManagerUtil.checkUser(serverURL, uid);
            if (response.getStatusCode().equals(500)) {
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (response.getBody().equals("NOT_FOUND")) {
                return new ResponseEntity("User id NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            response = keyManagerUtil.checkDataset(serverURL, did);
            if (response.getStatusCode().equals(500)) {
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (response.getBody().equals("NOT_FOUND")) {
                return new ResponseEntity("Dataset id NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            response = keyManagerUtil.checkAuthorizationContext(serverURL, did);
            if (response.getStatusCode().equals(500)) {
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (response.getBody().equals("NOT_FOUND")) {
                response = keyManagerUtil.getSecretKey(serverURL, did);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity(response.getBody(), HttpStatus.OK);
            } else {
//                String paeResponse = (String) authorizationService.authorizeIcarusRequest( "accessDataset",
//                        String.valueOf(did), String.valueOf(uid), "GET", request);
//                if (null != paeResponse && !paeResponse.isEmpty()) {
//                    if (paeResponse.equals("ALLOW")) {
//                        response = keyManagerUtil.getSecretKey(serverURL, did);
//                        if (!response.getStatusCode().is2xxSuccessful()) {
//                            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
//                        }
                        return new ResponseEntity(response.getBody(), HttpStatus.OK);
//                    }
//                }
            }
//            return new ResponseEntity(response.getBody(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }//EoM Create

    public ResponseEntity getKeyForSecureSpace(Long mid) {

        try {
            ResponseEntity<String> response = keyManagerUtil.checkDataset(serverURL, mid);
            if (response.getStatusCode().equals(500)) {
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (response.getBody().equals("NOT_FOUND")) {
                return new ResponseEntity("Dataset id NOT_FOUND", HttpStatus.NOT_FOUND);
            }
            response = keyManagerUtil.getSecretKey(serverURL, mid);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }//EoM Create

}
