package com.example.zoomimage;



import static com.example.zoomimage.MainActivity.ref;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity2 extends AppCompatActivity {

    RelativeLayout relativeLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchBtn;

    String key;

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
                            addNewCircle(event);
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
//                                    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                                        if (result.getResultCode() == Activity.RESULT_OK) {
//                                            Intent data = result.getData();
//                                        }
//                                    });



                                    handleImageClick((CircleImageView) v);

                                }
                            });

                        }
                    }
                    relativeLayout.setOnTouchListener(null);
                }
            }
        });
    }


    private void addNewCircle(MotionEvent event) {

        CircleImageView circleImageView = new CircleImageView(getApplicationContext());
        circleImageView.setImageResource(R.drawable.verde);

        circleImageView.setX(event.getX());
        circleImageView.setY(event.getY());

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        circleImageView.setLayoutParams(params);
        relativeLayout.addView(circleImageView);

        key = ref.push().getKey();

        Lampada lampada = new Lampada("", "", "");
        if (key != null) ref.child(key).setValue(lampada);
    }

    private void handleImageClick(CircleImageView v) {
        Intent intent = new Intent(Activity2.this, Activity3.class);
//        activityResultLauncher.launch(intent);
        intent.putExtra("key", key);
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


