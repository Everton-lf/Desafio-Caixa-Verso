package gov.caixa.invest.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class JwtGenerator {

    public String generate(String username, Set<String> roles) {

        return Jwt.issuer("invest-api")
                .upn(username)
                .groups(roles)
                .expiresIn(3600)
                .sign();
    }
}
