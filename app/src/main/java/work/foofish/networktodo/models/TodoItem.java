package work.foofish.networktodo.models;

import com.google.gson.annotations.SerializedName;

public class TodoItem {

    @SerializedName("id")
    private int id;
    @SerializedName("content")
    private String content;
    @SerializedName("status")
    private boolean status;

    public TodoItem(int id, String content, boolean status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
