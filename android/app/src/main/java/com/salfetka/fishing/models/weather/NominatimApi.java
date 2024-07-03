package com.salfetka.fishing.models.weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NominatimApi {
    @GET("search")
    Call<List<Address>> search(
            @Query("city") String city,
            @Query("country") String country,
            @Query("format") String format,
            @Query("addressdetails") int addressdetails,
            @Query("limit") int limit,
            @Query("accept-language") String language
    );
}
