package gov.caixa.invest.security;

import java.util.Map;
import java.util.Set;

public class UserStore {


    public static final Map<String, String> USERS = Map.of(
            "admin", "admin123",
            "user", "user123"
    );


    public static final Map<String, Set<String>> ROLES = Map.of(
            "admin", Set.of("admin", "user"),
            "user", Set.of("user")
    );
}
