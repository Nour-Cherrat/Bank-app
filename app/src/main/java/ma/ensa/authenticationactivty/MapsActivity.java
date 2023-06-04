package ma.ensa.authenticationactivty;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import ma.ensa.authenticationactivty.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private SearchView searchView;
    private String searchText;
    private GoogleMap googleMap;
    Localisation getlocalisation;

    String phoneNumber = "0645379943";
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        searchView = findViewById(R.id.searchView);
        Button sendEmailButton = findViewById(R.id.btnEmail);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailAddress = "bank@gmail.com";
                String subject = "Sujet: ";
                String message = "";

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Envoyer e-mail"));
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "Error mail", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button sendSmsButton = findViewById(R.id.btnSMS);

        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("smsto:" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
            }

        });

        Button CallCenterButton = findViewById(R.id.btnCA);

        CallCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                if (ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(intent);
                }  }});


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchText = query;
                updateMapWithSearchText();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.coordonnees_agence, null);

                TextView titleTextView = view.findViewById(R.id.titleTextView);

                titleTextView.setText(marker.getTitle());

                return view;
            }
        });

    }

    // need to add the zoom
    private void updateMapWithSearchText() {
        if (searchText != null) {
            getlocalisation = AgenceCoordonnees.getLocalisation(MapsActivity.this, searchText);

        }

        if (googleMap != null && getlocalisation != null) {
            phoneNumber = String.valueOf(getlocalisation.getTel());
            googleMap.clear();
            LatLng agence = new LatLng(getlocalisation.getAltitude(), getlocalisation.getLongitude());

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(agence)
                    .title("Agence: " + getlocalisation.getAgence() + "\n" + "Nom responsable: " + getlocalisation.getResp() + "\n" + "Num: " + getlocalisation.getTel()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(agence));
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });
        }else{

            Toast.makeText(this, "Aucune agence avec le nom: "+searchText+" n'a été trouvée ", Toast.LENGTH_SHORT).show();

        }
    }
}