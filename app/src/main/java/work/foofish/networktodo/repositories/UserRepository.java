package work.foofish.networktodo.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import work.foofish.networktodo.models.dto.BaseResponse;
import work.foofish.networktodo.models.dto.AuthRequest;
import work.foofish.networktodo.models.dto.AuthResponse;
import work.foofish.networktodo.network.RetrofitProvider;
import work.foofish.networktodo.network.TokenStore;
import work.foofish.networktodo.network.UserService;

public class UserRepository {
    private UserService userService;

    public UserRepository() {
        userService = RetrofitProvider.getUserService();
    }

    public LiveData<AuthResponse> login(String username, String password) {
        final MutableLiveData<AuthResponse> loginResponseData = new MutableLiveData<>();
        AuthRequest loginRequest = new AuthRequest(username, password);
        userService.login(loginRequest).enqueue(new Callback<BaseResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AuthResponse>> call, Response<BaseResponse<AuthResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse data = response.body().getData();
                    if (data != null && data.getToken() != null) {
                       TokenStore.setToken(data.getToken());
                    }
                    loginResponseData.setValue(data);
                } else {
                    loginResponseData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthResponse>> call, Throwable t) {
                loginResponseData.setValue(null);
            }
        });
        return loginResponseData;
    }

    public LiveData<AuthResponse> register(String username, String password) {
        final MutableLiveData<AuthResponse> registerResponseData = new MutableLiveData<>();
        AuthRequest registerRequest = new AuthRequest(username, password);
        userService.register(registerRequest).enqueue(new Callback<BaseResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AuthResponse>> call, Response<BaseResponse<AuthResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse data = response.body().getData();
                    if (data != null && data.getToken() != null) {
                        TokenStore.setToken(data.getToken());
                    }
                    registerResponseData.setValue(data);
                } else {
                    registerResponseData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthResponse>> call, Throwable t) {
                registerResponseData.setValue(null);
            }
        });
        return registerResponseData;
    }
}
