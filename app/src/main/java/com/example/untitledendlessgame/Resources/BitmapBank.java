package com.example.untitledendlessgame.Resources;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.untitledendlessgame.R;

public class BitmapBank {
    Bitmap background;
    Bitmap[] character;
    Bitmap boxTop, boxBottom, boxLeft, boxRight;
    Bitmap whiteBoxTop, whiteBoxBottom, whiteBoxLeft, whiteBoxRight;
    int screenWidth, screenHeight;
    private int img;

    public BitmapBank(Resources resource, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        if (Tools.theme1) {
            img = R.drawable.background1;
        }
        if (Tools.theme2) {
            img = R.drawable.background2;
        }
        background = BitmapFactory.decodeResource(resource, img);
        background = AppConstants.SVTools.scaleHeight(img, screenHeight);

        character = new Bitmap[6];
        for (int i = 0; i < character.length; i++) {
            character[i] = BitmapFactory.decodeResource(resource, R.drawable.character);
            character[i] = AppConstants.SVTools.scale(character[i], character[i].getWidth() / 5,
                    character[i].getHeight() / 5);
        }

        //Inicialización de cajas:
        //Cajas marrones
        boxTop = BitmapFactory.decodeResource(resource, R.drawable.box_top);
        boxBottom = BitmapFactory.decodeResource(resource, R.drawable.box_bottom);

        //Cajas blancas
        whiteBoxTop = BitmapFactory.decodeResource(resource, R.drawable.box_w_top);
        whiteBoxBottom = BitmapFactory.decodeResource(resource, R.drawable.box_w_bottom);

        //Reescalado de cajas
        boxTop = AppConstants.SVTools.scale(boxTop, boxTop.getWidth() / 4,
                boxTop.getHeight() / 4);
        boxBottom = AppConstants.SVTools.scale(boxBottom, boxBottom.getWidth() / 4,
                boxBottom.getHeight() / 4);
        whiteBoxTop = AppConstants.SVTools.scale(whiteBoxTop, whiteBoxTop.getWidth() / 4,
                whiteBoxTop.getHeight() / 4);
        whiteBoxBottom = AppConstants.SVTools.scale(whiteBoxBottom, whiteBoxBottom.getWidth() / 4,
                whiteBoxBottom.getHeight() / 4);
    }

    public Bitmap getBackground() {
        return background;
    }

    public int getBackgroundWidth() {
        return background.getWidth();
    }

    public int getBackgroundHeight() {
        return background.getHeight();
    }

    public Bitmap getCharacter(int frame) {
        return character[frame];
    }

    public int getCharacterWidth() {
        return character[0].getWidth();
    }

    public int getCharacterHeight() {
        return character[0].getHeight();
    }

    public Bitmap getBoxTop() {
        return boxTop;
    }

    public Bitmap getBoxBottom() {
        return boxBottom;
    }

    public Bitmap getBoxLeft() {
        return boxLeft;
    }

    public Bitmap getBoxRight() {
        return boxRight;
    }

    public Bitmap getWhiteBoxTop() {
        return whiteBoxTop;
    }

    public Bitmap getWhiteBoxBottom() {
        return whiteBoxBottom;
    }

    public Bitmap getWhiteBoxLeft() {
        return whiteBoxLeft;
    }

    public Bitmap getWhiteBoxRight() {
        return whiteBoxRight;
    }

    public int getBoxWidth() {
        return boxTop.getWidth();
    }

    public int getBoxHeight() {
        return boxTop.getHeight();
    }

    public Bitmap scaleImage(Bitmap bitmap) {
        Log.i("Image", "scaleImage: " + getBackgroundWidth() + " " + getBackgroundHeight());
        float WHRatio = getBackgroundWidth() / getBackgroundHeight();
        Log.i("Image", "scaleImage: " + WHRatio);
        int scaledWidth = (int) (WHRatio * screenHeight);
        return Bitmap.createScaledBitmap(bitmap, scaledWidth, screenHeight, true);
    }

}
