package work.foofish.networktodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import work.foofish.networktodo.databinding.ActivityLoginBinding;
import work.foofish.networktodo.network.TokenStore;
import work.foofish.networktodo.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        if (TokenStore.getToken() != null) {
            navigateToMain();
            return; // 结束当前 onCreate，防止后续代码执行
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Edge-to-edge 内边距适配：为根布局动态叠加系统栏（状态栏/导航栏）insets
        final View root = binding.getRoot();
        final int paddingStart = root.getPaddingStart();
        final int paddingTop = root.getPaddingTop();
        final int paddingEnd = root.getPaddingEnd();
        final int paddingBottom = root.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 为状态栏与底部系统栏增加安全内边距，避免内容被状态栏/ActionBar 或手势区遮挡
            v.setPaddingRelative(paddingStart, paddingTop + sysBars.top, paddingEnd, paddingBottom + sysBars.bottom);
            return insets; // 不消费，交给子视图按需继续处理
        });

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupObservers();

        binding.rgAuthMode.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLogin) {
                binding.layoutLogin.setVisibility(View.VISIBLE);
                binding.layoutRegister.setVisibility(View.GONE);
            } else {
                binding.layoutLogin.setVisibility(View.GONE);
                binding.layoutRegister.setVisibility(View.VISIBLE);
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etLoginEmail.getText().toString();
            String password = binding.etLoginPassword.getText().toString();
            viewModel.login(username, password);
        });

        binding.btnRegister.setOnClickListener(v -> {
            String username = binding.etRegUsername.getText().toString();
            String password = binding.etRegPassword.getText().toString();
            String confirmPassword = binding.etRegConfirmPassword.getText().toString();
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.register(username, password);
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 销毁登录页，防止用户按返回键回到登录页
    }

    private void setupObservers() {
        viewModel.isLoading.observe(this, isLoading -> {
            if (binding.rgAuthMode.getCheckedRadioButtonId() == R.id.rbRegister) {
                binding.registerProgressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.btnRegister.setEnabled(!isLoading);
            } else {
                binding.loginProgressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.btnLogin.setEnabled(!isLoading);
            }
        });

        viewModel.error.observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.loginResult.observe(this, loginResponse -> {
            if (loginResponse != null) {
                // 2. 提示成功
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                // 3. 跳转到主页
                navigateToMain();
            }
        });

        viewModel.registerResult.observe(this, loginResponse -> {
            if (loginResponse != null) {
                // 2. 提示成功
                Toast.makeText(this, "Register Successful!", Toast.LENGTH_SHORT).show();
                // 3. 跳转到主页
                navigateToMain();
            }
        });
    }
}