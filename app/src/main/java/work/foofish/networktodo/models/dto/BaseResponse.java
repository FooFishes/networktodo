package work.foofish.networktodo.models.dto;
import com.google.gson.annotations.SerializedName;


public class BaseResponse<T> {
    private int code;
    @SerializedName("msg")
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
