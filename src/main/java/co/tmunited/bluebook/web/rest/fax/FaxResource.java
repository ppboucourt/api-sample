package co.tmunited.bluebook.web.rest.fax;

import co.tmunited.bluebook.service.FaxSendLogService;
import co.tmunited.bluebook.service.TokenValidationService;
import co.tmunited.bluebook.service.impl.TokenValidationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * REST controller for managing Machine.
 */
@RestController
@RequestMapping("/fax")
public class FaxResource {

    private final Logger log = LoggerFactory.getLogger(FaxResource.class);

    @Inject
    private FaxSendLogService faxSendLogService;

    @Inject
    private TokenValidationService tokenValidationService;

    @RequestMapping("/pdf-download/{id}/{token}")
    public void downloadPDFResource(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable("id") Long id, @PathVariable("token") String token) throws IOException {

        log.info("Download pdf. Token Original: " + token);
        token = new String(Base64.getDecoder().decode(token.getBytes()));
        log.info("Download pdf. Token Decode: " + token);

        if (tokenValidationService.validateToken(token)) {
            byte[] fileStore = faxSendLogService.downloadFaxPdf(id);
            File tempFile = File.createTempFile("fax", ".pdf");
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);

            fos.write(Base64.getDecoder().decode(fileStore));
            fos.close();

            log.info("FILE: " + tempFile.getAbsolutePath());

            response.setContentType("application/pdf");
            Files.copy(tempFile.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } else {

            Files.copy(Paths.get("/dev/null"), response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}

