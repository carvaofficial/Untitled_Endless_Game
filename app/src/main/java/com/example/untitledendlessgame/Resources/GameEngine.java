package com.example.untitledendlessgame.Resources;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {
    GameBackground background;
    public Character character;
    private static int velocity = 3;
    private boolean gameState, running;
    private ArrayList<Box> boxes;
    private Random random;
    int score, scoringBox;

    public GameEngine() {
        background = new GameBackground(velocity);
        character = new Character(5);
        gameState = false;
        running = true;
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

    public boolean isGameState() {
        return gameState;
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void updateAndDrawBackground(Canvas canvas) {
        //Dibujo de background
        if (isRunning()) {
            background.setX(background.getX() - background.getVelocity());
            if (background.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
                background.setX(0);
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX(), background.getY(), null);
        if (background.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - Tools.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX() +
                    AppConstants.getBitmapBank().getBackgroundWidth(), background.getY(), null);
        }
    }

    public void updateAndDrawCharacter(Canvas canvas) {
        int currentFrame = character.getCurrentFrame();
        if (isGameState()) {
            //Establece una velocidad de salto y una gravedad, siempre que la posición Y del personaje
            //sea menor que la altura de la pantalla - su altura*2 (para que no llegue al borde de abajo de la pantalla)
            if ((character.getY() < Tools.SCREEN_HEIGHT - AppConstants.getBitmapBank().getCharacterHeight() * 2) ||
                    character.getVelocity() < 0) {
                character.setVelocity(character.getVelocity() + AppConstants.gravity);
                character.setY(character.getY() + character.getVelocity());
            }
            //Si la posición Y del personaje es menor a 0 (pantalla) - su altura/2 no puede subir más allá
            if (character.getY() < (-AppConstants.getBitmapBank().getCharacterHeight() / 2) && character.getVelocity() < 0) {
                character.setVelocity(0);
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

        //Cambio de fotograma del personaje
        if (isRunning()) {
            currentFrame++;
            if (currentFrame > Character.maxFrame) {
                currentFrame = 0;
            }
            character.setCurrentFrame(currentFrame);
        } else {

        }
    }

    public void updateAndDrawBoxes(Canvas canvas) {
        //Si el juego está en funcionamiento la puntuación se activa y las cajas se mueven
        if (isGameState()) {
            //Movimiento de cajas
            for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
                if (boxes.get(i).getX() < -AppConstants.getBitmapBank().getBoxWidth()) {
                    boxes.get(i).setX(boxes.get(i).getX() + AppConstants.numberOfBoxes * AppConstants.distanceBetweenBoxes);
                    int topBoxOffsetY = AppConstants.minBoxOffsetY + random.nextInt(AppConstants.maxBoxOffsetY - AppConstants.minBoxOffsetY + 1);
                    boxes.get(i).setOffsetY(topBoxOffsetY);
                    boxes.get(i).setBoxColor();
                }
                boxes.get(i).setX(boxes.get(i).getX() - AppConstants.boxVelocity);
            }
        }

        //Dibujo de cajas de diferente color
        for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
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
        }

        if ((boxes.get(0).getCollisions()[0].intersect(character.getCollision()) ||
                boxes.get(0).getCollisions()[1].intersect(character.getCollision())) ||
                (boxes.get(1).getCollisions()[0].intersect(character.getCollision()) ||
                        boxes.get(1).getCollisions()[1].intersect(character.getCollision()))) {
            setGameState(false);
            setRunning(false);
        }
    }

    public void updateandDrawScore(Canvas canvas) {
        //Puntuación: si el personaje pasa por el medio de las cajas, se suma puntuación
        if (isGameState()) {
            if (boxes.get(scoringBox).getX() < character.getX() + AppConstants.getBitmapBank().getBoxWidth() / 2) {
                score++;
                scoringBox++;
                if (scoringBox > AppConstants.numberOfBoxes - 1) {
                    scoringBox = 0;
                }
            }
        }

        //Dibujo de puntuación
        canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2 + AppConstants.SVTools.getPixels(5),
                Tools.SCREEN_HEIGHT / 4 + AppConstants.SVTools.getPixels(5), AppConstants.SVTools.pBold[0]);
        canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2, Tools.SCREEN_HEIGHT / 4, AppConstants.SVTools.pBold[1]);
    }
}
