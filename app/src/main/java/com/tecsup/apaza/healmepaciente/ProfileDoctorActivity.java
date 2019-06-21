package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sinch.android.rtc.calling.Call;
import com.squareup.picasso.Picasso;

public class ProfileDoctorActivity extends  BaseActivity {

    private TextView nameTxt;
    private TextView lastnameTxt;
    private TextView emailTxt;
    private TextView phoneTxt, valorationTxt;
    private Button mCallButton;
    private CircularImageView ivImageViewFromUrl;
    private String nombr, emai, phone, images, identity_document;
    private Double valor;
    private Integer id_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);
        nameTxt = (TextView) findViewById(R.id.name);
        emailTxt = (TextView) findViewById(R.id.email);
        phoneTxt = (TextView) findViewById(R.id.phone);
        valorationTxt = (TextView) findViewById(R.id.dc_valoration);
        mCallButton = (Button) findViewById(R.id.callButton);
        mCallButton.setEnabled(false);
        mCallButton.setOnClickListener(buttonClickListener);

        id_doc = getIntent().getExtras().getInt("id");
        nombr = getIntent().getExtras().getString("name");
        emai = getIntent().getExtras().getString("email");
        phone = getIntent().getExtras().getString("phone");
        images = getIntent().getExtras().getString("image");
        identity_document = getIntent().getExtras().getString("identity_document");
        valor = getIntent().getExtras().getDouble("valoration");
        Toast.makeText(ProfileDoctorActivity.this, identity_document, Toast.LENGTH_SHORT).show();
        ivImageViewFromUrl = (CircularImageView)findViewById(R.id.iv_image_from_url);


        String url = ApiService.API_BASE_URL + "/storage/" + images;
        Picasso.with(getApplicationContext()).load(url).into(ivImageViewFromUrl);


        nameTxt.setText(nombr);
        emailTxt.setText(emai);
        phoneTxt.setText(phone);
        valorationTxt.setText(valor.toString());

    }

    public void gorating(View view){
        Intent intent = new Intent(ProfileDoctorActivity.this, RatingActivity.class);
        intent.putExtra("id_doctor", id_doc);
        startActivity(intent);
    }

    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    @Override
    protected void onServiceConnected() {
        mCallButton.setEnabled(true);
    }



    private void callButtonClicked() {
        String userName = identity_document ;
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = getSinchServiceInterface().callUserVideo(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }



   private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    callButtonClicked();
                    break;

            }
        }
    };


}
