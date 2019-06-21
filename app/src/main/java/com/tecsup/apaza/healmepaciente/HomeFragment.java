package com.tecsup.apaza.healmepaciente;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.special.ResideMenu.ResideMenu;
import com.tecsup.apaza.healmepaciente.Adapters.UsersAdapter;
import com.tecsup.apaza.healmepaciente.Class.Office;
import com.tecsup.apaza.healmepaciente.Class.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment implements OnMapReadyCallback {

    //private View parentView;
    public static final int REQUEST_CODE=101;
    private ResideMenu resideMenu;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //setUpViews();
        //Zoom current location

        searchView = view.findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    //map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        LatLng myPosition;


        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }

        //MOSTRANDO TODAS LAS UBICACIONES DE CONSULTORIOS EN EL MAPA MARKERS




        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            myPosition = new LatLng(latitude, longitude);


            LatLng coordinate = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 17);
            map.animateCamera(yourLocation);
        }



        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Office>> call = service.getOffices();

        call.enqueue(new Callback<List<Office>>() {
            @Override
            public void onResponse(Call<List<Office>> call, Response<List<Office>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Office> offices = response.body();
                        //Log.d(TAG, "COORDENADAS: " + offices);

                        for(Office nada : offices){
                        UiSettings uiSettings = map.getUiSettings();
                        uiSettings.setZoomControlsEnabled(true);    // Controles de zoom
                        uiSettings.setCompassEnabled(true); // Brújula

                            double latitud = Double.parseDouble(nada.getLatitude());
                            double longitud = Double.parseDouble(nada.getLongitude());

                        LatLng ubi = new LatLng(-latitud, longitud);
                            Log.d(TAG, "-------------: " + ubi);
                        //map.addMarker(new MarkerOptions().position(ubi).title("Consultorio"));
                        //map.moveCamera(CameraUpdateFactory.newLatLng(ubi));
                        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(ubi,11));
                            map.addMarker(new MarkerOptions()
                                    .position(ubi)
                                    .title("Wrong Turn!")
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.ic_office)));
                        }


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
            public void onFailure(Call<List<Office>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }


    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                }
                break;
        }
    }



}