package com.apps.pietrodv.workspaceofficesdb;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/office/all")
    Call <List<Office>> getAllOffices();

    @FormUrlEncoded
    @POST("/office/add")
    Call<Void> addOffice(@Field("title") String title, @Field("description") String description, @Field("price") String price);

    @FormUrlEncoded
    @POST("/office/deleteById")
    Call<Void> deleteOfficeById(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("/office/add")
    Call<Void> updateOffice(@Field("id") Integer id, @Field("title") String title, @Field("description") String description, @Field("price") String price);

}
