package work.foofish.networktodo.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private String id;
    @SerializedName("username")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String id, String username, String token) {
        this.id = id;
        this.username = username;
    }
}
