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

    Bitmap  casseVide,casseRouge,casseJaune,
            casseTurquoise,Fondecran,casseBleu,
            casseBlack,win;
    Thread  cv_thread;
    private Resources Res;
    private Context 	Context;

    // taille de la carte
    static final int    carteWidth    = 5;
    static final int    carteHeight   = 5;
    static final int    carteTileSize = 105;
    int carteCentreGauche,carteCentreHaut;


    int Score = 0,nbPiece=0;
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
    int [][] petitL = new int[carteHeight][carteWidth];  /* bloc rouge */
    int [][] grandL = new int[carteHeight][carteWidth];  /* bloc bleu */
    int [][] piece = new int[carteHeight][carteWidth];  /* bloc black */
    int Ifixe,Jfixe; // global

    /** Position   **/
    int PosX=100,PosY=100; // position turquoise
    int Pos1X=1000, Pos1Y= 200; // position jaune
    int Pos2X=300, Pos2Y= 100;  // position rouge
    int Pos3X=800, Pos3Y= 300;  // position bleu
    int Pos4X=300, Pos4Y= 1000;  // position black

    /** booléen de Drap and Drop    **/
    boolean touch = false,  /* bloc turquoise */
            touch1 = false, /* bloc jaune */
            touch2 = false,  /* bloc rouge */
            touch3 = false,  /* bloc bleu */
            touch4 = false;  /* bloc black */

    /** booléen de fixation    **/
    boolean fixer1=false,
            fixer2=false,
            fixer3=false,
            fixer4=false,
            fixer=false;

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
        win = BitmapFactory.decodeResource(Res, R.drawable.win);

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
                        canvas.drawBitmap(casseTurquoise,carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune,carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 4:
                        canvas.drawBitmap(casseRouge,carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 5:
                        canvas.drawBitmap(casseBleu,carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 6:
                        canvas.drawBitmap(casseBlack,carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                }
            }
    }
    private void paintWin(Canvas canvas){

        canvas.drawBitmap(win,200,200,null);
    }


    private void paintForme(Canvas canvas) {
        int i,j,taille =0;
        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (forme[i][j]) {
                    case 2:
                        canvas.drawBitmap(casseTurquoise, PosX + j * carteTileSize, PosY + i * carteTileSize, null);
                        break;

                }


            }
        }


        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (Plateforme[i][j]) {

                    case 3:
                        canvas.drawBitmap(casseJaune, Pos1X + j * carteTileSize, Pos1Y + i * carteTileSize, null);
                        break;
                }
            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (petitL[i][j]) {
                    case 4:
                        canvas.drawBitmap(casseRouge, Pos2X + j * carteTileSize, Pos2Y + i * carteTileSize, null);
                        break;

                }
            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (grandL[i][j]) {
                    case 5:
                        canvas.drawBitmap(casseBleu, Pos3X + j * carteTileSize, Pos3Y + i * carteTileSize, null);
                        break;

                }
            }
        }

        for (i=0; i < carteWidth; i++ ) {
            for (j = 0; j < carteHeight; j++) {
                switch (piece[i][j]) {
                    case 6:
                        canvas.drawBitmap(casseBlack, Pos4X + j * carteTileSize, Pos4Y + i * carteTileSize, null);
                        break;

                }
            }
        }
    }


    private void nDraw(Canvas canvas) {

        canvas.drawBitmap(Fondecran, 0, 0, null);

        if(YouWin()){
            paintCarte(canvas);
            paintForme(canvas);
            paintWin(canvas);
        }else {
            paintCarte(canvas);
            paintForme(canvas);
        }
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




        nombreAléatoire = 0;

        forme[nombreAléatoire + 1][nombreAléatoire] = 2;
        forme[nombreAléatoire + 1][nombreAléatoire + 1] = 2;
        forme[nombreAléatoire][nombreAléatoire + 1] = 2;
        forme[nombreAléatoire][nombreAléatoire] = 2;


        Plateforme[nombreAléatoire][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 1][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 2][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire + 3][nombreAléatoire] = 3;
        Plateforme[nombreAléatoire][nombreAléatoire + 1] = 3;
        Plateforme[nombreAléatoire][nombreAléatoire +2] = 3;
        Plateforme [nombreAléatoire][nombreAléatoire +3] = 3;

        petitL[0][0] = 4;
        petitL[0][1] = 4;
        petitL[0][2] = 4;

        grandL[0][0] = 5;
        grandL[0][1] = 5;
        grandL[0][2] = 5;
        grandL[0][3] = 5;
        grandL[0][4] = 5;

        piece[1][0] = 6;
        piece[2][0] = 6;
        piece[0][1] = 6;
        piece[1][1] = 6;
        piece[2][1] = 6;
        piece[3][1] = 6;
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

                    if (positionX >= PosX && positionX < PosX + 2 * carteTileSize && PosY <= positionY && positionY < PosY + 2 * carteTileSize)
                        touch = true;


                for(i=0;i<5;i++) {
                    for (j = 0; j < 5; j++) {
                        if (positionX >= Pos1X && positionX < Pos1X + i * carteTileSize && Pos1Y <= positionY && positionY < Pos1Y + j * carteTileSize)
                                touch1 = true;
                    }
                }


                for(i=0;i < 4;i++) {
                    for (j = 0; j < 4; j++) {
                            if (positionX >= Pos2X && positionX < Pos2X + i * carteTileSize && Pos2Y <= positionY && positionY < Pos2Y + j * carteTileSize)
                                touch2 = true;
                    }
                }

                for(i=0;i < 5;i++)
                    for (j = 0; j < 5; j++) {

                            if (positionX >= Pos3X && positionX < Pos3X + i * carteTileSize && Pos3Y <= positionY && positionY < Pos3Y + j * carteTileSize)
                                touch3 = true;

                    }


                for(i=0;i<5;i++) {
                    for (j = 0; j < 5; j++) {
                        if(Pos4X !=2 && Pos4X !=3) {
                            if (positionX >= Pos4X && positionX < Pos4X + i * carteTileSize && Pos4Y <= positionY && positionY < Pos4Y + j * carteTileSize)
                                touch4 = true;
                        }
                    }
                }

                break;


            case MotionEvent.ACTION_MOVE:

                /* condition drap piece*/


                if(touch){
                   restart(2);
                    PosX  = positionX;
                    PosY = positionY;
                }

                if (touch1){
                    //raseTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                    restart(3);
                    Pos1X = positionX;
                    Pos1Y = positionY;
                }

                if (touch2){
                    restart(4);
                   //raseTable(petitL,GetValue(Jfixe),GetValue(Ifixe),1,3,4);
                    Pos2X = positionX;
                    Pos2Y = positionY;
                }

                if (touch3){
                    restart(5);
                    //raseTable(grandL,GetValue(Jfixe),GetValue(Ifixe),1,5,5);
                    Pos3X = positionX;
                    Pos3Y = positionY;
                }

                if (touch4){
                    restart(6);
                   // raseTable(piece,GetValue(Jfixe),GetValue(Ifixe),2,4,6);
                    Pos4X = positionX;
                    Pos4Y = positionY;
                }

                /* condition pour le placement automatique*/
                if (InBox(PosX, PosY)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau*/

                    Log.d("Piece Turquoise", "in box" + nbPiece);

                    PosX=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                    PosY= carteCentreHaut + GetValue(Jfixe) * carteTileSize;
                   // raseTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2); // efface
                    updateTable(forme,GetValue(Jfixe),GetValue(Ifixe),2,2,2);
                    fixer1 = true;

                }else fixer1 = false;



               if (InBox(Pos1X, Pos1Y)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau */

                   Log.d("Piece jaune", "in box");


                   Pos1X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                   Pos1Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;
                   //raseTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                   updateTable(Plateforme,GetValue(Jfixe),GetValue(Ifixe),4,4,3);
                    fixer2 =true;
                }else fixer2 = false;

                if (InBox(Pos2X, Pos2Y)){
                /**  force à poser sur la case 1-1
                 * Il l faut trouver le i et le j du grand tableau*/

                Pos2X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                Pos2Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                Log.d("Piece rouge", "in box");
                    updateTable(petitL, GetValue(Jfixe), GetValue(Ifixe), 1, 3, 4);
                    fixer3 =true;
                }else fixer3 = false;

                if (InBox(Pos3X, Pos3Y)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau*/

                    Pos3X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                    Pos3Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;
                    //Log.d("I3", " " + +GetValue(Ifixe));
                    //Log.d("J3", " " + +GetValue(Ifixe));
                    Log.d("Piece bleu", "in box");
                    updateTable(grandL, GetValue(Jfixe), GetValue(Ifixe), 1, 5, 5);
                    fixer4 =true;
                }else fixer4 = false;

                if (InBox(Pos4X, Pos4Y)) {
                    /**  force à poser sur la case 1-1
                     * Il l faut trouver le i et le j du grand tableau*/

                    Pos4X=carteCentreGauche +GetValue(Ifixe) * carteTileSize;
                    Pos4Y= carteCentreHaut + GetValue(Jfixe) * carteTileSize;

                    Log.d("Piece black", "in box");
                    updateTable(piece, GetValue(Jfixe), GetValue(Ifixe), 4, 2, 6);
                    fixer =true;
                }else fixer = false;

                break;

            case MotionEvent.ACTION_UP:
                touch = false;
                touch1 = false;
                touch2 = false;
                touch3 = false;
                touch4 = false;
                YouWin();
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



                    SetValue(i,j);

                    Log.d("piece",""+nbPiece);
                    return true;
                }
               else continue;
            }

        }

        return false;
    }


    public void updateTable(int temp[][],int x,int y,int tailleX,int tailleY,int id){
        int i,j;
       // Log.d("C :","_"+x+"-"+y);
         if(x >= 0 && x <= carteHeight-tailleX && y >= 0 && y <= carteWidth-tailleY) {
             for (i = x; i < tailleX+x; i++) {
                 for (j = y; j < tailleY+y; j++) {
                    if(temp[i-x][j-y] == id) {
                        Tab[i][j] = temp[i - x][j - y];
                       // Log.d("Update :", "good" + id);
                    }//else
                        //Log.d("Update :","Dans le vent");
                 }
             }
         }else{
             //Log.d("Update :","Hors limite");
             raseTable(temp, x, y, tailleX, tailleY, id);
         }
    }

    public void raseTable(int temp[][],int x,int y,int tailleX,int tailleY,int id){
        int i,j;
        Log.d("C :","_"+x+"-"+y);
        if(x >= 0 && x <= carteHeight-tailleX && y >= 0 && y <= carteWidth-tailleY) {
            for (i = x; i < tailleX+x; i++) {
                for (j = y; j < tailleY+y; j++) {
                    if(temp[i-x][j-y] == id)
                        Tab[i][j]=1;
                   // Log.d("Rase :","good" + id);
                }
            }
        }//else
           // Log.d("Rase :", "Hors limite");



    }

    public int GetValue(int x) {
        return x;
    }

    public void restart(int id){
        int i,j;
        for (i=0; i < carteHeight; i++)
            for (j=0; j < carteWidth; j++) {
                if(Tab[i][j]==id)
                Tab[i][j] = 1;
            }
    }

    public boolean YouWin(){

        if(fixer && fixer2 && fixer3 && fixer4 && fixer1) {

            Log.d("Yes","You Win");
            return true;
        }
        else
        return false;
    }

    public void SetValue(int x,int y){
        this.Ifixe = x;
        this.Jfixe = y;
    }
}
