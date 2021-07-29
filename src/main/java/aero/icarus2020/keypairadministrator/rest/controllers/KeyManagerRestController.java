package aero.icarus2020.keypairadministrator.rest.controllers;



import aero.icarus2020.keypairadministrator.rest.services.KeyManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


@Transactional(rollbackOn = Exception.class)
@RestController
@RequestMapping("/api/v1/get-key")
public class KeyManagerRestController {

    @Autowired
    KeyManagerService keyManagerService;

    /**
     *
     */
    @RequestMapping(value = "/dataasset/{did}/user/{uid}", method = RequestMethod.GET)
    public ResponseEntity getKey(@PathVariable("did") Long did, @PathVariable("uid") Long uid, HttpServletRequest request) {
        return keyManagerService.getKey(did, uid, request);
    }

    /**
     *
     */
    @RequestMapping(value = "/secure-space/{mid}", method = RequestMethod.GET)
    public ResponseEntity getKeyForSecureSpace(@PathVariable("mid") Long mid) {
        return keyManagerService.getKeyForSecureSpace(mid);
    }


}//EoC
