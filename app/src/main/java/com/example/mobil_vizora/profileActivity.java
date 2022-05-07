package com.example.mobil_vizora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity {

    EditText allas;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView email;
    DAO dao;
    Button button;
    ConstraintLayout cl;
    boolean[] click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        allas = findViewById(R.id.allas);
        email = findViewById(R.id.emailAddress);
        button = findViewById(R.id.button2);
        cl = findViewById(R.id.cl);

        dao = new DAO();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        click = new boolean[]{true};

        if (user == null) {
            finish();
        } else {
            email.setText(user.getEmail());
        }
    }

    public void newEntry(View view) {
        String strallas = allas.getText().toString();

        dao.add(Integer.parseInt(strallas)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(profileActivity.this, "Új vízállás hozzáadva!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void listEntries(View view) {
        Intent allaokIntent = new Intent(this, allasokActivity.class);
        startActivity(allaokIntent);
    }
}