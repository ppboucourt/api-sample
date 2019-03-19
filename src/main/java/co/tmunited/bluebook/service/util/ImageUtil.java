package co.tmunited.bluebook.service.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by adriel on 7/5/17.
 */
public class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public static String convertPicture(String base64Image, String formatName, int width) {

        File file = null;
        String converted = null;
        try {
            file = writePictureToTempFile(base64Image, formatName);
            converted = createThumbnailByImageMagick(file, formatName, width);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        return converted;
    }


    private static File writePictureToTempFile(String base64, String formatName) throws IOException {

        BufferedWriter writer = null;
        java.io.File tempFile = null;
        try {

            tempFile = java.io.File.createTempFile("picture", "." + formatName);

            // create a buffered image
            BufferedImage image = null;
            byte[] imageByte;

            imageByte = java.util.Base64.getDecoder().decode(base64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();

            // write the image to a file
            ImageIO.write(image, formatName, tempFile);


        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {

                ex.printStackTrace();
                throw ex;
            }

        }
        return tempFile;
    }


    private static String readFileToByteArray(File file) throws IOException {
        log.debug("Reading: " + file.getAbsolutePath());
        return Base64.encodeBase64String(FileUtils.readFileToByteArray(file));
    }

    private static String createThumbnailByImageMagick(File sourceImage, String fileFormat, int width) throws IOException {

        String imageName = sourceImage.getName();
        String sourceFileName = sourceImage.getAbsolutePath();

        String destinationFileName = sourceImage.getParent() + "/" + width + "_" + imageName;
        File thumbNailFile = new File(destinationFileName);

        resizeImage(sourceFileName, width, thumbNailFile.getAbsolutePath());

        return  readFileToByteArray(thumbNailFile);
    }

    private static boolean resizeImage(String from, long size, String to) {

        String convert_path = "/usr/bin/convert";
        ProcessBuilder pb = new ProcessBuilder(
            convert_path, "-background", "white", "-gravity", "center", from, "-resize", size + "x" + size, "-extent", size + "x" + size, to);

        pb.redirectErrorStream(true);

        try {

            Process process = pb.start();
            BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while (inStreamReader.readLine() != null) {
               inStreamReader.readLine();
            }
            return true;

        } catch (Exception e) {
            log.debug(e.getMessage());
            return false;
        }

    }
}
