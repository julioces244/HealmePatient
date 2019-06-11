package com.tecsup.apaza.healmepaciente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;

public class SinchCall extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinch_call);

        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("1")
                .applicationKey("7644e95f-08b4-4068-b8e0-a6263083aaa3")
                .applicationSecret("iEgn6ME0e06fLZqaqVKWVA==")
                .environmentHost("sandbox.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.start();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make a call!
                sinchClient.getCallClient().callUserVideo("2");

            }
        });

    }

    public void onIncomingCall(CallClient callClient, Call call) {
        // Start playing ringing tone

        // Add call listener
    }




}
