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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusJatim extends AppCompatActivity {
    private RequestQueue jatimQueue;
    private RecyclerView jatimRecyclerView;
    private RecyclerView.Adapter jatimAdapter;
    private RecyclerView.LayoutManager jatimLayoutManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_jatim);

        progressDialog = new ProgressDialog(KasusJatim.this);

        getSupportActionBar().setTitle("Data Jawa Timur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jatimQueue = Volley.newRequestQueue(this);

        jatimParse();

//        negaraList.add(new NegaraItem("Indo", "100", "100", "100"));



//        for(NegaraItem pair : negaraList){
//            Toast.makeText(getApplicationContext(), pair.getNegaraTittle(), Toast.LENGTH_LONG).show();
//        }

    }

    private void jatimParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "http://kholiqs.codes:3000/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
                try {
//                    ArrayList<JatimItem> jatimList = new ArrayList<>();

                    for (int i = response.length(); i > response.length() - 30 ; i--) {
                        JSONObject jatim = response.getJSONObject(i);

                        String namaKota = jatim.getString("id");
//                        String updateDate = jatim.getString("updated_at");
//                        String textPositif = jatim.getString("confirm");
//                        String textSembuh = jatim.getString("sembuh");
//                        String textMeninggal = jatim.getString("meninggal");
//                        String odr = jatim.getString("odr");
//                        String otg = jatim.getString("otg");
//                        String odp = jatim.getString("odp");
//                        String pdp = jatim.getString("pdp");
//
//                        jatimList.add(new JatimItem(namaKota, updateDate, textPositif, textSembuh, textMeninggal, odr, otg, odp, pdp));
                        Toast.makeText(getApplicationContext(), namaKota, Toast.LENGTH_LONG).show();
                    }

//                    jatimRecyclerView = findViewById(R.id.recyclerJatim);
//                    jatimRecyclerView.setHasFixedSize(true);
//                    jatimLayoutManager = new LinearLayoutManager(getApplicationContext());
//                    jatimAdapter = new JatimAdapter(jatimList);
//
//                    jatimRecyclerView.setLayoutManager(jatimLayoutManager);
//                    jatimRecyclerView.setAdapter(jatimAdapter);

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

        jatimQueue.add(jsonArrayRequest);
    }
}
