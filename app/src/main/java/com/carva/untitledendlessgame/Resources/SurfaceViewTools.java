package com.carva.untitledendlessgame.Resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import com.carva.untitledendlessgame.R;

/**
 * Herramientas generales para SurfaceView. Contiene Paints, tamaños de separación,
 * funciones de escalado para imágenes, etc.
 *
 * @author carva
 * @version 1.0
 */
public class SurfaceViewTools {
    /**
     * Contexto de la aplicación.
     *
     * @see Context
     */
    private Context context;
    /**
     * Pincel para visualizar rectángulos.
     */
    public Paint pRects;
    /**
     * Pincel para dibujar iconos del recurso de fuente <b>icons_solid</b>.
     */
    public Paint pIcons;
    /**
     * Pincel para dibujado de textos con el recurso de fuente <b>comfortaa_bold</b>.
     */
    public Paint[] pBold = new Paint[5];
    /**
     * Pincel para dibujado de textos con el recurso de fuente <b>comfortaa_regular</b>.
     */
    public Paint[] pRegular = new Paint[5];
    /**
     * Tamaño de separación de iconos.
     */
    public int iconSeparation;
    /**
     * Tamaño de separación de texto de la escena <code>MenuScene</code>
     *
     * @see com.carva.untitledendlessgame.Scenes.MenuScene
     */
    public int optionSeparation;
    /**
     * Tamaño de separación de texto en orientación horizontal.
     */
    public int landSeparation;
    /**
     * Comprueba si se ha realizado un <code>Intent</code> en alguna escena.
     */
    public static boolean intentFlag;

    /**
     * Inicialización de todas las herramientas (pinceles, tamaños, etc.).
     *
     * @param context contexto de la aplicación
     * @see Context
     */
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
//        pRects.setColor(Color.RED);
        pRects.setStyle(Paint.Style.STROKE);
        pRects.setStrokeWidth(3);
    }

    /**
     * Convierte densidad de píxeles (dp) a píxeles (px).
     *
     * @param dp densidad de pixeles a convertir
     * @return pixeles equivalentes a los dp del parámetro
     */
    public int getPixels(float dp) {
        return (int) (dp * Tools.metrics.density);
    }

    /**
     * Escala un recurso de imagen a un tamaño establecido por los parñametros.
     *
     * @param res       recurso de imagen.
     * @param newWidth  nuevo ancho de la imagen.
     * @param newHeight nueva altura de la imagen.
     * @return imagen reescalada convertida a <code>Bitmap</code>.
     */
    public Bitmap scale(int res, int newWidth, int newHeight) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bitmapAux.getWidth() && newHeight == bitmapAux.getHeight()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, newWidth, newHeight, true);
    }

    /**
     * Escala un mapa de bits (<code>Bitmap</code>) a un tamaño establecido por los parñametros.
     *
     * @param bitmap    imagen a escalar.
     * @param newWidth  nuevo ancho de la imagen.
     * @param newHeight nueva altura de la imagen.
     * @return imagen reescalada.
     */
    public Bitmap scale(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap.getHeight() == newHeight && bitmap.getWidth() == newWidth) return bitmap;
        return Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * newHeight) / bitmap.getHeight(),
                (bitmap.getHeight() * newWidth) / bitmap.getWidth(), true);
    }

    /**
     * Escala el ancho de un recurso de imagen.
     *
     * @param res      recurso de imagen.
     * @param newWidth nuevo ancho de imagen.
     * @return imagen reescalada convertida a <code>Bitmap</code>.
     */
    public Bitmap scaleWidth(int res, int newWidth) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newWidth == bitmapAux.getWidth()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, newWidth, (bitmapAux.getHeight() * newWidth) / bitmapAux.getWidth(), true);
    }

    /**
     * Escala la altura de un recurso de imagen.
     *
     * @param res       recurso de imagen.
     * @param newHeight nueva altura de imagen.
     * @return imagen reescalada convertida a <code>Bitmap</code>.
     */
    public Bitmap scaleHeight(int res, int newHeight) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (newHeight == bitmapAux.getHeight()) {
            return bitmapAux;
        }
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * newHeight) / bitmapAux.getHeight(), newHeight, true);
    }

    /**
     * Establece una fuente a un pincel. Dependiendo de la versión de Android, usa recursos o asset.
     *
     * @param p        pincel de dibujo.
     * @param resource recurso de fuente.
     * @param asset    asset de fuente.
     */
    private void setTypeface(Paint p, int resource, String asset) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p.setTypeface(context.getResources().getFont(resource));
        } else {
            p.setTypeface(Typeface.createFromAsset(context.getAssets(), asset));
        }
    }
}
