package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        jsonParse();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KasusFragment()).commit();
    }

    private void jsonParse() {
        String url = "https://api.kawalcorona.com/indonesia";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject indonesia = response.getJSONObject(i);

                        String positif = indonesia.getString("positif");
                        String sembuh = indonesia.getString("sembuh");
                        String meninggal = indonesia.getString("meninggal");

                        TextView tvPositif = findViewById(R.id.tv_positif);
                        TextView tvSembuh = findViewById(R.id.tv_sembuh);
                        TextView tvMeninggal = findViewById(R.id.tv_meninggal);

                        tvPositif.setText(positif);
                        tvSembuh.setText(sembuh);
                        tvMeninggal.setText(meninggal);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(jsonArrayRequest);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_kasus:
                            jsonParse();
                            selectedFragment = new KasusFragment();
                            break;
                        case R.id.nav_informasi:
                            selectedFragment = new InformasiFragment();
                            break;
                        case R.id.nav_help:
                            selectedFragment = new BantuanFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    //return false;
                    return true;
                }
            };
}
