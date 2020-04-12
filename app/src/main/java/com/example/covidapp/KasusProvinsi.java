package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

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

public class KasusProvinsi extends AppCompatActivity {
    private RequestQueue provinsiQueue;
    private RecyclerView provinsiRecyclerView;
    private ProvinsiAdapter provinsiAdapter;
    private RecyclerView.LayoutManager provinsiLayoutManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_provinsi);

        progressDialog = new ProgressDialog(KasusProvinsi.this);

        getSupportActionBar().setTitle("Data Provinsi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        provinsiQueue = Volley.newRequestQueue(this);

        provinsiParse();
    }

    private void provinsiParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/indonesia/provinsi/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
                try {
                    ArrayList<ProvinsiItem> provinsiList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject provinsi = response.getJSONObject(i);
                        JSONObject dataProvinsi = provinsi.getJSONObject("attributes");

                        String namaProvinsi = dataProvinsi.getString("Provinsi");
                        String textPositif = dataProvinsi.getString("Kasus_Posi");
                        String textSembuh = dataProvinsi.getString("Kasus_Semb");
                        String textMeninggal = dataProvinsi.getString("Kasus_Meni");

                        provinsiList.add(new ProvinsiItem(namaProvinsi, textPositif, textSembuh, textMeninggal));
                    }

                    provinsiRecyclerView = findViewById(R.id.recyclerProvinsi);
                    provinsiRecyclerView.setHasFixedSize(true);
                    provinsiLayoutManager = new LinearLayoutManager(getApplicationContext());
                    provinsiAdapter = new ProvinsiAdapter(provinsiList);

                    provinsiRecyclerView.setLayoutManager(provinsiLayoutManager);
                    provinsiRecyclerView.setAdapter(provinsiAdapter);

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

        provinsiQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Cari Provinsi");
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                provinsiAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
