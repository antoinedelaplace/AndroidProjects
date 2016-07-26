package com.delaplace.antoine.rebounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by antoine on 10/12/2015.
 */
public class GameThread extends Thread {
    public boolean keepDrawing;
    private Context context;
    private SurfaceHolder mSurfaceHolder;
    private int canvasWidth;
    private int canvasHeight;
    private Paint redPaint;
    private Paint blackPaint;
    private Ball ball;
    private Bar barJ1;
    private Bar barJ2;
    private boolean movingBar;

    public GameThread(SurfaceHolder mSurfaceHolder, Context context) {
        this.keepDrawing = true;
        this.context = context;
        this.mSurfaceHolder = mSurfaceHolder;
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.blackPaint = new Paint();
        this.blackPaint.setColor(Color.BLACK);
    }

    public Bar getBarJ1() {
        return barJ1;
    }

    public Bar getBarJ2() {
        return barJ2;
    }

    @Override
    public void run() {
        while (keepDrawing) {
            Canvas canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    updatePhysics();
                    onDraw(canvas);
                }
            } finally {
                if (canvas != null)
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}
        }
    }
    /**
     * Appelée par la boucle du thread, doit dessiner sur le canevas l'état actuel du jeu
     * @param c Le canevas utilisé pour dessiner
     */
    private void onDraw(Canvas c) {
        c.drawRect(0, 0, this.canvasWidth, this.canvasHeight, this.blackPaint);
        c.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), this.redPaint);
        c.drawRect(barJ1.getX(), barJ1.getY(), barJ1.getX() + barJ1.getLargeur(), barJ1.getY() + barJ1.getEpaisseur(), this.redPaint);
        c.drawRect(barJ2.getX(), barJ2.getY(), barJ2.getX() + barJ2.getLargeur(), barJ2.getY() + barJ2.getEpaisseur(), this.redPaint);
    }
    /**
     * Appelée lorsque la vue change de taille
     * @param width largeur de la surface
     * @param height hauteur de la surface
     */
    public void setSurfaceSize(int width, int height) {
        synchronized (this.mSurfaceHolder) {
            this.canvasWidth = width;
            this.canvasHeight = height;
            int largeurBar = 150;
            this.ball = new Ball( this.canvasWidth / 2, this.canvasHeight - 50, -1, -3);
            this.barJ1 = new Bar((this.canvasWidth - largeurBar) / 2, this.canvasHeight - 40, largeurBar);
            this.barJ2 = new Bar((this.canvasWidth - largeurBar) / 2, 40, largeurBar);
        }
    }

    /**
     * Méthode appelée dans la boucle du thread
     * met à jour la position des items
     * vérifie les collisions entre les items et avec les murs
     */
    private void updatePhysics() {
        ball.setX(ball.getX() + (int) ball.getxSpeed());
        ball.setY(ball.getY() + (int) ball.getySpeed());
    }

    /**
     * Indique si l'évènement a touché la barre en prenant en compte une tolérance autour de la barre
     * @param e Evènement du touché
     * @param tolerance Tolérance en px
     * @return
     */
    public boolean isTouchingBar(MotionEvent e, int tolerance, Bar bar) {
        return e.getX() >= bar.getX() - tolerance
                && e.getX() <= bar.getX() + bar.getLargeur() + tolerance
                && e.getY() >= bar.getY() - tolerance
                && e.getY() <= bar.getY() + bar.getEpaisseur() + tolerance;
    }

    /**
     * Indique si l'évènement a touché la barre en prenant en compte une tolérance autour de la barre
     * @param ev Evènement du touché
     * @param bar représente une des barres
     * @return
     */
    public void moveBar(MotionEvent ev, Bar bar) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN)
            this.movingBar = true;
        else if(ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
            this.movingBar = false;

        if(this.movingBar)
            bar.setX((int)ev.getX() - (barJ1.getLargeur() / 2));
    }


}

