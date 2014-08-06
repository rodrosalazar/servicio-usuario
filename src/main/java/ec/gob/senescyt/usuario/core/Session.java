package ec.gob.senescyt.usuario.core;

public class Session {
    private final String identity;
    private final String accessToken;

    public Session(String username) {
        this.identity = username;
        this.accessToken = "token123";
    }


    public String getIdentity() {
        return identity;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
