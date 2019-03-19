package co.tmunited.bluebook.service.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by adriel on 6/27/17.
 */
public class Base64Utils {

   public static  String decode(String text){

       String encoded = new StringBuilder(text ).toString();

       if(text == null){
           return "";
       }else{
           return new String(Base64.getDecoder().decode(text.trim().getBytes(StandardCharsets.UTF_8)));
       }
   }
}
