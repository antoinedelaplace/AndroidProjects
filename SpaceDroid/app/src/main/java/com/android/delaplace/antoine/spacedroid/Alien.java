package com.android.delaplace.antoine.spacedroid;

import android.widget.ImageView;

public class Alien {
    private boolean isShooter; // Alien can shoot or not
    private int lives;
    private ImageView staticImage;
    private ImageView motionImage;
    private ImageView currentImage;
    private GameActivity activity;

    public Alien(boolean isShooter, int lives, ImageView staticImage, ImageView motionImage, GameActivity g) {
        this.isShooter = isShooter;
        this.lives = lives;
        this.staticImage = staticImage;
        this.motionImage = motionImage;
        this.currentImage = staticImage;
        this.activity = g;
    }

    private void setImageAlien() {
        if (this.currentImage == this.staticImage) {
            this.currentImage = this.motionImage;
            this.activity.getLayout().removeView(staticImage);
            this.activity.getLayout().addView(motionImage);
        } else {
            this.currentImage = this.staticImage;
            this.activity.getLayout().removeView(motionImage);
            this.activity.getLayout().addView(staticImage);
        }
    }

    public boolean isShooter() {
        return isShooter;
    }

    public ImageView getCurrentImage() {
        return currentImage;
    }

    public float getX() {
        return this.currentImage.getX();
    }

    public void setX(float x) {
        this.currentImage.setX(x);
    }

    public float getWidth() {
        return this.currentImage.getWidth();
    }

    public float getY() {
        return this.currentImage.getY();
    }

    public void setY(float y) {
        this.currentImage.setY(y);
    }

    public float getHeight() {
        return this.currentImage.getHeight();
    }

    public void removeLife() {
        this.lives--;
        if (this.lives == 0)
            this.activity.removeAlien(this);
    }
}
