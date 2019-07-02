package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tecsup.apaza.healmepaciente.models.ResponseMessage;
import com.tecsup.apaza.healmepaciente.errors.ErrorResponse;
import com.tecsup.apaza.healmepaciente.errors.ErrorValidationModel;
import com.tecsup.apaza.healmepaciente.services.ApiService;
import com.tecsup.apaza.healmepaciente.services.ApiServiceGenerator;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText name;
    EditText lastname;
    EditText email;
    EditText password;
    EditText identity_document;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.edittext_name);
        lastname = findViewById(R.id.edittext_lastname);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        identity_document = findViewById(R.id.edittext_dni);
        phone = findViewById(R.id.edittext_phone);


    }

    public void gologin(View view){
        Intent intent = new Intent(RegisterActivity.this,
                LoginActivity.class);
        startActivity(intent);
    }

    public void callRegisterDoctor(View view){

        String rdocument_type = "DNI";
        String user_type = "PATIENT";
        String rname = name.getText().toString();
        String rlastname = lastname.getText().toString();
        String remail = email.getText().toString();
        String rpassword = password.getText().toString();
        String ridentity_document = identity_document.getText().toString();
        String rphone = phone.getText().toString();

        if (!isCorrectParameter()) {
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = service.createDoctor(remail, rpassword, rname, rlastname,
                rphone,1,user_type, rdocument_type, ridentity_document);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMessage> call, @NonNull Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);

                        Toast.makeText(RegisterActivity.this, "Usuario registrado!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();

                    } else {
                        ErrorResponse apiError = ErrorValidationModel.parseError(response);
                        if (apiError.errors.containsKey("identity_document"))
                            identity_document.setError("Parece que el DNI ya esta registrado");
                        if (apiError.errors.containsKey("email"))
                            email.setError("Parece que el email ya esta registrado");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMessage> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }


    private Boolean isCorrectParameter ()
     {

        if (email.getText().toString().isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

            if (email.getText().toString().isEmpty()) email.setError("Necesita un correo electrónico");
            else email.setError("Correo electrónico inválido");
            return false;

        } else {

            if (password.getText().toString().isEmpty()
                    || !Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S).{6,}")
                    .matcher(password.getText().toString()).matches()) {

                if (password.getText().toString().isEmpty()) password.setError("Necesita una contraseña");
                else password.setError("Necesita 6 caracteres alfanuméricos");
                return false;

            } else {
                if (name.getText().toString().isEmpty()) {

                    name.setError("Coloque sus nombres");
                    return false;

                } else {
                    if (lastname.getText().toString().isEmpty()) {

                        lastname.setError("Coloque sus apellidos");
                        return false;

                    } else {
                        if (phone.getText().toString().isEmpty()
                                || !Pattern.compile("^(9[0-9]{8})$").matcher(phone.getText().toString()).matches()) {

                            if (phone.getText().toString().isEmpty()) phone.setError("Necesita un número de celular");
                            else phone.setError("El número de teléfono no tiene el formato adecuado");
                            return false;

                        } else {
                            if (identity_document.getText().toString().isEmpty()
                                    || !Pattern.compile("^([1-9][0-9]{7})$").
                                    matcher(identity_document.getText().toString()).matches()) {

                                if (identity_document.getText().toString().isEmpty())
                                    identity_document.setError("Es necesario contar con un DNI");
                                else  identity_document.setError("El DNI no cuenta con 8 dígitos");

                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

}
