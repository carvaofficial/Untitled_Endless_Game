package com.example.untitledendlessgame.Resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import com.example.untitledendlessgame.R;

public class SurfaceViewTools {
    private Context context;
    public Paint pRects, pIcons;
    public Paint[] pBold = new Paint[5];
    public Paint[] pRegular = new Paint[5];
    public int iconSeparation, optionSeparation, landSeparation;
    public static boolean intentFlag;

    public SurfaceViewTools(Context context) {
        this.context = context;
        iconSeparation = 0;
        optionSeparation = 0;
        landSeparation = 0;
        intentFlag = false;

        //Inicialización pinceles:
        //Paints fuente gruesa
        for (int i = 0; i < pBold.length; i++) {
            pBold[i] = new Paint();
            pBold[i].setColor(Color.WHITE);
            setTypeface(pBold[i], R.font.comfortaa_bold, "fonts/comfortaa-bold.ttf");
            pBold[i].setAntiAlias(true);
        }

        //Paints fuente regular
        for (int i = 0; i < pRegular.length; i++) {
            pRegular[i] = new Paint();
            pRegular[i].setColor(Color.WHITE);
            setTypeface(pRegular[i], R.font.comfortaa_regular, "fonts/comfortaa-regular.ttf");
            pRegular[i].setAntiAlias(true);
        }

        //Paint iconos
        pIcons = new Paint();
        pIcons.setColor(Color.WHITE);
        setTypeface(pIcons, R.font.icons_solid, "fonts/icons-solid.ttf");
        pIcons.setAntiAlias(true);

        //Paint para rectángulos límite de onTouch
        pRects = new Paint();
        pRects.setColor(Color.argb(0, 0, 0, 0));
        pRects.setColor(Color.BLACK);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(3);
    }

    public int getPixels(float dp) {
        return (int) (dp * Tools.metrics.density);
    }

    public Bitmap scale(int res, int newWidth, int newHeight) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bitmapAux.getWidth() && newHeight == bitmapAux.getHeight()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, newWidth, newHeight, true);
    }

    public Bitmap scale(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap.getHeight() == newHeight && bitmap.getWidth() == newWidth) return bitmap;
        return Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * newHeight) / bitmap.getHeight(),
                (bitmap.getHeight() * newWidth) / bitmap.getWidth(), true);
    }

    public Bitmap scaleWidth(int res, int newWidth) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bitmapAux.getWidth()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, newWidth, (bitmapAux.getHeight() * newWidth) / bitmapAux.getWidth(), true);
    }

    public Bitmap scaleHeight(int res, int newHeight) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newHeight == bitmapAux.getHeight()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * newHeight) / bitmapAux.getHeight(), newHeight, true);
    }

    private Typeface setTypeface(Paint p, int resource, String asset){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return p.setTypeface(context.getResources().getFont(resource));
        } else {
            return p.setTypeface(Typeface.createFromAsset(context.getAssets(), asset));
        }
    }
}
