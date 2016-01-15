package com.example.hieunt.map_api;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import static android.app.PendingIntent.getActivity;

public class MapsActivity extends AppCompatActivity {
    // declare GoogleMap object
    GoogleMap mMap = null;

    // declare Progress Bar dialog
    ProgressDialog myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  mMap = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        //init Progress Bar
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Downloading");
        myProgress.setMessage("Please wait for a while...");
        myProgress.setCancelable(true);
        //show Progress Bar
        myProgress.show();
        //Take out Google Map object
    //    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        Fragment fm = getFragmentManager().findFragmentById(R.id.map);
        MapFragment mfm = (MapFragment)fm;
        mfm.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                myProgress.dismiss();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                set_position();
            }
        });
    }
    void set_position()
    {
        LatLng hcm = new LatLng(10.773599, 106.694420);
        mMap.addMarker(new MarkerOptions().position(hcm).title("Lò Tôn Market"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(37.4219999, -122.0862462))
                .title("Google Plex"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(hcm));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        myPlaces();
    }

    private void myPlaces() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(lastLocation != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getAltitude()),13));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude())).zoom(15).bearing(90).tilt(40).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /*private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        SupportMapFragment mapFragment;
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    */
}
