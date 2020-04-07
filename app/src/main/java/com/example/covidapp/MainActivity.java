package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    CardView cardNegara;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Updating data.....");
        progressDialog.show();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        };

        mQueue = Volley.newRequestQueue(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        jsonParse();
        updateTimeParse();

        handler.sendMessageDelayed(new Message(), 1000);

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

                        //      Membuat klik link detail
                        TextView lihatDetail = findViewById(R.id.detail_update);

                        lihatDetail.setMovementMethod(LinkMovementMethod.getInstance());
                        lihatDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                browserIntent.setData(Uri.parse("https://www.kawalcorona.com"));
                                startActivity(browserIntent);
                            }
                        });

//                        Membuat klik card negara
                        cardNegara = findViewById(R.id.card_list_negara);

                        cardNegara.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, KasusNegara.class);
                                startActivity(intent);
                            }
                        });

//                        Membuat klik card provinsi
                        cardNegara = findViewById(R.id.card_list_provinsi);

                        cardNegara.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, KasusProvinsi.class);
                                startActivity(intent);
                            }
                        });
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

    private void updateTimeParse() {
        String url = "https://api.kawalcorona.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < 1; i++) {
                        JSONObject negara = response.getJSONObject(i);
                        JSONObject dataNegara = negara.getJSONObject("attributes");

                        Long unix = dataNegara.getLong("Last_Update");
                        Date date = new Date(unix);

                        SimpleDateFormat sdf = new SimpleDateFormat("d, MMMM yyyy H:m a");

                        String dateTime = sdf.format(date);
                        String waktuTerbaru = "Diupdate pada ";
                        waktuTerbaru += dateTime;

                        TextView waktuUpdate = findViewById(R.id.waktuUpdate);
                        waktuUpdate.setText(waktuTerbaru);
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
