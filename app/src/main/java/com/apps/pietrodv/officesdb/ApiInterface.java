package com.apps.pietrodv.officesdb;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/office/all")
    Call<List<Office>> getAllOffices();

    @FormUrlEncoded
    @POST("/office/add")
    Call<Void> addOffice(@Field("title") String title, @Field("description") String description, @Field("price") String price, @Field("photo") String photo);

    @FormUrlEncoded
    @POST("/office/deleteById")
    Call<Void> deleteOfficeById(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("/office/add")
    Call<Void> updateOffice(@Field("id") Integer id, @Field("title") String title, @Field("description") String description, @Field("price") String price, @Field("photo") String photo);

    @Multipart
    @POST("/office/file/upload")
    Call<ResponseBody> uploadMultipartFile(@Part("keyname") RequestBody name , @Part MultipartBody.Part file);

    @DELETE("office/file/delete/{keyname}")
    Call<Void> deletePhotoS3(@Path("keyname") String keyname);

}