package work.foofish.networktodo.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import work.foofish.networktodo.models.dto.BaseResponse;
import work.foofish.networktodo.models.dto.AuthRequest;
import work.foofish.networktodo.models.dto.AuthResponse;

public interface UserService {
    @POST("/auth/login")
    Call<BaseResponse<AuthResponse>> login(@Body AuthRequest request);

    @POST("/auth/register")
    Call<BaseResponse<AuthResponse>> register(@Body AuthRequest request);
}
