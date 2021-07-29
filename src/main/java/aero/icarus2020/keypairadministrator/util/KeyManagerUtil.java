package aero.icarus2020.keypairadministrator.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Service
public final class KeyManagerUtil {

    private static final Logger logger = LogManager.getLogger(KeyManagerUtil.class);

    @Qualifier("webApplicationContext")
    @Autowired
    ResourceLoader resourceLoader;

    public ResponseEntity<String> checkUser(String serverURL, Long id) {

        ResponseEntity<String> response = null;
        BufferedReader br = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource model = resourceLoader.getResource("classpath:config.json");
            br = new BufferedReader(new InputStreamReader(model.getInputStream(), "UTF-8"));
            JsonNode jsonNode = objectMapper.readTree(br);
            String token = jsonNode.get("token").asText();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Token", token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            response = restTemplate.exchange(serverURL + "/api/v1/key-pair/user/" +
                    id, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error(e);
            logger.error("Exception Check user: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }//EoM

    public ResponseEntity<String> checkDataset(String serverURL, Long id) {
        ResponseEntity<String> response = null;
        BufferedReader br = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource model = resourceLoader.getResource("classpath:config.json");
            br = new BufferedReader(new InputStreamReader(model.getInputStream(), "UTF-8"));
            JsonNode jsonNode = objectMapper.readTree(br);
            String token = jsonNode.get("token").asText();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Token", token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            response = restTemplate.exchange(serverURL + "/api/v1/key-pair/dataset/" +
                    id, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error(e);
            logger.error("Exception Check dataset: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }

    public ResponseEntity<String> checkAuthorizationContext(String serverURL, Long id) {
        ResponseEntity<String> response = null;
        BufferedReader br = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource model = resourceLoader.getResource("classpath:config.json");
            br = new BufferedReader(new InputStreamReader(model.getInputStream(), "UTF-8"));
            JsonNode jsonNode = objectMapper.readTree(br);
            String token = jsonNode.get("token").asText();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Token", token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            response = restTemplate.exchange(serverURL + "/api/v1/key-pair/check-context/" +
                    id, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error(e);
            logger.error("Exception checkAuthorizationContext: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }

    public ResponseEntity<String> getSecretKey(String serverURL, Long id) {
        ResponseEntity<String> response = null;
        BufferedReader br = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource model = resourceLoader.getResource("classpath:config.json");
            br = new BufferedReader(new InputStreamReader(model.getInputStream(), "UTF-8"));
            JsonNode jsonNode = objectMapper.readTree(br);
            String token = jsonNode.get("token").asText();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Token", token);
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            response = restTemplate.exchange(serverURL + "/api/v1/key-pair/key/" +
                    id, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error(e);
            logger.error("Exception Get Secret key: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return response;
    }
}//EoC
