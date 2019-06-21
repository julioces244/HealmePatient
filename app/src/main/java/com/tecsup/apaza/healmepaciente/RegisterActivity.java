package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tecsup.apaza.healmepaciente.Class.ResponseMessage;

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

        name = (EditText) findViewById(R.id.edittext_name);
        lastname = (EditText) findViewById(R.id.edittext_lastname);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        identity_document = (EditText) findViewById(R.id.edittext_dni);
        phone = (EditText) findViewById(R.id.edittext_phone);

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



        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;

        call = service.createDoctor(remail, rpassword, rname, rlastname, rphone,1,user_type, rdocument_type, ridentity_document);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);

                        //Toast.makeText(RegisterActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        //finish();
                        Toast.makeText(RegisterActivity.this, "Usuario registrado!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
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
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
