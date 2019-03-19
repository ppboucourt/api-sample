package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.service.TokenValidationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by adriel on 8/3/17.
 */

@Service
@Transactional
public class TokenValidationServiceImpl implements TokenValidationService {

    @Inject
    private JHipsterProperties jHipsterProperties;

    private final Logger log = LoggerFactory.getLogger(TokenValidationServiceImpl.class);



    public TokenValidationServiceImpl() {

    }

    public JHipsterProperties getjHipsterProperties() {
        return jHipsterProperties;
    }

    public void setjHipsterProperties(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public boolean validateToken(String authToken) {

        String secretKey = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        long tokenValidityInSeconds = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        long tokenValidityInSecondsForRememberMe = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }
}
