package com.tecsup.apaza.healmepaciente.errors;

import com.tecsup.apaza.healmepaciente.services.ApiServiceGenerator;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorValidationModel {

    public static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                ApiServiceGenerator.retrofit
                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }

        return error;
    }

}
