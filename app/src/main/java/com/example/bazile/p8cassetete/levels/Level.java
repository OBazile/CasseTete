package com.example.bazile.p8cassetete.levels;

import java.util.ArrayList;

/**
 * Created by nizar on 30/12/2015.
 */
public class Level {


    /**
     * Une plateforme a besion des données suivant
     * Un tableau ou on connait sa taille en x et en y
     * Position x,y
     * Booléen qui verifie qu'il est toucher
     * Booléen qui vérifie si il est placer
     * <p/>
     * exemple 5 pièces
     * 5 dimension differentes
     * 5 Position x et y differentes
     * 5 boolean touch pour permette à objet de déplacer
     * 5 boolean fixer pour signaler qu'il est fixer
     * Des casse differents à chaque briques c'est essentiel
     * <p/>
     * RAPPEL IL FAUT QUE LES TABLEAUX DE BLOC SOIT FORMER 0 PARTIE DE 0 en LINE et 0 COLONNE
     */

    // taille de la carte
    public static final int carteWidth = 5;
    public static final int carteHeight = 5;
    public static final int carteTileSize = 105;
    /**
     * Tableau
     **/
    public int[][] Tab; /* Bloc principale */
    public ArrayList<Bloc> list_bloc;
    /**
     * Variable global qui va récuperer les position i et j selon le drop
     **/
    int finalPostion_x, finalPosition_y; // global


    public Level() {
        Tab = new int[carteHeight][carteWidth];
        init_Tab();
        list_bloc = new ArrayList<>();
    }


    void init_Tab() {
            for (int i = 0; i < carteHeight; i++) {
                for (int j = 0; j < carteWidth; j++) {
                    Tab[i][j] = 1;
                }

            }
        }


    public void add_Bloc(int posX, int posY, int tailleX, int tailleY, int[][] forme, int id) {
        Bloc b = new Bloc(posX, posY, tailleX, tailleY, forme, id);
        this.list_bloc.add(b);
    }
}
