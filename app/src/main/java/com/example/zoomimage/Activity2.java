package com.example.zoomimage;


import static com.example.zoomimage.MainActivity.ref;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity2 extends AppCompatActivity {

    RelativeLayout relativeLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        relativeLayout = findViewById(R.id.relativeLayout);
        switchBtn = findViewById(R.id.switchBtn);

        Uri uri = getIntent().getData();
        Drawable drawable = uriToDrawable(uri, getContentResolver());

        relativeLayout.setBackground(drawable);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            String uniqueID = UUID.randomUUID().toString();
                            addNewCircle(event, uniqueID);
                            return false;
                        }
                    });
                } else {
                    for (int i = 0; i < relativeLayout.getChildCount(); i++) {
                        View child = relativeLayout.getChildAt(i);
                        if (child instanceof CircleImageView) {
                            child.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    handleImageLongClick((CircleImageView) child);
                                    return true;
                                }
                            });

                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String imageID = (String) v.getTag();

                                    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("images_data").child(imageID);

                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String s = snapshot.getKey();
                                            Log.d("asd", s);
//                                            Lampada lampada = snapshot.getValue(Lampada.class);
//                                            String name = lampada.getName();
//                                            String description = lampada.getDescription();
//                                            String imageUri = lampada.getImageUrl();
//                                            Intent intent = new Intent(Activity2.this, Activity3.class);
//                                            intent.putExtra("name", name);
//                                            intent.putExtra("description", description);
//                                            intent.putExtra("imageUri", imageUri);
//                                            startActivity(intent);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    handleImageClick((CircleImageView) v);

                                }
                            });

                        }
                    }
                    relativeLayout.setOnTouchListener(null);
                }
            }
        });
//        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
////            QUESTO CODICE SERVE PER LA CREAZIONE DI IMMAGINI
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if (isChecked) {
//
//
//                            } else {
////                                circleImageView.setOnTouchListener(new View.OnTouchListener() {
////                                    @Override
////                                    public boolean onTouch(View v, MotionEvent event) {
////                                        Intent intent = new Intent(Activity2.this, Activity3.class);
////                                        startActivity(intent);
////
////                                        return false;
////                                    }
////                                });
////                                circleImageView.setOnLongClickListener(new View.OnLongClickListener() {
////                                    @Override
////                                    public boolean onLongClick(View v) {
////                                        relativeLayout.removeView(v);
////                                        return true;
////                                    }
////                                });
//                            }
//                        }
//                    });
//
//                    return false;
//                }
//            });
    }


    private void addNewCircle(MotionEvent event, String uniqueID) {

        CircleImageView circleImageView = new CircleImageView(getApplicationContext());
        circleImageView.setImageResource(R.drawable.verde);

        circleImageView.setId(Integer.parseInt(uniqueID));
        circleImageView.setX(event.getX());
        circleImageView.setY(event.getY());

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        circleImageView.setLayoutParams(params);
        relativeLayout.addView(circleImageView);
    }

    private void handleImageClick(CircleImageView v) {
        Intent intent = new Intent(Activity2.this, Activity3.class);
        intent.putExtra("imageID", v.getId());
        startActivity(intent);
    }

    private void handleImageLongClick(CircleImageView v) {
        relativeLayout.removeView(v);
    }

    public Drawable uriToDrawable(Uri uri, ContentResolver contentResolver) {
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return new BitmapDrawable(getResources(), bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}


