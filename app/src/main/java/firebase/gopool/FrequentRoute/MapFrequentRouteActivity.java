package firebase.gopool.FrequentRoute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import firebase.gopool.Home.HomeActivity;
import firebase.gopool.MapDirectionHelper.FetchURL;
import firebase.gopool.MapDirectionHelper.TaskLoadedCallback;
import firebase.gopool.R;
import firebase.gopool.models.FrequentRouteResults;

public class MapFrequentRouteActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private FrequentRouteResults route;
    private Marker markerStart,markerEnd;

    private LinearLayout layout_radiobtn;
    private Button btn_share,btn_cancle_share;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_frequent_route);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();

    }

    private void init() {

        layout_radiobtn = (LinearLayout) findViewById(R.id.layout_radiobtn);
        btn_share = (Button) findViewById(R.id.btn_share);
        btn_cancle_share = (Button) findViewById(R.id.btn_cancle_share);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);


        setOnclick();
    }

    private void setOnclick() {
        btn_share.setOnClickListener(view -> {

            Toast.makeText(this,"Share successful!",Toast.LENGTH_SHORT).show();
            finish();
        });
        btn_cancle_share.setOnClickListener(view -> {
            Toast.makeText(this,"Cancle share successful!",Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private void zoomMap(LatLng startPoint, LatLng endPoint) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startPoint);
        builder.include(endPoint);
        LatLngBounds bounds = builder.build();

        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,padding);
        mMap.animateCamera(cu);
    }

    private void createMarkerStart(LatLng startPoint) {
        String snippet = route.getAddress_start() + "\n" +
                "Starting time: " + route.getTime_start() + "\n";

        MarkerOptions marker = new MarkerOptions()
                .position(startPoint)
                .title("Origin")
                .snippet(snippet);

        markerStart = mMap.addMarker(marker);
    }
    private void createMarkerEnd(LatLng endPoint) {
        String snippet =  route.getAddress_destination() + "\n" +
                "Arrival time: " + route.getTime_destination() + "\n";

        MarkerOptions marker = new MarkerOptions()
                .position(endPoint)
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet(snippet);

        markerEnd = mMap.addMarker(marker);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String google_api = getResources().getString(R.string.google_maps_api_key);
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + google_api;
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if(getIntent() != null) {
            route = (FrequentRouteResults) getIntent().getSerializableExtra("data");
        }
        if (route != null) {
            LatLng startPoint = new LatLng(route.getLat_start(),route.getLng_start());
            LatLng endPoint = new LatLng(route.getLat_end(),route.getLng_end());
            createMarkerStart(startPoint);
            createMarkerEnd(endPoint);
            new FetchURL(MapFrequentRouteActivity.this, mMap).execute(getUrl(markerStart.getPosition(), markerEnd.getPosition(), "driving"), "driving");

            mMap.setOnMapLoadedCallback(() -> {
                zoomMap(startPoint,endPoint);
            });
        }

    }

    @Override
    public void onTaskDone(Object... values) {

    }
}