package com.example.noapperance;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ChatApi {
    @GET("chats?userId=1")
    Call<List<ChatRequest>> getChats();
}