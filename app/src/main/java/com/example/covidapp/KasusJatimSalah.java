package com.example.covidapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusJatimSalah extends AppCompatActivity {
    private RequestQueue jatimQueue;
    private RecyclerView jatimRecyclerView;
    private JatimAdapter jatimAdapter;
    private RecyclerView.LayoutManager jatimLayoutManager;
    ProgressDialog progressDialog;
    ArrayList<JatimItem> jatimList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_jatim2);

        progressDialog = new ProgressDialog(KasusJatimSalah.this);

        getSupportActionBar().setTitle("Data Jawa Timur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jatimQueue = Volley.newRequestQueue(this);

        jatimParse();
    }

    private void jatimParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://nadasthing.000webhostapp.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);

                        String namaKota = object.getString("kota");
                        String update = object.getString("uodated_at");
                        String positf = object.getString("confirm");
                        String odr = object.getString("odr");
                        String otg = object.getString("otg");
                        String odp = object.getString("odp");
                        String pdp = object.getString("pdp");

//                    String namaKota = jatim.getString("kota");
//                    String update = "tanggal";
//                    String positf = "99";
//                    String odr = "1";
//                    String otg = "2";
//                    String odp = "3";
//                    String pdp = "4";

                        jatimList.add(new JatimItem(namaKota, update, positf, odr, otg, odp, pdp));
                        Toast.makeText(getApplicationContext(), namaKota, Toast.LENGTH_LONG).show();
                    }

                    jatimRecyclerView = findViewById(R.id.recyclerJatim);
                    jatimRecyclerView.setHasFixedSize(true);
                    jatimLayoutManager = new LinearLayoutManager(getApplicationContext());
                    jatimAdapter = new JatimAdapter(jatimList);

                    jatimRecyclerView.setLayoutManager(jatimLayoutManager);
                    jatimRecyclerView.setAdapter(jatimAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        jatimQueue.add(jsonArrayRequest);
    }
}
