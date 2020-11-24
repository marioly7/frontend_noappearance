package com.example.noapperance;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UserApi {
    @GET("userRequest?userId=1")
    Call <List<UserRequest>> getPosts();
}
