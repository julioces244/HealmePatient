package com.tecsup.apaza.healmepaciente;

//import com.tecsup.apaza.healmepaciente.Class.Doctor;
import com.tecsup.apaza.healmepaciente.Class.Doctor;
import com.tecsup.apaza.healmepaciente.Class.Office;
import com.tecsup.apaza.healmepaciente.Class.ResponseMessage;
import com.tecsup.apaza.healmepaciente.Class.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://52.45.158.42";

    @GET("api/user")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("/api/user")
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
    @POST("/api/login_general")
    Call<User> loginUser(@Field("email") String email,
                                    @Field("password") String password);


    @GET("api/office")
    Call<List<Office>> getOffices();

    @GET("api/doctor_all")
    Call<List<Doctor>> getDoctors();

    @GET("api/doctor")
    Call<List<Doctor>> getDoctorsConnected();

    @GET("api/doctor/{id}")
    Call<Doctor> showDoctor(@Path("id") Integer id);

    @GET("api/user/{id}")
    Call<User> showUser(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("api/patient_doctor/{id}")
    Call<ResponseMessage> doctorRating(@Path("id") Integer id,
                                       @Field("doctor_id") Integer doctor_id,
                                       @Field("valorated") Integer valorated);





}
