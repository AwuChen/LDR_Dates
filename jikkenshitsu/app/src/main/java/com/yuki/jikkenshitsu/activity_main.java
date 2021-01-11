package com.yuki.jikkenshitsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.*;

public class activity_main extends AppCompatActivity {
    private static final String TAG = "MainActivity.java";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dateRef = db.collection("dates").document("hike");
    private CollectionReference datesRef = db.collection("dates");

    private TextView dateName;
    private TextView dateDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav_main = findViewById(R.id.nav_main);
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(nav_main, navController);
    }

    public void loadDate(View v) {
        dateRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String description = documentSnapshot.getString("description");

                            dateName = findViewById(R.id.dateName);
                            dateName.setText(name);

                            dateDescription = findViewById(R.id.dateDescription);
                            dateDescription.setText(description);
                        } else {
                            Toast.makeText(activity_main.this, "Document does not exist.", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Document does not exist.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_main.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, e.toString());
                    }
                });
    }

    public void randomize(View v) {
    }
}