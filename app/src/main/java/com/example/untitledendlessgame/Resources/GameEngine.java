package com.example.untitledendlessgame.Resources;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    GameBackground background;
    public Character character;
    public static int velocity = 3;
    public boolean gameState;
    public ArrayList<Box> boxes;
    Random random;
    int score, scoringBox;

    public GameEngine() {
        background = new GameBackground(velocity);
        character = new Character(5);
        gameState = false;
        boxes = new ArrayList<>();
        random = new Random();

        //Inicialización cajas
        for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
            int boxX = Tools.SCREEN_WIDTH + i * AppConstants.distanceBetweenBoxes;
            int topBoxOffsetY = AppConstants.minBoxOffsetY + random.nextInt(AppConstants.maxBoxOffsetY
                    - AppConstants.minBoxOffsetY + 1);
            Box box = new Box(boxX, topBoxOffsetY);
            boxes.add(box);
        }

        //Inicialización marcador para puntuación
        score = 0;
        scoringBox = 0;
    }

    public void updateAndDrawBackground(Canvas canvas) {
        //Dibujo de background
        background.setX(background.getX() - background.getVelocity());
        if (background.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
            background.setX(0);
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX(), background.getY(), null);
        if (background.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - Tools.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX() +
                    AppConstants.getBitmapBank().getBackgroundWidth(), background.getY(), null);
        }
    }

    //TODO hacer que el personaje no pueda subir mas del alto de la pantalla (ver tutorial Udemy - Character)
    public void updateAndDrawCharacter(Canvas canvas) {
        int currentFrame = character.getCurrentFrame();
        if (gameState) {
            if (character.getY() < Tools.SCREEN_HEIGHT - AppConstants.getBitmapBank().getCharacterHeight() * 2 ||
                    character.getVelocity() < 0) {
                character.setVelocity(character.getVelocity() + AppConstants.gravity);
                character.setY(character.getY() + character.getVelocity());
            }
        }
        //Colisión personaje
        character.setCollision(new Rect(character.getX(), character.getY(),
                character.getX() + AppConstants.getBitmapBank().getCharacterWidth(),
                character.getY() + AppConstants.getBitmapBank().getCharacterHeight()));

        canvas.drawBitmap(AppConstants.getBitmapBank().getCharacter(currentFrame), character.getX(),
                character.getY(), null);
        canvas.drawRect(character.getCollision(), AppConstants.SVTools.pRects);
        currentFrame++;
        if (currentFrame > Character.maxFrame) {
            currentFrame = 0;
        }
        character.setCurrentFrame(currentFrame);
    }

    public void updateAndDrawBoxes(Canvas canvas) {
        if (gameState) {
            if (boxes.get(scoringBox).getX() < character.getX() + AppConstants.getBitmapBank().getBoxWidth() / 2) {
                score++;
                scoringBox++;
                if (scoringBox > AppConstants.numberOfBoxes - 1) {
                    scoringBox = 0;
                }
            }

            for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
                if (boxes.get(i).getX() < -AppConstants.getBitmapBank().getBoxWidth()) {
                    boxes.get(i).setX(boxes.get(i).getX() + AppConstants.numberOfBoxes * AppConstants.distanceBetweenBoxes);
                    int topBoxOffsetY = AppConstants.minBoxOffsetY + random.nextInt(AppConstants.maxBoxOffsetY - AppConstants.minBoxOffsetY + 1);
                    boxes.get(i).setOffsetY(topBoxOffsetY);
                    boxes.get(i).setBoxColor();
                }
                boxes.get(i).setX(boxes.get(i).getX() - AppConstants.boxVelocity);

                switch (boxes.get(i).getBoxColor()) {
                    case 0:
                        canvas.drawBitmap(AppConstants.getBitmapBank().getBoxTop(), boxes.get(i).getX(), boxes.get(i).getTopBoxY(), null);
                        canvas.drawBitmap(AppConstants.getBitmapBank().getBoxBottom(), boxes.get(i).getX(), boxes.get(i).getBottomBoxY(), null);
                        break;
                    case 1:
                        canvas.drawBitmap(AppConstants.getBitmapBank().getWhiteBoxTop(), boxes.get(i).getX(), boxes.get(i).getTopBoxY(), null);
                        canvas.drawBitmap(AppConstants.getBitmapBank().getWhiteBoxBottom(), boxes.get(i).getX(), boxes.get(i).getBottomBoxY(), null);
                        break;
                }

                //Colisiones cajas
                for (int j = 0; j < boxes.get(i).getCollisions().length; j++) {
                    if (j == 0) {
                        boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getBottomBoxY(),
                                boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
                                boxes.get(i).getBottomBoxY() + AppConstants.getBitmapBank().getBoxHeight()), j);
                    } else {
                        boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getTopBoxY(),
                                boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
                                boxes.get(i).getTopBoxY() + AppConstants.getBitmapBank().getBoxHeight()), j);
                    }
                    canvas.drawRect(boxes.get(i).getCollisions()[j], AppConstants.SVTools.pRects);
                }
//                boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getBottomBoxY(),
//                        boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
//                        boxes.get(i).getBottomBoxY() + AppConstants.getBitmapBank().getBoxHeight()), 0);
//
//                boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getTopBoxY(),
//                        boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
//                        boxes.get(i).getTopBoxY() + AppConstants.getBitmapBank().getBoxHeight()), 1);

//                canvas.drawRect(boxes.get(i).getCollisions()[0], AppConstants.SVTools.pRects);
//                canvas.drawRect(boxes.get(i).getCollisions()[1], AppConstants.SVTools.pRects);
            }

            canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2 + AppConstants.SVTools.getPixels(5),
                    Tools.SCREEN_HEIGHT / 4 + AppConstants.SVTools.getPixels(5), AppConstants.SVTools.pBold[0]);
            canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2, Tools.SCREEN_HEIGHT / 4, AppConstants.SVTools.pBold[1]);

            if ((boxes.get(0).getCollisions()[0].intersect(character.getCollision()) ||
                    boxes.get(0).getCollisions()[1].intersect(character.getCollision())) ||
                    (boxes.get(1).getCollisions()[0].intersect(character.getCollision()) ||
                            boxes.get(1).getCollisions()[1].intersect(character.getCollision()))) {
                gameState = false;
            }
        }
    }
}
