package co.tmunited.bluebook.service.util;

import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.client.FileRestClient;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating random Strings.
 */
public class StorageUtil {

    public static Boolean storeFileToServer(URL restUrl, FileDTO fileDTO) {

        try {

            if (FileRestClient.getInstance().uploadFile(restUrl, fileDTO)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean storeFileToLocal(String path, FileDTO fileDTO) {

        try {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateFolderName() {

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
