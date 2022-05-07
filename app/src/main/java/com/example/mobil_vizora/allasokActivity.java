package com.example.mobil_vizora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class allasokActivity extends AppCompatActivity {

    List<String> allasok;
    DAO dao;
    RecyclerView recView;
    RecViewAdapter RecViewAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allasok);
        dao = new DAO();

        recView = findViewById(R.id.recView);

        dao.update().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                allasok = dao.getAllasok();

                RecViewAd = new RecViewAdapter(allasokActivity.this, allasok);
                recView.setAdapter(RecViewAd);
                recView.setLayoutManager(new LinearLayoutManager(allasokActivity.this));
            }
        });
    }
}