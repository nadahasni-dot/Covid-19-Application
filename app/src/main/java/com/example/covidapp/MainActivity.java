package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidapp.jatim.KasusJatim;
import com.example.covidapp.negara.KasusNegara;
import com.example.covidapp.provinsi.KasusProvinsi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CardView cardNegara;
    private RequestQueue mQueue;
    ProgressDialog progressDialog;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);

        mQueue = Volley.newRequestQueue(this);

        updateTimeParse();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KasusFragment()).commit();
    }

    private void jsonParse() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/indonesia";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();
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

//                        Membuat klik card jatim
                        cardNegara = findViewById(R.id.card_list_jatim);

                        cardNegara.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, KasusJatimSalah.class);
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
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.cancel();

                Spinner spinner = findViewById(R.id.country_spinner);
                ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_country, android.R.layout.simple_spinner_item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(MainActivity.this);

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

    private void globalParsePositif() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/positif";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String text = jsonObject.getString("value");
//                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

                    TextView tv = findViewById(R.id.tv_positif);

                    tv.setText(text);

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

//                        Membuat klik card jatim
                    cardNegara = findViewById(R.id.card_list_jatim);

                    cardNegara.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Sumber Data sedang dalam perbaikan", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, KasusJatim.class);
                            startActivity(intent);
                        }
                    });

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

        mQueue.add(stringRequest);
    }

    private void globalParseSembuh() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/sembuh";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String text = jsonObject.getString("value");
//                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

                    TextView tv = findViewById(R.id.tv_sembuh);

                    tv.setText(text);

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

//                        Membuat klik card jatim
                    cardNegara = findViewById(R.id.card_list_jatim);

                    cardNegara.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, KasusJatim.class);
                            startActivity(intent);
                        }
                    });

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

        mQueue.add(stringRequest);
    }

    private void globalParseMeninggal() {
        progressDialog.setMessage("Updating data.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        String url = "https://api.kawalcorona.com/meninggal";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String text = jsonObject.getString("value");
//                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

                    TextView tv = findViewById(R.id.tv_meninggal);

                    tv.setText(text);

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

//                        Membuat klik card jatim
                    cardNegara = findViewById(R.id.card_list_jatim);

                    cardNegara.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, KasusJatim.class);
                            startActivity(intent);
                        }
                    });

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

        mQueue.add(stringRequest);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_kasus:
                            updateTimeParse();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

//        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        if (text.equalsIgnoreCase("Indonesia")) {
            jsonParse();
        } else {
            globalParsePositif();
            globalParseSembuh();
            globalParseMeninggal();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
