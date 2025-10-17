package work.foofish.networktodo.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MediatorLiveData;


import work.foofish.networktodo.models.dto.AuthResponse;
import work.foofish.networktodo.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository;

    // 使用一个内部的 MutableLiveData 来驱动外部的 LiveData
    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    // 用来通知 UI 登录成功事件
    private MutableLiveData<AuthResponse> _loginResult = new MutableLiveData<>();
    public LiveData<AuthResponse> loginResult = _loginResult;

    // 用来通知 UI 错误信息
    private MutableLiveData<String> _error = new MutableLiveData<>();
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
        final Observer<AuthResponse> observer = new Observer<AuthResponse>() {
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
}