package com.example.covidapp.jember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusJember extends AppCompatActivity {
    private RequestQueue jemberQueue;
    private RecyclerView jemberRecyclerView;
    private JemberAdapter jemberAdapter;
    private RecyclerView.LayoutManager jemberLayoutManager;
    ProgressDialog progressDialog;
    ArrayList<JemberItem> jemberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_jember);

        progressDialog = new ProgressDialog(KasusJember.this);

        getSupportActionBar().setTitle("Data Jawa Timur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jemberQueue = Volley.newRequestQueue(this);

        jemberParse();
    }

    private void jemberParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://nadasthing.000webhostapp.com/jember.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
                try {
                    ArrayList<JemberItem> jemberList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);

                        String namaKecamatan = object.getString("kecamatan");
                        String positif = object.getString("positif");
                        String odr = object.getString("odr");
                        String odp = object.getString("odp");
                        String pdp = object.getString("pdp");

                        jemberList.add(new JemberItem(namaKecamatan, positif, odr, odp, pdp));
                    }

                    jemberRecyclerView = findViewById(R.id.recyclerJember);
                    jemberRecyclerView.setHasFixedSize(true);
                    jemberLayoutManager = new LinearLayoutManager(getApplicationContext());
                    jemberAdapter = new JemberAdapter(jemberList);

                    jemberRecyclerView.setLayoutManager(jemberLayoutManager);
                    jemberRecyclerView.setAdapter(jemberAdapter);

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

        jemberQueue.add(jsonArrayRequest);
    }
}
