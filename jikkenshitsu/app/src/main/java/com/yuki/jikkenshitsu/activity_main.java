package com.yuki.jikkenshitsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.*;

import java.util.UUID;

public class activity_main extends AppCompatActivity {
    private static final String TAG = "MainActivity.java";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dateRef = db.collection("dates").document("hike");
    private CollectionReference datesRef = db.collection("dates");

    private TextView dateName;
    private TextView dateDescription;

    private EditText mAddTitleEt, mAddDescriptionEt;
    private Button mSaveDataBtn;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav_main = findViewById(R.id.nav_main);
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupWithNavController(nav_main, navController);

//        mAddTitleEt = (EditText) findViewById(R.id.addTitle);
//        mAddDescriptionEt = (EditText) findViewById(R.id.addDescription);
//        mSaveDataBtn = (Button) findViewById(R.id.saveData);
//
//        pd = new ProgressDialog(this);
//
//        mSaveDataBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = mAddTitleEt.getText().toString().trim();
//                String description = mAddDescriptionEt.getText().toString().trim();
//                uploadData(title, description);
//            }
//        });
    }

    private void uploadData(String title, String description) {
        pd.setTitle("Adding Data");
        pd.show();
        String id = UUID.randomUUID().toString();

        db.collection("temp").document("id").set(title)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(activity_main.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(activity_main.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        db.collection("temp").document("id").set(description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(activity_main.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(activity_main.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadDate(View v) {
        dateRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("title");
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
}