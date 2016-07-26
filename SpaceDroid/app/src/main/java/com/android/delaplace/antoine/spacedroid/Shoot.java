package com.android.delaplace.antoine.spacedroid;

import android.os.Handler;
import android.widget.ImageView;

public class Shoot {
    private float x;
    private float y;
    private ImageView image;
    private Handler handler;
    private GameActivity activity;
    private boolean isActive;

    public Shoot(float x, float y, ImageView image, GameActivity a) {
        this.isActive = true;
        this.activity = a;
        this.x = x;
        this.y = y;
        this.image = image;
        this.handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    Shoot.this.setY(30);
                    Shoot.this.collision();
                }
            }
        };

        this.move();
    }


    private void collision() {
        if (this.getY() + this.getImage().getHeight() < 0) {
            this.isActive = false;
            this.activity.getLayout().removeView(this.image);
        }

        synchronized (this.activity.getAliens()) {
            for (int i = 0; i < this.activity.getAliens().size(); i++) {
                Alien a = this.activity.getAliens().get(i);
                boolean conditionY = this.getY() < a.getY() + a.getCurrentImage().getHeight() && Shoot.this.getY() > a.getCurrentImage().getY();
                boolean conditionX = this.getX() + this.getImage().getWidth() / 2 > a.getX() && Shoot.this.getX() + this.getImage().getWidth() / 2 < a.getX() + a.getCurrentImage().getWidth();

                if (conditionX && conditionY) {
                    this.isActive = false;
                    a.removeLife();
                    this.activity.getLayout().removeView(this.image);
                }
            }
        }

    }


    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }


    public void setY(float y) {
        this.getImage().setY(this.getImage().getY() - y);
        this.y = this.getImage().getY();
    }

    public ImageView getImage() {
        return image;
    }

    private void move() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Shoot.this.isActive) {
                    handler.sendEmptyMessage(0);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
