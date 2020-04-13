package com.example.covidapp.negara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.covidapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KasusNegara extends AppCompatActivity {
    private RequestQueue negaraQueue;
    private RecyclerView negaraRecyclerView;
    private NegaraAdapter negaraAdapter;
    private RecyclerView.LayoutManager negaraLayoutManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_negara);

        progressDialog = new ProgressDialog(KasusNegara.this);

        getSupportActionBar().setTitle("Data Negara");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        negaraQueue = Volley.newRequestQueue(this);

        negaraParse();

//        negaraList.add(new NegaraItem("Indo", "100", "100", "100"));



//        for(NegaraItem pair : negaraList){
//            Toast.makeText(getApplicationContext(), pair.getNegaraTittle(), Toast.LENGTH_LONG).show();
//        }

    }

    private void negaraParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Cari Negara");
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                negaraAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
