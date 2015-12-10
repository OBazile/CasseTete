package com.example.bazile.p8cassetete;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

public class CasseTeteView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    Bitmap  casseVide,casseRouge,casseJaune,casseTurquoise,Fondecran,casseBleu,casseBlack;
    Thread  cv_thread;
    private Resources Res;
    private Context 	Context;

    // taille de la carte
    static final int    carteWidth    = 5;
    static final int    carteHeight   = 5;
    static final int    carteTileSize = 105;
    int carteCentreGauche,carteCentreHaut;


    int Score = 0;
    Random r = new Random();


    /** Une plateforme a besion des données suivant
     *  Un tableau ou on connait sa taille en x et en y
     *  Position x,y
     *  Booléen qui verifie qu'il est toucher
     *  Booléen qui vérifie si il est placer
     *
     */

/** Tableau    **/
    int [][] Tab; /* Bloc principale */
    int [][] forme;  /* bloc turquoise */
    int [][] Plateforme = new int[carteHeight][carteWidth];  /* bloc jaune */
    int [][] petitL = new int[carteHeight][carteWidth];  /* bloc bleu */
    int [][] grandL = new int[carteHeight][carteWidth];  /* bloc rouge */
    int Ifixe,Jfixe; // global

    int PosX=100,PosY=100; // position turquoise
    int Pos1X=20+carteTileSize, Pos1Y= 200+carteTileSize; // position jaune
    int Pos2X=300, Pos2Y= 100;  // position bleu
    int Pos3X=800, Pos3Y= 300;  // position rouge


    boolean touch = false,touch1 = false,touch2 = false,touch3 = false;;
    boolean fixer = false;

    int nombreAléatoire;
    int pas;



    Paint  paint;
    private boolean go = true;
    private boolean start = false;
    private  SurfaceHolder holder;



    public CasseTeteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        Context	= context;
        Res 		= Context.getResources();
        Fondecran = BitmapFactory.decodeResource(Res, R.drawable.fond_ecran);
        casseBleu=BitmapFactory.decodeResource(Res,R.drawable.cube);
        casseTurquoise = BitmapFactory.decodeResource(Res,R.drawable.cube1);
        casseRouge = BitmapFactory.decodeResource(Res, R.drawable.cube2);
        casseJaune = BitmapFactory.decodeResource(Res, R.drawable.cube3);
        casseVide = BitmapFactory.decodeResource(Res,R.drawable.cube4);
        casseBlack = BitmapFactory.decodeResource(Res, R.drawable.cube5);

        initparameters();
        cv_thread = new Thread(this);

        setFocusable(true);
    }


    private void paintCarte(Canvas canvas) {
        int i,j;
        for (i=0; i < carteWidth; i++ )
            for(j=0; j < carteHeight; j++){
                switch (Tab[i][j]){
                    case 1:
                        canvas.drawBitmap(casseVide, carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;

                    case 2:
                        canvas.drawBitmap(casseVide, carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;

                    case 3:
                        canvas.drawBitmap(casseVide, carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                }
            }
    }

    private void paintForme(Canvas canvas) {
        int i,j,taille =0;
        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (forme[i][j]) {

                    case 1:
                        canvas.drawBitmap(casseBleu, PosX + j * carteTileSize, PosY + i * carteTileSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(casseTurquoise, PosX + j * carteTileSize, PosY + i * carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune, carteCentreGauche + j * carteTileSize, carteCentreHaut + i * carteTileSize, null);
                        break;
                    case 4:
                        canvas.drawBitmap(casseRouge, carteCentreGauche + j * carteTileSize, carteCentreHaut + i * carteTileSize, null);
                        break;
                }


            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (grandL[i][j]) {
                    case 4:
                        canvas.drawBitmap(casseBleu, Pos3X + j * carteTileSize, Pos3Y + i * carteTileSize, null);
                        break;

                }
            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (petitL[i][j]) {
                    case 5:
                        canvas.drawBitmap(casseRouge, Pos2X + j * carteTileSize, Pos2Y + i * carteTileSize, null);
                        break;

                }
            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (Plateforme[i][j]) {
                    case 1:
                        canvas.drawBitmap(casseBleu, Pos1X + j * carteTileSize, Pos1Y + i * carteTileSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(casseTurquoise, Pos1X + j * carteTileSize, Pos1Y + i * carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune, Pos1X + j * carteTileSize, Pos1Y + i * carteTileSize, null);
                        break;
                }
            }
        }
    }


    private void nDraw(Canvas canvas) {

        canvas.drawBitmap(Fondecran,0,0,null);

        paintCarte(canvas);
        paintForme(canvas);
    }

    public void initialisation ( /*int minLine,, int maxCol/*int minCol*/){
        int i,j;
        for (i = 0; i < carteWidth;i++ ) {
            for (j = 0; j < carteHeight; j++) {
                Tab[i][j] = 1;

            }
        }
    }

    public void CreePlateforme() {
        int i = 0, j = 0,Case=4;

        int compteur = 0;
        int compt;
        boolean gg = true;
        //nombreAléatoire = r.nextInt(carteWidth - 1);
        compt = 2 + r.nextInt(5 - 2);
        nombreAléatoire = 0;

        forme[nombreAléatoire + 1][nombreAléatoire] = 2;
        forme[nombreAléatoire + 1][nombreAléatoire + 1] = 2;
        forme[nombreAléatoire][nombreAléatoire + 1] = 2;
        forme[nombreAléatoire][nombreAléatoire] = 2;

        grandL[Case][Case] = 4;
        grandL[Case][1] = 4;
        grandL[Case][2] = 4;
        grandL[Case][3] = 4;
        grandL[Case][0] = 4;
        grandL[3][Case] = 4;
        grandL[2][Case] = 4;
        grandL[1][Case] = 4;
        grandL[0][Case] = 4;

        petitL[3][3] = 5;
        petitL[3][1] = 5;
        petitL[3][2] = 5;
        petitL[2][3] = 5;
        petitL[1][3] = 5;

        Plateforme[nombreAléatoire][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 1][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 2][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 3][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire][nombreAléatoire + 1] = 3;
        Plateforme[nombreAléatoire][nombreAléatoire +2] = 3;
        Plateforme [nombreAléatoire][nombreAléatoire +3] = 3;
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
        Tab = new int[carteHeight][carteWidth];
        forme = new int[carteHeight][carteWidth];
        Plateforme = new int[carteHeight][carteWidth];
        initialisation();
        CreePlateforme();
        carteCentreHaut = (getHeight() - carteHeight * carteTileSize) / 2;
        carteCentreGauche = (getWidth() - carteWidth * carteTileSize) / 2;
        PosX = carteCentreGauche + 3 * carteTileSize;
        PosY = carteCentreGauche + 4 * carteTileSize;
        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
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
            }catch(Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }

        }
    }

    public void afficher(){
        int i,j;

        for(i=0;i<carteWidth;i++) {
            for (j = 0; j < carteHeight; j++)
                System.out.print(Tab[i][j]);
            System.out.println("");
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int i,j;
        int positionX = (int) event.getX();
        int positionY = (int) event.getY();

        switch (event.getAction()){

            case   MotionEvent.ACTION_DOWN:
                if(!fixer) {
                    if (positionX >= PosX && positionX < PosX + 2 * carteTileSize && PosY <= positionY && positionY < PosY + 2 * carteTileSize)
                        touch = true;
                }

                for(i=0;i<5;i++) {
                    for (j = 0; j < 5; j++) {
                        if(!fixer) {
                            if (positionX >= Pos1X && positionX < Pos1X + i * carteTileSize && Pos1Y <= positionY && positionY < Pos1Y + j * carteTileSize)
                                touch1 = true;

                        }

                    }
                }


                for(i=3;i>=0;i--) {
                    for (j = 3; j >= 0; j--) {
                        if(!fixer) {
                            if (positionX >= Pos2X && positionX < Pos2X + i * carteTileSize && Pos2Y <= positionY && positionY < Pos2Y + j * carteTileSize)
                                touch2 = true;

                        }

                    }
                }

                for(i=4;i>=0;i--) {
                    for (j = 4; j >= 0; j--) {
                        if(!fixer) {
                            if (positionX >= Pos3X && positionX < Pos3X + i * carteTileSize && Pos3Y <= positionY && positionY < Pos3Y + j * carteTileSize)
                                touch3 = true;
                        }
                    }
                }

                break;


            case MotionEvent.ACTION_MOVE:


                if(touch){
                    raseTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2);
                    PosX  = positionX;
                    PosY = positionY;
                }

                if (touch1){
                    raseTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                    Pos1X = positionX;
                    Pos1Y = positionY;
                }

                if (touch2){
                   // raseTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                    Pos2X = positionX;
                    Pos2Y = positionY;
                }

                if (touch3){
                    //raseTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                    Pos3X = positionX;
                    Pos3Y = positionY;
                }

               if (InBox(Pos1X, Pos1Y)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau */

                   Log.d("Dans", "boite");
                   Log.d("I2", " " + +GetValue(Ifixe));
                   Log.d("J2", " " + +GetValue(Ifixe));
                   restart();
                   Pos1X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                   Pos1Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                   updateTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);

                }

                if (InBox(PosX, PosY)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau*/


                    PosX=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                    PosY= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                    Log.d("Piece Turquoise", "in box");

                    //restart();
                    updateTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2);
                }

                if (InBox(Pos2X, Pos2Y)){
                /**  force à poser sur la case 1-1
                 * Il l faut trouver le i et le j du grand tableau*/


                Pos2X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                Pos2Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                Log.d("I3", " " + +GetValue(Ifixe));
                Log.d("J3", " " + +GetValue(Ifixe));
                Log.d("Piece rouge", "in box");

                //restart();
                //updateTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2);
                }

                if (InBox(Pos3X, Pos3Y)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau*/


                    Pos3X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                    Pos3Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                    Log.d("I3", " " + +GetValue(Ifixe));
                    Log.d("J3", " " + +GetValue(Ifixe));
                    Log.d("Piece rouge", "in box");

                    //restart();
                    //updateTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2);
                }

                break;

            case MotionEvent.ACTION_UP:
                touch = false;
                touch1 = false;
                touch2 = false;
                touch3 = false;

                afficher();
                break;
        }

        invalidate();
        return true;
    }

    public boolean InBox(int x ,int y){
        int i,j;

         for(i=0;i<carteHeight;i++){
                for(j=0;j<carteWidth;j++) {
                if ( x > carteCentreGauche - carteTileSize
                        && x <= carteCentreGauche + i * carteTileSize && y > carteCentreHaut -carteTileSize
                        && y <= carteCentreHaut + j * carteTileSize) {

                    Log.d("I", " " + i);
                    Log.d("J", " " + j);

                    SetValue(i,j);

                    return true;
                }
               else continue;
            }

        }
        return false;
    }

    public boolean InBox2(int x ,int y){
        int i,j;

        for(i=0;i<carteHeight;i++){
            for(j=0;j<carteWidth;j++) {
                if ( x > carteCentreGauche
                        && x <= carteCentreGauche + i * carteTileSize && y > carteCentreHaut
                        && y <= carteCentreHaut + j * carteTileSize) {

                    Log.d("I", " " + i);
                    Log.d("J", " " + j);

                    SetValue(i,j);

                    return true;
                }
                else continue;
            }

        }
        return false;
    }

    public void updateTable(int temp[][],int x,int y,int tailleX,int tailleY,int id){
        int i,j;

         if(x >= 0 && x <= carteHeight-tailleX && y >= 0 && y <= carteWidth-tailleY) {
             for (i = x; i < tailleX+x; i++) {
                 for (j = y; j < tailleY+y; j++) {
                    if(temp[i-x][j-y] == id)
                     Tab[i][j]=temp[i-x][j-y];

                 }
             }
         }else{
             Log.d("L :","Hors limite");

         }
    }

    public void raseTable(int temp[][],int x,int y,int tailleX,int tailleY,int id){
        int i,j;

        if(x >= 0 && x <= carteHeight-tailleX && y >= 0 && y <= carteWidth-tailleY) {
            for (i = x; i < tailleX+x; i++) {
                for (j = y; j < tailleY+y; j++) {
                    if(temp[i-x][j-y] == id)
                        Tab[i][j]=1;

                }
            }
        }else{
            Log.d("L :","Hors limite");

        }

    }

    public int GetValue(int x) {
        return x;
    }

    public void restart(){
        int i,j;
        for (i=0; i < carteHeight; i++)
            for (j=0; j < carteWidth; j++)
                Tab[i][j]=1;

    }

    public void SetValue(int x,int y){
        this.Ifixe = x;
        this.Jfixe = y;
    }
}
