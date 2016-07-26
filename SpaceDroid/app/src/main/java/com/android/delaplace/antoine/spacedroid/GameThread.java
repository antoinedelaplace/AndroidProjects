package com.android.delaplace.antoine.spacedroid;

import android.os.Handler;

public class GameThread extends Thread {
    final int aliensYSpeed = 60;
    final int sleep = 500;
    public String threadState;
    private GameActivity activity;
    private Handler handler;
    private int aliensXSpeed;
    private boolean isGoingToRight; //True if Aliens go to the right direction

    public GameThread(GameActivity activity) {
        this.activity = activity;
        this.aliensXSpeed = 30;
        this.threadState = "GAME";
        this.isGoingToRight = true;
        this.handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0 && GameThread.this.threadState.equals("GAME")) {
                    synchronized (GameThread.this.activity.getAliens()) {
                        GameThread.this.move();
                    }
                }
            }
        };

    }

    public String getThreadState() {
        return threadState;
    }

    public void setThreadState(String threadState) {
        this.threadState = threadState;
    }

    @Override
    public void run() {
        while (true) {
            handler.sendEmptyMessage(0);

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        for (int i = this.activity.getAliens().size() - 1; i >= 0; i--) {
            Alien a = this.activity.getAliens().get(i);
            //this.setImageAlien();

            if (this.isGoingToRight) {
                //If an alien will be outside of the right edge screen
                if (this.activity.getXLayout() + a.getX() + a.getWidth() + this.aliensXSpeed > this.activity.getXLayout() + this.activity.getWidthLayout()) {
                    this.isGoingToRight = false;
                    //Aliens go the bottom
                    this.verticalMove();
                    break;
                } else
                    a.setX(a.getX() + aliensXSpeed);
            } else {
                //If an alien will be outside of the left edge screen
                if (a.getX() - aliensXSpeed < 0) {
                    this.isGoingToRight = true;
                    //Aliens go the bottom
                    this.verticalMove();
                    break;
                } else
                    a.setX(a.getX() - aliensXSpeed);
            }
        }
    }

    public void verticalMove() {
        for (int i = this.activity.getAliens().size() - 1; i >= 0; i--) {
            Alien a = this.activity.getAliens().get(i);
            if (this.activity.getYLayout() + a.getY() + a.getHeight() + aliensYSpeed > this.activity.getYLayout() + this.activity.getHeightLayout()) {
                //this.activity.stop(false);
            }
            a.setY(a.getY() + aliensYSpeed);
        }
    }
}