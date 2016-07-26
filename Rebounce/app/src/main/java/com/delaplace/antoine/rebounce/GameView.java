package com.delaplace.antoine.rebounce;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by antoine on 10/12/2015.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
        SurfaceHolder mSurfaceHolder;
        GameThread mThread;

        public GameView(Context context) {
            super(context);
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);

            mThread = new GameThread(mSurfaceHolder, context);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.mThread.setSurfaceSize(width,height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mThread.keepDrawing = true;
            mThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mThread.keepDrawing = false;

            boolean joined = false;
            while (!joined) {
                try {
                    mThread.join();
                    joined = true;
                } catch (InterruptedException e) {}
            }
        }

        public boolean onTouchEvent(MotionEvent ev) {
            int tolerance = 50;
            int maskedAction = ev.getActionMasked();

            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    if(this.mThread.isTouchingBar(ev, tolerance, this.mThread.getBarJ1()))
                        this.mThread.moveBar(ev, this.mThread.getBarJ1());
                    else if(this.mThread.isTouchingBar(ev, tolerance, this.mThread.getBarJ2()))
                        this.mThread.moveBar(ev, this.mThread.getBarJ2());
                    break;
                }
            }
            return true;
        }
}
