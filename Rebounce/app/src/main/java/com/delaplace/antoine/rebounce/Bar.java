package com.delaplace.antoine.rebounce;

/**
 * Classe représentant le palet permettant de renvoyer la balle
 */
public class Bar {
	
	// Coordonnées du coin en haut à gauche du palet
	private int x;
	private int y;
	
	private int largeur;
	private final int epaisseur = 20; // champs constant
	
	/**
	 * Constructeur du palet
	 * @param xCoord Abscisse de la barre
	 * @param yCoord Ordonné de la barre
	 */
	public Bar(int xCoord, int yCoord, int largeur) {
		this.x = xCoord;
		this.y = yCoord;
		
		this.largeur = largeur;
	}
	
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getLargeur() {
		return this.largeur;
	}
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
	public int getEpaisseur() {
		return this.epaisseur;
	}
}
