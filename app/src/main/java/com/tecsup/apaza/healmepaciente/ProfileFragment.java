package com.tecsup.apaza.healmepaciente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsup.apaza.healmepaciente.Adapters.UsersAdapter;
import com.tecsup.apaza.healmepaciente.Class.Doctor;
import com.tecsup.apaza.healmepaciente.Class.User;
import com.tecsup.apaza.healmepaciente.LoginActivity;

import java.util.List;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        pay = (Button) view.findViewById(R.id.payment);
        uemail = (TextView) view.findViewById(R.id.pf_email);
        uphone = (TextView) view.findViewById(R.id.pf_phone);
        uname = (TextView) view.findViewById(R.id.pf_name);
        ulastname = (TextView) view.findViewById(R.id.pf_lastname);
        udni = (TextView) view.findViewById(R.id.pf_dni);
        uimage = (CircularImageView) view.findViewById(R.id.pf_image);


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        id_user = sharedPref.getInt("key_id",0);
        Toast.makeText(getContext(),"Mi numero de id es: "+ id_user, Toast.LENGTH_SHORT).show();

        initialize();


        pay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //OnCLick Stuff
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<User> call = service.showUser(id_user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        User user = response.body();
                        Log.d(TAG, "Usuario: " + user);

                        String Imgurl = user.getImages().get(0).getUrl();
                        final String regex = "^(?:([^:]*):(?://)?)?([^/]*)(/.*)?";
                        final Pattern pattern = Pattern.compile(regex);
                        final Matcher matcher = pattern.matcher(Imgurl);

                        if (matcher.find()) {
                            String ruta   = matcher.group(3);
                            System.out.println("ruta      = " + ruta);
                            String url = ApiService.API_BASE_URL + "/storage/" + ruta;
                            Picasso.with(getApplicationContext()).load(url).into(uimage);

                            Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();

                        }



                        uemail.setText(user.getEmail());
                        uphone.setText(user.getPhone());
                        udni.setText(user.getIdentity_document());
                        uname.setText(user.getName());
                        ulastname.setText(user.getLastname());


                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }



}