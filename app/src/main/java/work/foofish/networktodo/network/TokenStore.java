package work.foofish.networktodo.network;

import android.content.Context;
import android.content.SharedPreferences;

public final class TokenStore {
    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";

    private static volatile String userId;
    private static volatile String token;
    private static volatile SharedPreferences preferences;

    private TokenStore() {}

    public static synchronized void init(Context context) {
        if (preferences == null) {
            preferences = context.getApplicationContext()
                    .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        if (token == null) {
            token = preferences.getString(KEY_TOKEN, null);
        }
        if (userId == null) {
            userId = preferences.getString(KEY_USER_ID, null);
        }
    }

    public static void setToken(String t) {
        token = t;
        persist(KEY_TOKEN, t);
    }

    public static String getToken() {
        if (token == null && preferences != null) {
            token = preferences.getString(KEY_TOKEN, null);
        }
        return token;
    }

    public static String getUserId() {
        if (userId == null && preferences != null) {
            userId = preferences.getString(KEY_USER_ID, null);
        }
        return userId;
    }

    public static void setUserId(String userId) {
        TokenStore.userId = userId;
        persist(KEY_USER_ID, userId);
    }

    public static void clear() {
        token = null;
        userId = null;
        if (preferences != null) {
            preferences.edit()
                    .remove(KEY_TOKEN)
                    .remove(KEY_USER_ID)
                    .apply();
        }
    }

    private static void persist(String key, String value) {
        if (preferences == null) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        if (value == null) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }
        editor.apply();
    }
}
