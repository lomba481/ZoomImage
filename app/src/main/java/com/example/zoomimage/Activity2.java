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
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
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


//        image.setOnPhotoTapListener(new OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(ImageView view, float x, float y) {
//                Log.d("coord2", "coordX2 is:" + x + ", coordY2 is:" + y);
//                Display display = getWindowManager().getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                int width = size.x;
//                int height = size.y;
//
//                Log.d("coord1", ""+width+'-' + height);
//
//
//
//                float bitmapX = x * (image.getWidth()/width);
//                float bitmapY = y * (image.getHeight()/height);
//
//
//                Bitmap originalBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
//                Bitmap highlightedBitmap = highlightPixel(originalBitmap, bitmapX, bitmapY, 50);
//                image.setImageBitmap(highlightedBitmap);
//
//
//            }
//        });

//        image.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                float point[] = getPointerCoords(image, motionEvent);
//                Bitmap originalBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
//                Bitmap highlightedBitmap = highlightPixel(originalBitmap, point[0], point[1], 50);
//                image.setImageBitmap(highlightedBitmap);
//
//                return false;
//            }
//        });
        RelativeLayout relativeLayout;
        relativeLayout = findViewById(R.id.relativelayout);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageView nuova = new ImageView(getApplicationContext());
                nuova.setImageResource(R.drawable.download);
                nuova.setX(event.getX());
                nuova.setY(event.getY());
                nuova.setScaleX(0.5F);
                nuova.setScaleY(0.5F);
                relativeLayout.addView(nuova);

//                Shape shape = new Shape() {
//                    @Override
//                    public void draw(Canvas canvas, Paint paint) {
//                        paint.setColor(Color.RED);
//                        canvas.drawCircle(event.getX(), event.getY(), 25, paint);
//
//
//
//                    }
//                };
                return false;
            }
        });







    }



    final float[] getPointerCoords(ImageView view, MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[]{e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        view.getImageMatrix().invert(matrix);
        matrix.postTranslate(view.getScrollX(), view.getScrollY());
        matrix.mapPoints(coords);
        return coords;
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


