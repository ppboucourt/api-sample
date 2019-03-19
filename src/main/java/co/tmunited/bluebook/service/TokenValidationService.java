package co.tmunited.bluebook.service;

/**
 * Created by adriel on 8/3/17.
 */
public interface TokenValidationService {

    boolean validateToken(String authToken);
}
