package com.tecsup.apaza.healmepaciente.services;

import com.tecsup.apaza.healmepaciente.models.Doctor;
import com.tecsup.apaza.healmepaciente.models.Office;
import com.tecsup.apaza.healmepaciente.models.ResponseMessage;
import com.tecsup.apaza.healmepaciente.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("user")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("user")
    Call<ResponseMessage> createDoctor(@Field("email") String email,
                                         @Field("password") String password,
                                         @Field("name") String name,
                                         @Field("lastname") String lastname,
                                         @Field("phone") String phone,
                                         @Field("gender_id") int gender_id,
                                         @Field("user_type") String user_type,
                                         @Field("document_type") String document_type,
                                         @Field("identity_document") String identity_document);

    @FormUrlEncoded
    @POST("login_general")
    Call<User> loginUser(@Field("email") String email,
                                    @Field("password") String password);


    @GET("office")
    Call<List<Office>> getOffices();

    @GET("doctor_all")
    Call<List<Doctor>> getDoctors();

    @GET("doctor")
    Call<List<Doctor>> getDoctorsConnected();

    @GET("doctor/{id}")
    Call<Doctor> showDoctor(@Path("id") Integer id);

    @GET("user/{id}")
    Call<User> showUser(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("patient_doctor/{id}")
    Call<ResponseMessage> doctorRating(@Path("id") Integer id,
                                       @Field("doctor_id") Integer doctor_id,
                                       @Field("valorated") Integer valorated);





}
