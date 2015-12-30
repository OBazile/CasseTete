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
    /**
     * Position
     **/
    int Pos0X = 100, Pos0Y = 100; // position turquoise
    int Pos1X = 1000, Pos1Y = 200; // position jaune
    int Pos2X = 300, Pos2Y = 100;  // position rouge
    int Pos3X = 800, Pos3Y = 300;  // position bleu
    int Pos4X = 300, Pos4Y = 1000;  // position black
    int Pos5X = 400, Pos5Y = 100;  // position black

    public Level() {
        Tab = new int[carteHeight][carteWidth];

    }

    void init_bloc(int[][] t) {
        for (Bloc b : list_bloc) {
            for (int i = 0; i < carteHeight; i++) {
                for (int j = 0; j < carteWidth; j++) {
                    b.forme[i][j] = 0;
                }

            }
        }
    }

    public void add_Bloc(int posX, int posY, int tailleX, int tailleY, int[][] forme, int id) {
        Bloc b = new Bloc(posX, posY, tailleX, tailleY, forme, id);
        list_bloc.add(b);
    }

    void creer_bloc() {
        int[][] blc0 = new int[carteHeight][carteWidth], blc1 = new int[carteHeight][carteWidth],
                blc2 = new int[carteHeight][carteWidth], blc3 = new int[carteHeight][carteWidth],
                blc4 = new int[carteHeight][carteWidth], blc5 = new int[carteHeight][carteWidth];

        init_bloc(blc0);
        init_bloc(blc1);
        init_bloc(blc2);
        init_bloc(blc3);
        init_bloc(blc4);
        init_bloc(blc5);


        // premiers bloc
        blc0[0][0] = 2;
        blc0[0][1] = 2;
        blc0[0][2] = 2;
        blc0[1][1] = 2;

        // 2 bloc
        blc1[1][0] = 3;
        blc1[2][0] = 3;
        blc1[3][0] = 3;
        blc1[2][1] = 3;
        blc1[2][1] = 3;

        // 3e bloc
        blc2[0][0] = 4;
        blc2[0][1] = 4;
        blc2[0][2] = 4;
        blc2[0][3] = 4;

        // 4e bloc
        blc3[0][0] = 5;
        blc3[0][1] = 5;
        blc3[0][2] = 5;
        blc3[1][2] = 5;

        // 5e bloc
        blc4[0][0] = 6;
        blc4[0][1] = 6;
        blc4[1][0] = 6;
        blc4[1][1] = 6;

        // 6e bloc
        blc5[0][0] = 7;
        blc5[0][1] = 7;
        blc5[1][1] = 7;
        blc5[2][1] = 7;

    }
}
