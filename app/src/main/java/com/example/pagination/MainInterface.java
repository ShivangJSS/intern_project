package com.example.pagination;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
    @GET("posts")
    Call<List<MainData>> getPosts(
            @Query("_start") int start,
            @Query("_limit") int limit
    );
}
