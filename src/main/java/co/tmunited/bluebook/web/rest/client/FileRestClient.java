package co.tmunited.bluebook.web.rest.client;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.tools.rest.RestClient;
import co.tmunited.tools.rest.exception.NotAuthenticatedException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * Created by hermeslm on 1/23/17.
 * <p>
 * FileRestClient singleton class, used for communicating with Jhipster server application
 */
@Service
public class FileRestClient {

    private static FileRestClient instance = null;
    private String userName;
    private String authorities;
    private String secretKey;
    private Integer validity = 18000;
    private URL serverUrl;

    protected FileRestClient() {
        //Just can't instanciate this constructor
    }

    public static FileRestClient getInstance() {
        if (instance == null) {
            instance = new FileRestClient();
        }
        return instance;
    }

    /**
     * Get a Rest FileRestClient instance, creating it if is not instanced,
     * this is a singleton class
     *
     * @return FileRestClient instance
     */
//    public static FileRestClient init(String username, String authorities) {
//        if (instance == null) {
//            instance = new FileRestClient();
//        }
//
//        instance.setUserName(username);
//        instance.setAuthorities(authorities);
//        return instance;
//    }

    /**
     * Get a Rest FileRestClient instance, creating it if is not instanced,
     * this is a singleton class
     *
     * @return FileRestClient instance
     */
    public FileRestClient init(String username, String authorities, String secretKey, Integer validity) {
        if (instance == null) {
            instance = new FileRestClient();
        }

        instance.setUserName(username);
        instance.setAuthorities(authorities);
        instance.setSecretKey(secretKey);
        instance.setValidity(validity);

        RestClient.getInstance().init(username, authorities, secretKey, validity);

        return instance;
    }

    public ResponseEntity<Resource> getDownloadFileEntityResponse(String restFileUrl) {
        try {
            FileDTO fileDTO = getFile(new URL(restFileUrl));

            ByteArrayResource resource = new ByteArrayResource(fileDTO.getFile().getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileDTO.getFileContentType()));
            headers.setContentDispositionFormData("attachment", "file_result.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setContentLength(resource.getByteArray().length);

            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean uploadFile(URL url, FileDTO fileDTO) throws NotAuthenticatedException, IOException, JSONException {

        Gson gson = new Gson();
        String payload = gson.toJson(fileDTO);

        ResponseEntity<String> responseEntity = RestClient.
            getInstance().getResponseEntity(url, HttpMethod.POST, new JSONObject(payload));

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return true;
        }
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new NotAuthenticatedException();
        }
        return false;
    }

    public FileDTO getFile(URL url) throws NotAuthenticatedException, IOException {

        ResponseEntity<String> responseEntity = RestClient.
            getInstance().getResponseEntity(url, HttpMethod.GET, new JSONObject());

        FileDTO fileDTO = null;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ObjectMapper mapper = new ObjectMapper();
            fileDTO = mapper.readValue(responseEntity.getBody(),
                new TypeReference<FileDTO>() {
                });
        }
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new NotAuthenticatedException();
        }
        return fileDTO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public File downloadFile(JHipsterProperties jHipsterProperties, String uuid) throws IOException, NotAuthenticatedException {

        File tmp = null;
        tmp = File.createTempFile("temp_", ".pdf");
        tmp.deleteOnExit();

        FileDTO fileDTO = FileRestClient.getInstance().getFile(new URL(
            jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + uuid));

        FileOutputStream fout = new FileOutputStream(tmp);
        fout.write(Base64.getDecoder().decode(fileDTO.getFile().getBytes()));
        fout.flush();
        fout.close();

        return tmp;
    }
}
