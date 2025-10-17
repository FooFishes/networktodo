package work.foofish.networktodo.network;

public final class TokenStore {
    private static volatile String token;

    private TokenStore() {}

    public static void setToken(String t) {
        token = t;
    }

    public static String getToken() {
        return token;
    }

    public static void clear() {
        token = null;
    }
}

