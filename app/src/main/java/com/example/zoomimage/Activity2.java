package com.example.zoomimage;

import static com.example.zoomimage.R.drawable.download;
import static com.example.zoomimage.R.drawable.planimetria1;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity2 extends AppCompatActivity {
    RelativeLayout relativeLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        relativeLayout = findViewById(R.id.relativeLayout);

        Uri uri = getIntent().getData();
        Drawable drawable = uriToDrawable(uri, getContentResolver());

        relativeLayout.setBackground(drawable);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {

//            QUESTO CODICE SERVE PER LA CREAZIONE DI IMMAGINI
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                circleImageView.setImageResource(R.drawable.verde);

                circleImageView.setX(event.getX());
                circleImageView.setY(event.getY());

                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                circleImageView.setLayoutParams(params);
                relativeLayout.addView(circleImageView);

                circleImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Intent intent = new Intent(Activity2.this, Activity3.class);
                        startActivity(intent);

                        return false;
                    }
                });


//                QUESTO CODICE SERVE PER LA CREAZIONE DI BOTTONI

//                Button btn = new Button(getApplicationContext());
//                btn.setBackgroundResource(R.drawable.round_button);
//
//                int uniqueButtonId = View.generateViewId();
//                btn.setId(uniqueButtonId);
//
//                btn.setX(event.getX());
//                btn.setY(event.getY());
//
//
//                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
//                btn.setLayoutParams(params);
//
//                relativeLayout.addView(btn);
//
//
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(Activity2.this, Activity3.class);
//                        startActivity(intent);
//
//                    }
//
//                });
//
//                btn.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
////                        Button btnRemove = findViewById(uniqueButtonId);
//                        relativeLayout.removeView(v);
//                        return true;
//
//                    }
//                });

                return false;
            }
        });
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


//    final float[] getPointerCoords(ImageView view, MotionEvent e) {
//        final int index = e.getActionIndex();
//        final float[] coords = new float[]{e.getX(index), e.getY(index)};
//        Matrix matrix = new Matrix();
//        view.getImageMatrix().invert(matrix);
//        matrix.postTranslate(view.getScrollX(), view.getScrollY());
//        matrix.mapPoints(coords);
//        return coords;
//    }


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
//
//    private Bitmap highlightPixel(Bitmap original, float x, float y, int radius) {
//        Bitmap mutableBitmap = original.copy(Bitmap.Config.RGBA_F16, true);
//        Canvas canvas = new Canvas(mutableBitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(x, y, radius, paint);
//        return mutableBitmap;
//    }
}


