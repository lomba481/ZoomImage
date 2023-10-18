package com.example.zoomimage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Activity3 extends AppCompatActivity {

    EditText bloccoName, bloccoDescription;
    Button sendDatabtn;

    RelativeLayout pickImagebtn;

    ImageView viewPager;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;


    Uri uri;

    StorageReference storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        pickImagebtn = findViewById(R.id.ScegliImmagine);
        viewPager = findViewById(R.id.ImageView);

        pickImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageFromCamera();
            }

        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity3.this, Activity4.class);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        View freccia = findViewById(R.id.Freccia);
        freccia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        storage = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Lampada");

        bloccoName = findViewById(R.id.NomeBlocco);
        bloccoDescription = findViewById(R.id.DescrizioneBlocco);

//        blocco = new Blocco();

        sendDatabtn = findViewById(R.id.SalvaBtn);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bloccoName.getText().toString();
                String description = bloccoDescription.getText().toString();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(description)) {
                    Toast.makeText(Activity3.this, "Aggiungi dati.", Toast.LENGTH_SHORT).show();
                } else {
                    addDataFirebase(name, description, uri);
                }
            }
        });
    }


    private void PickImageFromCamera() {
        ImagePicker.with(Activity3.this)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        viewPager.setImageURI(uri);
    }
    private void addDataFirebase(String name, String description, Uri uri) {
//        blocco.setBloccoName(name);
//        blocco.setBloccoDescription(description);


        StorageReference filepath = storage.child("lampada_immagini").child(uri.getLastPathSegment());

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri download = taskSnapshot.getUploadSessionUri();

                DatabaseReference newItem = databaseReference.push();
                newItem.child("name").setValue(name);
                newItem.child("description").setValue(description);
                newItem.child("image").setValue(download.toString());
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(blocco);
                Toast.makeText(Activity3.this, "dati aggiunti", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity3.this, "Non riesco ad inserire i dati"+ error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}




