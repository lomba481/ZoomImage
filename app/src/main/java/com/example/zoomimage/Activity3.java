package com.example.zoomimage;

import static com.example.zoomimage.MainActivity.ref;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Activity3 extends AppCompatActivity {

    EditText bloccoName, bloccoDescription;
    Button sendDatabtn;
    RelativeLayout pickImagebtn;
    ImageView viewPager;
    String key;
    Uri uri;
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

        key = getIntent().getStringExtra("key");
        Log.d("chiave", key);


        bloccoName = findViewById(R.id.NomeBlocco);
        bloccoDescription= findViewById(R.id.DescrizioneBlocco);

        sendDatabtn = findViewById(R.id.SalvaBtn);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bloccoName.getText().toString();
                String description = bloccoDescription.getText().toString();


                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(description)) {
                    Toast.makeText(Activity3.this, "Aggiungi dati.", Toast.LENGTH_SHORT).show();
                } else {
                    Lampada lampada = new Lampada();
                    lampada.setDescription(description);
                    lampada.setName(name);

                    try {
                        addDataFirebase(lampada, uri, key);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
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

    private void addDataFirebase(Lampada lampada, Uri uri, String key) throws FileNotFoundException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imageRef = storageReference.child("immagini");

        InputStream stream = getContentResolver().openInputStream(uri);

        UploadTask uploadTask = imageRef.putStream(stream);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String imageUrl = downloadUri.toString();
                    lampada.setImageUrl(imageUrl);
                    ref.child(key).setValue(lampada);
                });
            }
        });

//        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri download = taskSnapshot.getUploadSessionUri();
////                String image = download.toString();
////                lampada.setImageUri(image);
////                ref.child(key).setValue(lampada);
//
//
//
//                DatabaseReference newItem = ref.child(key);
//
//                newItem.child("name").setValue(name);
//                newItem.child("description").setValue(description);
//                newItem.child("image").setValue(download.toString());
//            }
//        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Activity3.this, "dati aggiunti", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity3.this, "Non riesco ad inserire i dati"+ error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}




