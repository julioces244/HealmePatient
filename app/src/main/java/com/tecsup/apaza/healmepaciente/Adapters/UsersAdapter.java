package com.tecsup.apaza.healmepaciente.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsup.apaza.healmepaciente.ApiService;
import com.tecsup.apaza.healmepaciente.Class.Doctor;
import com.tecsup.apaza.healmepaciente.Class.Office;
import com.tecsup.apaza.healmepaciente.Class.User;
import com.tecsup.apaza.healmepaciente.DoctorListActivity;
import com.tecsup.apaza.healmepaciente.ProfileDoctorActivity;
import com.tecsup.apaza.healmepaciente.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<Doctor> doctors;
    private List<User> users;

    public UsersAdapter(){
        this.doctors = new ArrayList<>();

    }

    public void setDoctos(List<Doctor> doctors){
        this.doctors = doctors;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircularImageView fotoImage;
        public TextView nameText, spec;
        public TextView lastnameText;
        public TextView state;
        public Button btnstate;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (CircularImageView)itemView.findViewById(R.id.foto_image);
            nameText = (TextView) itemView.findViewById(R.id.name_text);
            lastnameText = (TextView) itemView.findViewById(R.id.lastname_text);
            state = (TextView) itemView.findViewById(R.id.state);
            spec = (TextView) itemView.findViewById(R.id.speciality_text);
            btnstate = (Button) itemView.findViewById(R.id.btnstate);

        }
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder viewHolder, int position) {



        final Doctor doctor = this.doctors.get(position);

        final Context context = viewHolder.itemView.getContext();


        String urlImage = "";
        if(doctor.getUser().getImages().size() != 0) {
            urlImage = doctor.getUser().getImages().get(0).getUrl();
        } else {
            if (doctor.getUser().getGender_id() == 1) {
                urlImage = "public/storage/JVqBFD0XlccFQzybZIwHmGKlSpwTFwTGkpoSgjfI.png";
            } else {
                urlImage = "public/storage/eUzGzIrRdBxMkunLiMl93ntzhetSqJ9niRWIOWE5.png";
            }
        }
            final String regex = "^(?:([^:]*):(?://)?)?([^/]*)(/.*)?";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(urlImage);





            viewHolder.nameText.setText(doctor.getUser().getName());
            viewHolder.lastnameText.setText(doctor.getUser().getLastname());
            if(doctor.getSpecialities().size() != 0) {
                viewHolder.spec.setText(doctor.getSpecialities().get(0).getName());
            }


            if(doctor.getIsOnline()==1){
                viewHolder.state.setText("Conectado");
                viewHolder.btnstate.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_round));

            }else {
                viewHolder.state.setText("Desconectado");
                viewHolder.btnstate.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_round2));
                //viewHolder.btnstate.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            //intent.putExtra("image", doctor.getImages().get(0).getId());





//Ver si coincide el regex
            if (matcher.find()) {
                //Obtener el texto capturado por cada conjunto de paréntesis
                String ruta   = matcher.group(3);
                System.out.println("ruta      = " + ruta);
                String url = ApiService.API_BASE_URL + "/storage/" + ruta;
                Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context.getApplicationContext(), ProfileDoctorActivity.class);
                        intent.putExtra("id", doctor.getId());
                        intent.putExtra("name", doctor.getUser().getName());
                        intent.putExtra("image", ruta);
                        intent.putExtra("email",doctor.getUser().getEmail());
                        intent.putExtra("phone", doctor.getUser().getPhone());
                        intent.putExtra("gender_id", doctor.getUser().getGender_id());
                        intent.putExtra("identity_document", doctor.getUser().getIdentity_document());
                        intent.putExtra("valoration",doctor.getValoration());
                        context.startActivity(intent);
                    }
                });
            }









    }

    @Override
    public int getItemCount() {
        return this.doctors.size();
    }


}
