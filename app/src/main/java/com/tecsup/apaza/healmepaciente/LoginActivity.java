package com.tecsup.apaza.healmepaciente;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.sinch.android.rtc.SinchError;
import com.steelkiwi.library.SlidingSquareLoaderView;
import com.tecsup.apaza.healmepaciente.Class.ResponseMessage;
import com.tecsup.apaza.healmepaciente.Class.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements SinchService.StartFailedListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final int REQUEST_CODE=101;
    EditText email;
    EditText password;
    private Button mLoginButton;

    public static final String PREFS_NAME = "MyApp";

    //SlidingSquareLoaderView anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //anim = (SlidingSquareLoaderView) findViewById(R.id.view);
        //anim.hide();
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);

        mLoginButton =findViewById(R.id.bt_login);

        permission();


    }

    public void permission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                        1);
            }}

        /*
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        2);
            }}

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        3);
            }}*/



    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(LoginActivity.this, "Permission denied to read your Camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            /*
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(LoginActivity.this, "Permission denied to read your Audio", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 3: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(LoginActivity.this, "Permission denied to read your Location", Toast.LENGTH_SHORT).show();
                }
                return;
        }*/
    }}


    public void goregister(View view){
        //anim.startDeterminate();
        //anim.stopOk();;
        //anim.stopOk();
        // to show loading
        //anim.show();

        // to hide loading
        //anim.hide();

        Intent intent = new Intent(LoginActivity.this,
                RegisterActivity.class);
        startActivity(intent);
    }


    ////////////////////////////////
    @Override
    protected void onServiceConnected() {
        mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);

        getSinchServiceInterface().setStartListener(new SinchService.StartFailedListener() {
            @Override
            public void onStartFailed(SinchError error) {

            }

            @Override
            public void onStarted() {

            }
        });
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStarted() {
        openPlaceCallActivity();
    }



    private void openPlaceCallActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    ////////////////////////////////

    public void gomainview(View view){

        String user  = email.getText().toString();
        String pass = password.getText().toString();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call = null;

        call = service.loginUser(user, pass);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        User responseMessage = response.body();
                        Log.d(TAG, "USER NAME: " + responseMessage.getName());

                        String userName =  responseMessage.getIdentity_document();
                        Integer id = responseMessage.getId();

                        //Toast.makeText(LoginActivity.this, userName,Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DNI: " + userName);





                        if (!userName.equals(getSinchServiceInterface().getUserName())) {
                            getSinchServiceInterface().stopClient();
                        }

                        if (!getSinchServiceInterface().isStarted()) {
                            getSinchServiceInterface().startClient(userName);
                        } else {
                            openPlaceCallActivity();
                        }

                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("key_id",id);
                        editor.commit();


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra("id_user", id);
                        startActivity(intent);


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");


                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }

}
