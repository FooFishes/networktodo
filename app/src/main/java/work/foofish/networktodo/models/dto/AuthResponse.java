package work.foofish.networktodo.models.dto;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("username")
    private String username;
    @SerializedName("userId")
    private String id;
    @SerializedName("accessToken")
    private String token;

    public AuthResponse(String username, String id, String token) {
        this.username = username;
        this.id = id;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
