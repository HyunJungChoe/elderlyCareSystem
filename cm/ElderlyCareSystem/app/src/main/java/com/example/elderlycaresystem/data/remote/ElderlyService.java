package com.example.elderlycaresystem.data.remote;

import com.example.elderlycaresystem.data.elderly.ElderlyInfo;
import com.example.elderlycaresystem.data.info.ElderlyData;
import com.example.elderlycaresystem.data.login.LoginData;
import com.example.elderlycaresystem.data.login.LoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ElderlyService {
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginData loginData);

    @GET("users/logout")
    Call<ResponseBody> logout();

    @GET("devices")
    Call<List<ElderlyInfo>> getElderlyList(@Query("uid") String id);

    @GET("devices/{num}/data")
    Call<ElderlyData> getElderlyData(@Path("num") int key);

    @GET("http://192.168.1.22:3000/login")
    Call<LoginResponse> getTestLogin();


    @GET("http://192.168.1.22:3000/devices")
    Call<List<ElderlyInfo>> getTestList();

    @GET("http://192.168.1.22:3000/data")
    Call<ElderlyData> getTestData();

}
