package work.foofish.networktodo.network;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitProvider {
    // TODO: Replace with your real base URL or move to BuildConfig.
    private static final String BASE_URL = "https://todo.foofish.work/";

    private static volatile Retrofit retrofit;

    private RetrofitProvider() {}

    private static OkHttpClient createClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String token = TokenStore.getToken();
                if (token != null && !token.isEmpty()) {
                    Request withAuth = original.newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(withAuth);
                }
                return chain.proceed(original);
            }
        };

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitProvider.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(createClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static UserService getUserService() {
        return getRetrofit().create(UserService.class);
    }
}

