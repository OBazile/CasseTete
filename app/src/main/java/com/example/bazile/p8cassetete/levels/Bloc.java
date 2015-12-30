package com.example.bazile.p8cassetete.levels;

/**
 * Created by nizar on 30/12/2015.
 */
public class Bloc {
    int posX, posY;
    boolean est_touche;
    boolean est_fixe;
    int[][] forme;
    int id;
    int tailleX, tailleY;

    public Bloc(int posX, int posY, int tailleX, int tailleY, int[][] f, int i) {
        this.posX = posX;
        this.posY = posY;

        forme = f;
        est_fixe = false;
        est_touche = false;
        id = i;
    }

    public int getTailleX() {
        return tailleX;
    }

    public void setTailleX(int tailleX) {
        this.tailleX = tailleX;
    }

    public int getTailleY() {
        return tailleY;
    }

    public void setTailleY(int tailleY) {
        this.tailleY = tailleY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {

        this.posY = posY;
    }

    public boolean isEst_touche() {
        return est_touche;
    }

    public void setEst_touche(boolean est_touche) {
        this.est_touche = est_touche;
    }

    public boolean isEst_fixe() {
        return est_fixe;
    }

    public void setEst_fixe(boolean est_fixe) {
        this.est_fixe = est_fixe;
    }

    public int[][] getForme() {
        return forme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
