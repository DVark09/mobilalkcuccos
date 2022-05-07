package com.example.mobil_vizora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class registerActivity extends AppCompatActivity {

    private static final int KEY = 33;
    private static final String CHANNEL_ID = "22";

    EditText email, password, password2;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);
        password2 = findViewById(R.id.regPass2);

        mAuth = FirebaseAuth.getInstance();

    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
        String em, pass, pass2;

        em = email.getText().toString();
        pass = password.getText().toString();
        pass2 = password2.getText().toString();

        if (em.isEmpty()) {
            email.setError("This field can't be empty!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            email.setError("Email address is not valid!");
            email.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError("This field can't be empty!");
            password.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            password.setError("The password has to be at least 6 character!");
            password.requestFocus();
            return;
        }

        if (pass2.isEmpty()) {
            password2.setError("This field can't be empty!");
            password2.requestFocus();
            return;
        }

        if (pass2.length() < 6) {
            password2.setError("The password has to be at least 6 character!");
            password2.requestFocus();
            return;
        }

        if (!pass.equals(pass2)) {
            password2.setError("The passwords do not match!");
            password2.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showNotification(false);
                Toast.makeText(registerActivity.this, "Sikeres regisztráció", Toast.LENGTH_LONG).show();

                Intent profileintent = new Intent(this, profileActivity.class);

                profileintent.putExtra("seckey", KEY);
                startActivity(profileintent);
            } else {
                Toast.makeText(registerActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showNotification(Boolean what){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.noti);
            String description = getString(R.string.desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, profileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Gratulálok!")
                .setContentText("Sikeres " + (what ? "bejelentkezés!" : "regisztráció!"))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(new Random().nextInt(), builder.build());
    };
}