package com.android.delaplace.antoine.spacedroid;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends Activity implements SensorEventListener {
    //Sensor
    static float xGyroscope;
    final int aliensNumber = 9;
    final int aliensLives = 10;
    final int spaceBetweenAliens = 70;
    private RelativeLayout principalLayout;
    private GameThread thread;
    private ArrayList<Alien> aliens = new ArrayList<Alien>();
    private ImageView ship;
    private SensorManager sensorManager;
    private Sensor gyroscope;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        principalLayout = (RelativeLayout) findViewById(R.id.layout);
        principalLayout.setBackgroundResource(R.drawable.background);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.InitAliens(aliensNumber, false, aliensLives);
        this.thread = new GameThread(this);
        this.thread.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Init ship image
        ship = new ImageView(this);
        ship.setImageResource(R.drawable.vaisseau);
        ship.setX(this.principalLayout.getWidth() / 2);//Place the ship in X position
        ship.setY(this.principalLayout.getHeight() - ship.getHeight() - 100);//Place the ship in Y position
        principalLayout.addView(ship);

        //Activate sensor event
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);

		/* TODO
		AnimationDrawable anim = (AnimationDrawable) principalLayout.getBackground() ;
		anim.start();
		*/
    }

    @Override
    protected void onPause() {
        //Inactivate sensor event
        sensorManager.unregisterListener(this, gyroscope);
        // and don't forget to pause the thread that redraw the xyAccelerationView
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    //Action when you turn your device
    public void onSensorChanged(SensorEvent se) {
        GameActivity.xGyroscope = se.values[0];

        if (GameActivity.xGyroscope < -1)
            if ((ship.getX() + 60) < (principalLayout.getWidth()))
                ship.setX(ship.getX() + 20);

        if (GameActivity.xGyroscope > 1)
            if ((ship.getX() - 10) > 0)
                ship.setX(ship.getX() - 20);
    }

    //Action when you touch the screen
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.thread.getThreadState().equals("GAME")) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                this.addShoot();
            }

            return true;
        } else return false;

    }

    public ImageView getShip() {
        return ship;
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public RelativeLayout getLayout() {
        return principalLayout;
    }

    public float getXLayout() {
        return principalLayout.getX();
    }

    public float getYLayout() {
        return principalLayout.getY();
    }

    public float getWidthLayout() {
        return principalLayout.getWidth();
    }

    public float getHeightLayout() {
        return principalLayout.getHeight();
    }


    public void stop(boolean win) {
        this.thread.setThreadState("END");
        sensorManager.unregisterListener(this, gyroscope);

        if (win)
            (Toast.makeText(this, "Winner", Toast.LENGTH_SHORT)).show();
        else
            (Toast.makeText(this, "Looser", Toast.LENGTH_SHORT)).show();

    }

    private void addShoot() {
        ImageView missile = new ImageView(this);
        missile.setX(this.getShip().getX());
        missile.setY(this.getShip().getY() - this.getShip().getHeight());
        missile.setImageResource(R.drawable.missile2);
        this.principalLayout.addView(missile);
        new Shoot(missile.getX(), missile.getY(), missile, this);
    }

    //Init aliens table
    public void InitAliens(int nbAliens, boolean shoot, int life) {
        for (int i = 0; i < nbAliens; i++) {
            ImageView staticImage = new ImageView(this);
            ImageView motionImage = new ImageView(this);
            staticImage.setImageResource(R.drawable.green_alien_static);
            motionImage.setImageResource(R.drawable.green_alien_motion);
            staticImage.setX(staticImage.getX() + spaceBetweenAliens * i);
            principalLayout.addView(staticImage);
            this.aliens.add(new Alien(shoot, life, staticImage, motionImage, this));
        }
    }

    public void removeAlien(Alien a) {
        synchronized (this.getAliens()) {
            this.principalLayout.removeView(a.getCurrentImage());
            this.getAliens().remove(a);

            if (this.getAliens().size() == 0) {
                this.stop(true);

            }
        }
    }
}