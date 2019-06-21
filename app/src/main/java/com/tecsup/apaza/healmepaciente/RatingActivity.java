package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tecsup.apaza.healmepaciente.Class.Doctor;
import com.tecsup.apaza.healmepaciente.Class.ResponseMessage;
import com.tecsup.apaza.healmepaciente.Class.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tecsup.apaza.healmepaciente.LoginActivity.PREFS_NAME;
import static org.webrtc.ContextUtils.getApplicationContext;

public class RatingActivity extends AppCompatActivity {

    private static final String TAG = RatingActivity.class.getSimpleName();

    TextView titlerate, resultrate;
    Button btnfeedback;
    ImageView charPlace, icSprite;
    RatingBar rateStars;
    String answerValue;
    Animation charanim,anisprite;
    private Integer id;
    Integer id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        id = getIntent().getExtras().getInt("id_doctor");

        titlerate = findViewById(R.id.titlerate);
        resultrate = findViewById(R.id.resultRate);

        btnfeedback = findViewById(R.id.btnfeedback);

        charPlace = findViewById(R.id.charPlace);
        icSprite = findViewById(R.id.icSprite);
        rateStars = findViewById(R.id.rateStars);

        //cargar animacion
        charanim = AnimationUtils.loadAnimation(this, R.anim.charanim);
        anisprite = AnimationUtils.loadAnimation(this, R.anim.anisprite);

        //dar animancion
        charPlace.startAnimation(charanim);
        icSprite.startAnimation(anisprite);

        //give condition
        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                answerValue = String.valueOf((int)(rateStars.getRating()));
                if(answerValue.equals("1")){
                    charPlace.setImageResource(R.drawable.ic_doctor1);
                    charPlace.startAnimation(charanim);
                    icSprite.animate().alpha(0).setDuration(300).start();
                    resultrate.setText("Muy Malo");
                }
                else if(answerValue.equals("2")){
                    charPlace.setImageResource(R.drawable.ic_doctor2);
                    charPlace.startAnimation(charanim);
                    icSprite.animate().alpha(0).setDuration(300).start();
                    resultrate.setText("Malo");
                }
                else if(answerValue.equals("3")){
                    charPlace.setImageResource(R.drawable.ic_doctor3);
                    charPlace.startAnimation(charanim);
                    icSprite.animate().alpha(0).setDuration(300).start();
                    //icSprite.startAnimation(anisprite);
                    resultrate.setText("Regular");
                }
                else if(answerValue.equals("4")){
                    charPlace.setImageResource(R.drawable.ic_doctor4);
                    charPlace.startAnimation(charanim);
                    icSprite.animate().alpha(1).setDuration(300).start();
                    icSprite.startAnimation(anisprite);
                    resultrate.setText("Bueno");
                }
                else if(answerValue.equals("5")){
                    charPlace.setImageResource(R.drawable.ic_doctor5);
                    charPlace.startAnimation(charanim);
                    icSprite.animate().alpha(1).setDuration(300).start();
                    icSprite.startAnimation(anisprite);
                    resultrate.setText("Excelente");
                }
                else {
                    Toast.makeText(getApplicationContext(),"No puntua",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //import font
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MR.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        //customize font
        titlerate.setTypeface(MRegular);
        resultrate.setTypeface(MMedium);
        btnfeedback.setTypeface(MMedium);

    }

    public void sendrating(View view){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        id_user = sharedPref.getInt("key_id",0);
        //Toast.makeText(RatingActivity.this,"Mi numero de id es: "+ id_user, Toast.LENGTH_SHORT).show();
        //Toast.makeText(RatingActivity.this,"Doctor id: "+ id, Toast.LENGTH_SHORT).show();
        Toast.makeText(RatingActivity.this,"Valoracion es: "+ answerValue, Toast.LENGTH_SHORT).show();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;

        Integer answerValueInt = Integer.parseInt(answerValue);
        call = service.doctorRating(id_user,id, answerValueInt);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "USER NAME: " + responseMessage);


                        Toast.makeText(RatingActivity.this, responseMessage.getMsg(), Toast.LENGTH_LONG).show();

                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RatingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RatingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }
}
