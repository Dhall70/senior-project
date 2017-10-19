package com.example.seniorproject.TrackInspector;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.seniorproject.MapActivitys;
import com.example.seniorproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



/*
 * Created by willw on 9/11/2017.
 */

public class MapTI extends MapActivitys {
    //Tag used for log
    private String TAG = "Google Drive Activity";
    //title for generated report
    private String documentTitle = "A title";
    //button that generate report and stores that report into root folder of google drive specified
    private Button endInspectionButton;
    private Button setCurrLoc;
    private Button addDefect;
    private ToggleButton toggleMarkerPlacement;
    private int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 99;
    public static boolean toggleMarkerOn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_track_inspector);
        endInspectionButton = (Button) findViewById(R.id.endInspection);
        setCurrLoc = (Button) findViewById(R.id.setcurrentlocation);
        addDefect = (Button) findViewById(R.id.adddefect);
        toggleMarkerPlacement = (ToggleButton) findViewById(R.id.placemarkermode);

        //for map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        ActivityCompat.requestPermissions(MapTI.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);

        setButtons();

    }


    public void setButtons(){
        endInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MapTI.this);
                builder.setMessage("Would you like to end inspection?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                               generateReport();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
               ;

            }
        });
        setCurrLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currLocMarker.remove();
                LatLng curLoc = new LatLng(LATITUDE,LONGITUDE);
                currLocMarker = mMap.addMarker(new MarkerOptions().position(curLoc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).title("Current Location."));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(curLoc));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

            }
        });
        addDefect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currMarker != null) {
                    Intent intent = new Intent(MapTI.this, InspectionForm.class);
                    startActivity(intent);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MapTI.this);
                    builder.setMessage("A new marker needs to be added.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.cancel();
                                }
                            });

                                final AlertDialog alert = builder.create();
                                alert.show();

                }
            }
        });
        toggleMarkerPlacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleMarkerOn) {
                    toggleMarkerPlacement.setChecked(false);
                    toggleMarkerOn = false;
                }
                else{
                    toggleMarkerPlacement.setChecked(true);
                    toggleMarkerOn = true;
                }


            }
        });

    }
    private void generateReport(){
        Intent intent = new Intent(MapTI.this, FormatReport.class);
        startActivity(intent);
    }



}