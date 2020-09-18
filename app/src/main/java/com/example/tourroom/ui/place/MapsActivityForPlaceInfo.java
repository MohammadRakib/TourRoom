package com.example.tourroom.ui.place;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tourroom.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;



public class MapsActivityForPlaceInfo extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String addresstrack;
    private Geocoder geocoder;
    static int LOCATION_REQUEST_CODE = 10001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_place_info);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        Bundle extra1 = getIntent().getExtras();
        if (extra1 != null) {

            addresstrack = extra1.getString("addresspass");
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try {
            List<Address> addresses = geocoder.getFromLocationName(addresstrack, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);

                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                        .title(String.valueOf(addresstrack));
                mMap.addMarker(markerOptions);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.moveCamera(cameraUpdate);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}