package com.example.mobil_vizora;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DAO {
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Map<String, Long> allasok;
    Calendar cal = Calendar.getInstance();
    static Long allas;

    public DAO() {
        this.database = FirebaseDatabase.getInstance("https://mobil-vizora-default-rtdb.europe-west1.firebasedatabase.app/");
        this.auth = FirebaseAuth.getInstance();
        this.reference = database.getReference(auth.getCurrentUser().getUid())/*.child(new SimpleDateFormat("MMM").format(cal.getTime()))*/;
        Log.i("firebase", "reference: " + reference.getKey());

        GenericTypeIndicator<Map<String, Long>> gti = new GenericTypeIndicator<Map<String, Long>>() {};

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("firebase", "Snapshot: " + snapshot);

                allasok = snapshot.getValue(gti);
                if (allasok != null) {
                    Log.i("firebase", "Vízállások betöltve: " + allasok.toString());
                    allas = allasok.get("last");
                    allasok.remove("last");
                } else {
                    Log.i("firebase", "Nem sikerült betölteni a vízállásokat");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "loadPost:onCancelled", error.toException());
            }
        });
    }

    public Task<Void> add(int vizallas) {
        allas = (long) vizallas;
        reference.child("last").setValue(vizallas);
        return reference.child(new SimpleDateFormat("MMM").format(cal.getTime())).setValue(vizallas);
    }

    public Task<Void> update(String month, int vizallas) {
        return reference.child(month).setValue(vizallas);
    }

    public Task<Void> update() {
        return reference.child(new SimpleDateFormat("MMM").format(cal.getTime())).setValue(allas);
    }

    public Task<Void> remove(String key) {
        return reference.child(key).removeValue();
    }


    public List<String> getAllasok() {
        List<String> sb = new ArrayList<>();

        for (String s : allasok.keySet()) {
            sb.add(s + ": " + allasok.get(s));
        }

        return sb;
    }
}
