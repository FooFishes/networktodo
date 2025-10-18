package work.foofish.networktodo.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import work.foofish.networktodo.models.dto.AuthResponse;
import work.foofish.networktodo.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    // 使用一个内部的 MutableLiveData 来驱动外部的 LiveData
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    // 用来通知 UI 登录成功事件
    private final MutableLiveData<AuthResponse> _loginResult = new MutableLiveData<>();
    public LiveData<AuthResponse> loginResult = _loginResult;

    private final MutableLiveData<AuthResponse> _registerResult = new MutableLiveData<>();
    public LiveData<AuthResponse> registerResult = _registerResult;

    // 用来通知 UI 错误信息
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public LoginViewModel() {
        this.userRepository = new UserRepository();
    }

    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            _error.setValue("Username and password cannot be empty.");
            return;
        }

        _isLoading.setValue(true);

        final LiveData<AuthResponse> source = userRepository.login(username, password);
        final Observer<AuthResponse> observer = new Observer<>() {
            @Override
            public void onChanged(AuthResponse loginResponse) {
                _isLoading.setValue(false);
                if (loginResponse != null && loginResponse.getToken() != null) {
                    _loginResult.setValue(loginResponse);
                } else {
                    _error.setValue("Login failed. Please check your credentials.");
                }
                // remove to avoid leaks
                source.removeObserver(this);
            }
        };
        source.observeForever(observer);
    }

    public void register(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            _error.setValue("Username and password cannot be empty.");
            return;
        }

        _isLoading.setValue(true);

        final LiveData<AuthResponse> source = userRepository.register(username, password);
        final Observer<AuthResponse> observer = new Observer<>() {
            @Override
            public void onChanged(AuthResponse registerResponse) {
                _isLoading.setValue(false);
                if (registerResponse != null && registerResponse.getToken() != null) {
                    _registerResult.setValue(registerResponse);
                } else {
                    _error.setValue("Register failed. Please check your credentials.");
                }
                // remove to avoid leaks
                source.removeObserver(this);
            }
        };
        source.observeForever(observer);
    }
}