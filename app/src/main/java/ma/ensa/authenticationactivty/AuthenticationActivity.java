package ma.ensa.authenticationactivty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView myMap = findViewById(R.id.gMap);
        myMap.setBackgroundResource(R.drawable.googlemaps);
        myMap.setOnClickListener(v -> {


            Intent intent = new Intent(AuthenticationActivity.this, MapsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnLogin;
        btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TransactionActivity.class);
                startActivity(intent);

            }
        });
    }

}