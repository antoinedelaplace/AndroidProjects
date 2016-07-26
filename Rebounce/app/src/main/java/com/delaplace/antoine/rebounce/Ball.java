package com.delaplace.antoine.rebounce;

public class Ball {
	// Position du centre de la balle par rapport au coté gauche
	private int x;
	
	// Position du centre de la balle par rapport au haut de la vue
	private int y;
	
	// Vitesse de la balle
	private double xSpeed;
	private double ySpeed;
	
	// Diametre de la balle
	private final int radius = 10;
	
	
	public Ball(int x, int y, double xSpeed, double ySpeed) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public int getRadius() {
		return this.radius;
	}
}
