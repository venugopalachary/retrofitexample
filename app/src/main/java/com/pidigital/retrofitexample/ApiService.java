package com.pidigital.retrofitexample;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/b7b0914b97e40cc673b5")
    Call<ImageModel> getImageUrl();
}
