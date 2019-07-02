package com.tecsup.apaza.healmepaciente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsup.apaza.healmepaciente.models.User;
import com.tecsup.apaza.healmepaciente.services.ApiService;
import com.tecsup.apaza.healmepaciente.services.ApiServiceGenerator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.tecsup.apaza.healmepaciente.LoginActivity.PREFS_NAME;
import static org.webrtc.ContextUtils.getApplicationContext;


public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    Button pay;
    View view;
    Integer id_user;
    private TextView uemail, uphone, uname, ulastname, udni;
    private CircularImageView uimage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        pay = view.findViewById(R.id.payment);
        uemail = view.findViewById(R.id.pf_email);
        uphone = view.findViewById(R.id.pf_phone);
        uname = view.findViewById(R.id.pf_name);
        ulastname = view.findViewById(R.id.pf_lastname);
        udni = view.findViewById(R.id.pf_dni);
        uimage = view.findViewById(R.id.pf_image);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        id_user = sharedPref.getInt("key_id",0);
        initialize();


        pay.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            view.getContext().startActivity(intent);
        });
        return view;
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<User> call = service.showUser(id_user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        User user = response.body();
                        Log.d(TAG, "Usuario: " + user);

                        String urlImage = "";
                        if(user.getImages().size() != 0) {
                            urlImage = user.getImages().get(0).getUrl();
                        } else {
                            if (user.getGender_id() == 1) {
                                urlImage = "public/storage/JVqBFD0XlccFQzybZIwHmGKlSpwTFwTGkpoSgjfI.png";
                            } else {
                                urlImage = "public/storage/eUzGzIrRdBxMkunLiMl93ntzhetSqJ9niRWIOWE5.png";
                            }
                        }

                        final String regex = "^(?:([^:]*):(?://)?)?([^/]*)(/.*)?";
                        final Pattern pattern = Pattern.compile(regex);
                        final Matcher matcher = pattern.matcher(urlImage);

                        if (matcher.find()) {
                            String ruta   = matcher.group(3);
                            System.out.println("ruta      = " + ruta);
                            String url = ApiServiceGenerator.API_BASE_URL + "/storage/" + ruta;
                            Picasso.with(getApplicationContext()).load(url).into(uimage);
                        }

                        uemail.setText(user.getEmail());
                        uphone.setText(user.getPhone());
                        udni.setText(user.getIdentity_document());
                        uname.setText(user.getName());
                        ulastname.setText(user.getLastname());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Oops parace que tenemos algunos problemas!");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
//                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), "Oops! parace que la conexión esta fallando.", Toast.LENGTH_LONG).show();
            }

        });
    }

}