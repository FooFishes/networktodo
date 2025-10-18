package work.foofish.networktodo.network;

public final class TokenStore {
    private static volatile String userId;
    private static volatile String token;

    private TokenStore() {}

    public static void setToken(String t) {
        token = t;
    }

    public static String getToken() {
        return token;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        TokenStore.userId = userId;
    }

    public static void clear() {
        token = null;
        userId = null;
    }
}

