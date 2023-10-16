package com.example.zoomimage;

import static com.example.zoomimage.R.drawable.download;
import static com.example.zoomimage.R.drawable.planimetria1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.io.IOException;

public class Activity2 extends AppCompatActivity {

    PhotoView image;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        image = findViewById(R.id.immagine);
        image.setImageURI(getIntent().getData());

        image.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                Log.d("coord2", "coordX2 is:" + x + ", coordY2 is:" + y);

                float bitmapX = x * 2450;
                float bitmapY = y * 2450;

                Bitmap originalBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Bitmap highlightedBitmap = highlightPixel(originalBitmap, bitmapX, bitmapY, 50);
                image.setImageBitmap(highlightedBitmap);

//                Button btn = new Button(getApplicationContext());
//                btn.setText("test");
//
//                btn.setX(x);
//                btn.setY(y);
//
//                Log.d("coord3", "coordX3 is:" + btn.getX() + ", coordY3 is:" + btn.getY());
            }
        });
    }

//    private Bitmap getBitmapFromView(View view) {
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(returnedBitmap);
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null) {
//            bgDrawable.draw(canvas);
//        } else {
//            canvas.drawColor(Color.WHITE);
//        }
//        view.draw(canvas);
//        return returnedBitmap;
//    }

    private Bitmap highlightPixel(Bitmap original, float x, float y, int radius) {
        Bitmap mutableBitmap = original.copy(Bitmap.Config.RGBA_F16, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, paint);
        return mutableBitmap;
    }
}


