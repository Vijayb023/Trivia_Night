package com.example.trivia_night;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.trivia_night.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        LatLng atlanta = new LatLng(33.748997, -84.387985);
        MarkerOptions atlMarker = new MarkerOptions().position(atlanta).title("ATL Placeholder Markers");
        atlMarker.snippet("This is a placeholder marker");
        mMap.addMarker(atlMarker);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));

        LatLng battleBrew = new LatLng(33.916340,-84.381287);
        MarkerOptions btlbrewMarker = new MarkerOptions().position(battleBrew).title("Battle and Brew");
        btlbrewMarker.snippet("Themed Trivia Nights hosted here varying fom Star Wars, Disney, Pokemon, Harry Potter and more on Wednesdays 8pm.\n You can contact them at +1 678-560-1500");
        mMap.addMarker(btlbrewMarker);


        LatLng norRivTavern = new LatLng(34.000010,-84.350430);
        MarkerOptions norRivMarker = new MarkerOptions().position(norRivTavern).title("North River Tavern");
        norRivMarker.snippet("Located just south of the Chattahoochee River in Sandy Springs, North River Tavern doesn’t limit its trivia to just one night. The beer pub offers trivia Wednesdays at 7:30, Thursdays at 8, and Sundays at 7.\nYou can contact them at +1 770-552-8784.");
        mMap.addMarker(norRivMarker);

        LatLng lostDruidBrew = new LatLng(33.777309,-84.272583);
        MarkerOptions lostDruidMarker = new MarkerOptions().position(lostDruidBrew).title("Lost Druid Brewery");
        lostDruidMarker.snippet("Lost Druid Brewery welcomes its neighbors with beer, bar good, and Wednesday night trivia at 7pm.\nYou can contact them at +1 404-998-5679");
        mMap.addMarker(lostDruidMarker);



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(33.8, -84.3))
                .zoom(9)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void BackToMain(View view) {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
}