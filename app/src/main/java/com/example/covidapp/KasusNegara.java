package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusNegara extends AppCompatActivity {
    private RequestQueue negaraQueue;
    private RecyclerView negaraRecyclerView;
    private RecyclerView.Adapter negaraAdapter;
    private RecyclerView.LayoutManager negaraLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_negara);

        final ProgressDialog progressDialog = new ProgressDialog(KasusNegara.this);
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

        getSupportActionBar().setTitle("Data Negara");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        negaraQueue = Volley.newRequestQueue(this);

        negaraParse();

        handler.sendMessageDelayed(new Message(), 1000);

//        negaraList.add(new NegaraItem("Indo", "100", "100", "100"));



//        for(NegaraItem pair : negaraList){
//            Toast.makeText(getApplicationContext(), pair.getNegaraTittle(), Toast.LENGTH_LONG).show();
//        }

    }

    private void negaraParse() {
        String url = "https://api.kawalcorona.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<NegaraItem> negaraList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject negara = response.getJSONObject(i);
                        JSONObject dataNegara = negara.getJSONObject("attributes");

                        String namaNegara = dataNegara.getString("Country_Region");
                        String textPositif = dataNegara.getString("Confirmed");
                        String textSembuh = dataNegara.getString("Recovered");
                        String textNegatif = dataNegara.getString("Deaths");

                        negaraList.add(new NegaraItem(namaNegara, textPositif, textSembuh, textNegatif));
                    }

                    negaraRecyclerView = findViewById(R.id.recyclerNegara);
                    negaraRecyclerView.setHasFixedSize(true);
                    negaraLayoutManager = new LinearLayoutManager(getApplicationContext());
                    negaraAdapter = new NegaraAdapter(negaraList);

                    negaraRecyclerView.setLayoutManager(negaraLayoutManager);
                    negaraRecyclerView.setAdapter(negaraAdapter);

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

        negaraQueue.add(jsonArrayRequest);
    }
}
