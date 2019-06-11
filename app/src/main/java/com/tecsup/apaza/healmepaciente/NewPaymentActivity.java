package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewPaymentActivity extends AppCompatActivity {

    private Button btnlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);

        btnlist = (Button) findViewById(R.id.bt_list);

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(NewPaymentActivity.this, DoctorListActivity.class);
                startActivity(a);
            }
        });
    }

    private void CallBack(){

    }
}
