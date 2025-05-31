package com.example.alexandriafrontend.api;

import com.example.alexandriafrontend.response.LibroResponse;
import com.example.alexandriafrontend.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {

    @GET("/api/usuarios/login")
    Call<LoginResponse> login(
            @Query("email") String email,
            @Query("contrasena") String contrasena
    );

    @FormUrlEncoded
    @POST("/api/usuarios/registrar")
    Call<ResponseBody> registrar(
            @Field("primerNombre") String primerNombre,
            @Field("segundoNombre") String segundoNombre,
            @Field("email") String email,
            @Field("contrasena") String contrasena,
            @Field("role") String role
    );

    @GET("api/libros/todos")
    Call<List<LibroResponse>> obtenerTodosLibros();

    @GET("/api/libros/buscar")
    Call<List<LibroResponse>> buscarLibros(@Query("texto") String texto);
}
