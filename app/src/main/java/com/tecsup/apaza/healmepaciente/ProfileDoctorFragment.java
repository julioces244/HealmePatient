package com.tecsup.apaza.healmepaciente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProfileDoctorFragment extends Fragment {

    Button playPause;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_doctor, container, false);
        //FloatingActionButton imageView = (FloatingActionButton) view.findViewById(R.id.button_video);
        Button imageView = (Button) view.findViewById(R.id.button_video);
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //OnCLick Stuff
                Intent intent = new Intent(getActivity(), VideoStreamActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_profile_doctor, container, false);
        //playPause = (Button) getView().findViewById(R.id.button_video);
    }

    public void govideostream(View view){
        Intent intent = new Intent(getActivity(), VideoStreamActivity.class);
        view.getContext().startActivity(intent);
    }

}
