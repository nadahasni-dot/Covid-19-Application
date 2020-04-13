package com.example.covidapp.jatim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusJatim extends AppCompatActivity {
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

        progressDialog = new ProgressDialog(KasusJatim.this);

        getSupportActionBar().setTitle("Data Jawa Timur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jatimQueue = Volley.newRequestQueue(this);

        jatimParse();

        jatimRecyclerView = findViewById(R.id.recyclerJatim);
        jatimRecyclerView.setHasFixedSize(true);
        jatimLayoutManager = new LinearLayoutManager(getApplicationContext());
        jatimAdapter = new JatimAdapter(jatimList);

        jatimRecyclerView.setLayoutManager(jatimLayoutManager);
        jatimRecyclerView.setAdapter(jatimAdapter);
    }

    private void jatimParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://nadasthing.000webhostapp.com/";

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
//                    for (int i = 0; i < 49; i++) {
                        JSONObject object = new JSONObject(response);

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

//                    }
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
