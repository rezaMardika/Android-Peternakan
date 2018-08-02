package com.example.root.ternaknesia;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by reza on 21/11/17.
 */

public interface RequestInterface {

    @GET("android/jsonandroid")
    Call<JSONRespon> getJSON() ;
}
