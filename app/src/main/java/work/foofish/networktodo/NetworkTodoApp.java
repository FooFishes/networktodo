package work.foofish.networktodo;

import android.app.Application;

import work.foofish.networktodo.network.TokenStore;

public class NetworkTodoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TokenStore.init(this);
    }
}
