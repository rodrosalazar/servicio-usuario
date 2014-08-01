package ec.gob.senescyt.usuario.core;

import java.util.UUID;

public class Session {
    private final String identity;
    private final String accessToken;

    public Session(String username) {
        this.identity = username;
//        this.accessToken = UUID.randomUUID().toString().substring(0, 23);
        this.accessToken = "token123";
    }


    public String getIdentity() {
        return identity;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
