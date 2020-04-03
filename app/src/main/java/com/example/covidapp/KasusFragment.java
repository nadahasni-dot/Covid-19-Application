package com.example.covidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class KasusFragment extends Fragment {
    View view;
    WebView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kasus, container, false);

        String mapUrl1 = "http://covid19dev.jatimprov.go.id/xweb/draxi";
        String mapUrl2 = "https://www.google.com/maps/place/Kabupaten+Jember,+Jawa+Timur/@-8.17515,113.683965,14z/data=!4m5!3m4!1s0x2dd690a7abde8777:0xdeb6730c83a3dd2e!8m2!3d-8.1844859!4d113.6680747?shorturl=1";
        mapView = (WebView) view.findViewById(R.id.maps);
        mapView.setWebViewClient(new WebViewClient());
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.loadUrl(mapUrl2);

        return inflater.inflate(R.layout.fragment_kasus, container, false);
    }
}
