package com.github.httply;

import com.github.httply.retra.annotations.Call;
import com.github.httply.retra.annotations.GET;

import java.util.List;

public interface FoodApi {
    // The path should be relative to the base URL
    @GET("v1/861a8605-a6e0-408d-8feb-ab303b15f59f")
    Call<List<FoodItem>> getFoodItems();
}