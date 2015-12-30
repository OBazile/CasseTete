package com.example.bazile.p8cassetete;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bazile.p8cassetete.levels.Bloc;

import java.util.ArrayList;
import java.util.Random;

public class CasseTeteView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    Bitmap casseVide, casseRouge, casseJaune,
            casseTurquoise, Fondecran, casseBleu,
            casseBlack, casseVert, win;

    Thread cv_thread;

    int carteCentreGauche, carteCentreHaut;

    // booleen qui sert à bloquer le jeu
    boolean bloquer;
    // le  score du joueur
    int Score = 0, nbPiece = 0;

    Random r = new Random();

    /**
     * Dessin
     **/
    Paint paint;
    /**
     * Variable global qui va récuperer les position i et j selon le drop
     **/
    int finalPostion_x, finalPosition_y; // global
    ArrayList<com.example.bazile.p8cassetete.levels.Level> levels;
    com.example.bazile.p8cassetete.levels.Level currentLevel;
    private Resources Res;
    private Context Context;
    private boolean go = true; // Pour le draw
    private SurfaceHolder holder;

    /**
     * Fonction pricipale
     **/
    public CasseTeteView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        Context = context;
        Res = Context.getResources();
        Fondecran = BitmapFactory.decodeResource(Res, R.drawable.fond_ecran);
        casseBleu = BitmapFactory.decodeResource(Res, R.drawable.cube);
        casseTurquoise = BitmapFactory.decodeResource(Res, R.drawable.cube1);
        casseRouge = BitmapFactory.decodeResource(Res, R.drawable.cube2);
        casseJaune = BitmapFactory.decodeResource(Res, R.drawable.cube3);
        casseVide = BitmapFactory.decodeResource(Res, R.drawable.cube4);
        casseBlack = BitmapFactory.decodeResource(Res, R.drawable.cube5);
        casseVert = BitmapFactory.decodeResource(Res, R.drawable.cube6);
        win = BitmapFactory.decodeResource(Res, R.drawable.win);

        initparameters();
        cv_thread = new Thread(this);

        setFocusable(true);
    }

    public void initparameters() {

        paint = new Paint();
        paint.setColor(0xff0000);
        paint.setDither(true);
        paint.setColor(0xFFFFFF00);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setTextAlign(Paint.Align.LEFT);

        levels = new ArrayList<>();

        adding_levels();

        int choix_level = r.nextInt(3);

        currentLevel = levels.get(r.nextInt(levels.size()));

        bloquer = false;

        // initialisation du tableau principale a vide
        initialisation();


        carteCentreHaut = (getHeight() - currentLevel.carteHeight * currentLevel.carteTileSize) / 2;
        carteCentreGauche = (getWidth() - currentLevel.carteWidth * currentLevel.carteTileSize) / 2;

        currentLevel.list_bloc.get(0).setPosX(carteCentreGauche + 4 * currentLevel.carteTileSize);
        currentLevel.list_bloc.get(0).setPosY(carteCentreGauche + 3 * currentLevel.carteTileSize);


        if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }
    }

    private void adding_levels() {

        int[][] blc0 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        initzeros(blc0);

        com.example.bazile.p8cassetete.levels.Level level1 = new com.example.bazile.p8cassetete.levels.Level();
        // premiers bloc
        blc0[0][0] = 2;
        blc0[0][1] = 2;
        blc0[0][2] = 2;
        blc0[1][1] = 2;
        level1.add_Bloc(100, 100, 3, 2, blc0, 2);
        initzeros(blc0);
        // 2 bloc
        blc0[1][0] = 3;
        blc0[2][0] = 3;
        blc0[3][0] = 3;
        blc0[2][1] = 3;
        blc0[2][1] = 3;
        level1.add_Bloc(1000, 200, 2, 3, blc0, 3);
        initzeros(blc0);
        // 3 bloc
        blc0[0][0] = 4;
        blc0[0][1] = 4;
        blc0[0][2] = 4;
        blc0[0][3] = 4;
        level1.add_Bloc(300, 100, 1, 4, blc0, 4);
        initzeros(blc0);
        // 4 bloc
        blc0[0][0] = 5;
        blc0[0][1] = 5;
        blc0[0][2] = 5;
        blc0[1][2] = 5;
        level1.add_Bloc(800, 300, 3, 2, blc0, 5);
        initzeros(blc0);
        // 5 bloc
        blc0[0][0] = 6;
        blc0[0][1] = 6;
        blc0[1][0] = 6;
        blc0[1][1] = 6;
        level1.add_Bloc(300, 1000, 2, 2, blc0, 6);
        initzeros(blc0);
        // 6 bloc
        blc0[0][0] = 7;
        blc0[0][1] = 7;
        blc0[1][1] = 7;
        blc0[2][1] = 7;
        level1.add_Bloc(400, 100, 2, 3, blc0, 7);
        initzeros(blc0);
    }

    public void initzeros(int[][] blocs) {

        for (int i = 0; i < currentLevel.carteWidth; i++) {
            for (int j = 0; j < currentLevel.carteHeight; j++) {
                blocs[i][j] = 0;
            }

        }
    }

    public void initialisation( /* int line, int colonne */) {
        int i, j;
        for (i = 0; i < currentLevel.carteWidth; i++) {
            for (j = 0; j < currentLevel.carteHeight; j++) {
                currentLevel.Tab[i][j] = 1;
            }

        }
    }

    /**
     * Dessin du bloque ou on doit placer les platefomes
     **/
    private void paintCarte(Canvas canvas) {
        int i, j;
        for (i = 0; i < currentLevel.carteWidth; i++)
            for (j = 0; j < currentLevel.carteHeight; j++) {

                switch (currentLevel.Tab[i][j]) {
                    case 1:
                        canvas.drawBitmap(casseVide, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(casseTurquoise, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 4:
                        canvas.drawBitmap(casseRouge, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 5:
                        canvas.drawBitmap(casseBleu, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 6:
                        canvas.drawBitmap(casseBlack, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 7:
                        canvas.drawBitmap(casseVert, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                }
                break;


            }
    }

    /**
     * Dessin de la victor
     **/
    private void paintWin(Canvas canvas) {

        canvas.drawBitmap(win, 200, 200, null);
    }

    /**
     * Dessin des briques  en mode statique non par un tableau aléatoires
     **/
    private void paintBloc(Canvas canvas) {

        for (Bloc bloc : currentLevel.list_bloc) {
            for (int i = 0; i < currentLevel.carteHeight; i++) {
                for (int j = 0; j < currentLevel.carteWidth; j++) {
                    switch (bloc.getForme()[i][j]) {
                        case 1:
                            canvas.drawBitmap(casseVide, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 2:
                            canvas.drawBitmap(casseTurquoise, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 3:
                            canvas.drawBitmap(casseJaune, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 4:
                            canvas.drawBitmap(casseRouge, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 5:
                            canvas.drawBitmap(casseBleu, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 6:
                            canvas.drawBitmap(casseBlack, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;
                        case 7:
                            canvas.drawBitmap(casseVert, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                            break;

                    }
                }
            }
        }

    }


    private void nDraw(Canvas canvas) {

        canvas.drawBitmap(Fondecran, 0, 0, null);

        if (YouWin()) {
            paintCarte(canvas);
            paintBloc(canvas);
            paintWin(canvas);
        } else {
            paintCarte(canvas);
            paintBloc(canvas);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters();
        afficher();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    @Override
    public void run() {
        Canvas c = null;
        while (go) {
            try {
                cv_thread.sleep(40);
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }

        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        int positionX = (int) event.getX();
        int positionY = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                for (Bloc b : currentLevel.list_bloc) {

                    if (positionX >= b.getPosX() && positionX < b.getPosX() + 2 * currentLevel.carteTileSize
                            && b.getPosY() <= positionY && positionY < b.getPosY() + 2 * currentLevel.carteTileSize)
                        b.setEst_touche(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                for (Bloc b : currentLevel.list_bloc) {
                    if (b.isEst_touche()) {
                        restart(currentLevel.list_bloc.indexOf(b) + 2);
                        b.setPosX(positionX);
                        b.setPosY(positionY);

                    }
                }

                for (Bloc b : currentLevel.list_bloc) {
                    if (InBox(b.getPosX(), b.getPosY())) {
                        b.setPosX(carteCentreGauche + GetValue(finalPostion_x) * currentLevel.carteTileSize);
                        b.setPosY(carteCentreHaut + GetValue(finalPosition_y) * currentLevel.carteTileSize);
                        updateTable(b, GetValue(finalPosition_y), GetValue(finalPostion_x), b.getId());
                        b.setEst_fixe(true);
                    } else {
                        b.setEst_fixe(false);
                    }
                }
                break;


            case MotionEvent.ACTION_UP:

                for (Bloc b : currentLevel.list_bloc) {
                    b.setEst_touche(false);
                }
                comptageVide();
                YouWin();
                afficher();
                break;
        }

        invalidate();
        return true;
    }

    /**
     * fonction qui vérifie si tu es dedans je dois la refaire il vérifie pour un point de la pièce.
     **/
    public boolean InBox(int x, int y) {
        int i, j;

        for (i = 0; i < currentLevel.carteHeight; i++) {
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (x > carteCentreGauche - currentLevel.carteTileSize
                        && x <= carteCentreGauche + i * currentLevel.carteTileSize && y > carteCentreHaut - currentLevel.carteTileSize
                        && y <= carteCentreHaut + j * currentLevel.carteTileSize) {

                    SetValue(i, j);


                    return true;
                } else continue;
            }

        }
        Log.d("Inbox :", "Hors limite");
        return false;
    }

    /**
     * fonction qui change la pièce si il est en dedans.
     **/
    public void updateTable(Bloc temp, int x, int y, int id) {
        int i, j;
        if (x >= 0 && x <= currentLevel.carteHeight - temp.getTailleX() && y >= 0 && y <= currentLevel.carteWidth - temp.getTailleY()) {
            for (i = x; i < temp.getTailleX() + x; i++) {
                for (j = y; j < temp.getTailleY() + y; j++) {
                    if (temp.getForme()[i - x][j - y] == id && currentLevel.Tab[i][j] == 1) {
                        currentLevel.Tab[i][j] = temp.getForme()[i - x][j - y];

                    }
                }
            }
        } else {
            raseTable(temp, x, y, id);
        }


    }

    /**
     * Inutile
     **/
    public void raseTable(int temp[][], int x, int y, int tailleX, int tailleY, int id) {
        int i, j;
        if (x >= 0 && x <= currentLevel.carteHeight - tailleX && y >= 0 && y <= currentLevel.carteWidth - tailleY) {
            for (i = x; i < tailleX + x; i++) {
                for (j = y; j < tailleY + y; j++) {
                    if (temp[i - x][j - y] == id)
                        currentLevel.Tab[i][j] = 1;
                    // Log.d("Rase :","good" + id);
                }
            }
        }

    }

    public void raseTable(Bloc temp, int x, int y, int id) {
        int i, j;
        if (x >= 0 && x <= currentLevel.carteHeight - temp.getTailleX() && y >= 0 && y <= currentLevel.carteWidth - temp.getTailleY()) {
            for (i = x; i < temp.getTailleX() + x; i++) {
                for (j = y; j < temp.getTailleY() + y; j++) {
                    if (temp.getForme()[i - x][j - y] == id)
                        currentLevel.Tab[i][j] = 1;
                }
            }
        }

    }

    /**
     * fonction qui éfface si la pièce n'est pas dans le complétement dans le bloc.
     **/
    public void restart(int id) {
        int i, j;
        for (i = 0; i < currentLevel.carteHeight; i++)
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (currentLevel.Tab[i][j] == id)
                    currentLevel.Tab[i][j] = 1;
            }
    }

    /**
     * Tu devine.
     **/
    public boolean YouWin() {
        for (Bloc b : currentLevel.list_bloc) {
            if (!b.isEst_fixe())
                return false;
        }
        if (nbPiece != 0) {
            return false;
        }

        return true;

    }

    /**
     * Savoir si il y a des 1.
     **/
    public int comptageVide() {
        int i, j, cmt = 0;

        for (i = 0; i < currentLevel.carteHeight; i++) {
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (currentLevel.Tab[i][j] == 1) {
                    cmt++;
                }
            }
        }
        return nbPiece = cmt;
    }

    /**
     * Outil neccessaire.
     **/
    public void SetValue(int x, int y) {
        this.finalPostion_x = x;
        this.finalPosition_y = y;
    }

    public int GetValue(int x) {
        return x;
    }

    public void afficher() {
        int i, j;

        for (i = 0; i < currentLevel.carteWidth; i++) {
            for (j = 0; j < currentLevel.carteHeight; j++)
                System.out.print(currentLevel.Tab[i][j]);
            System.out.println("");
        }
    }
}
