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

/// 我在使用安卓原生开发,请你为这个TodoList写一份给学生培训使用的文档
/// 技术栈: MVVM + Java + XML开发,
/// 请你讲一下网络请求所需的所有知识, 网络请求的最佳实践. MVVM架构, 要一份很详细的文档, 我的学生只有一些基础的页面布局和组件只是, 没有其他前置知识, 所以请你详细写, 先给我一份大纲吧
