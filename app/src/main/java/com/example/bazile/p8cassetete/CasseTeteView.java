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

    int [][] Tab;
    int [][] forme;

    /** Une plateforme a besion des données suivant
     *  Un tableau ou on connait sa taille en x et en y
     *  Position x,y
     *  Booléen qui verifie qu'il est toucher
     *  Booléen qui vérifie si il est placer
     *
     */

    int [][] Plateforme = new int[carteHeight][carteWidth];


    int PosX=10,PosY=10;
    int Pos1X=20+carteTileSize, Pos1Y= 200+carteTileSize;


    boolean touch = false,touch1 = false;
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
                        canvas.drawBitmap(casseTurquoise, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
                        break;
                    case 4 :
                        canvas.drawBitmap(casseRouge, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
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
        int i = 0, j = 0;
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

    public boolean onTouchEvent(MotionEvent event) {
        int i,j;
        int positionX = (int) event.getX();
        int positionY = (int) event.getY();
        int calx = carteCentreHaut;
        int caly = carteCentreGauche;
        int calY = Pos1Y+carteTileSize;
        int calX = Pos1X+carteTileSize;


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

                Log.d("So","Mince");
                if (InTheBox(Pos1X, Pos1Y)) {
                    Log.d("So","good");

                    fixer = true;

                }

                if (InTheBox(PosX, PosY)) {
                    Log.d("So","good");

                    fixer = true;

                }

                break;


            case MotionEvent.ACTION_MOVE:

                if(touch){
                    PosX  = positionX;
                    PosY = positionY;
                }
                if (touch1){
                    Pos1X = positionX;
                    Pos1Y = positionY;
                }


                Log.d("So","Mince");
                if (InTheBox(Pos1X, Pos1Y)) {
                    Log.d("So","good");

                    fixer = true;

                }

                if (InTheBox(PosX, PosY)) {
                    Log.d("So","good");

                    fixer = true;

                }
                break;

            case MotionEvent.ACTION_UP:
                touch = false;
                touch1 = false;
                //Log.d("posB", "bool:" + touch);
                break;
        }

        invalidate();
        return true;
    }

    public boolean InBox(int x ,int y){
        int tempX = x,tempY =y;
        if(x > carteCentreGauche
                && x <  carteCentreGauche+ (carteWidth-1)*carteTileSize
                && y >  carteCentreHaut
                && y <  carteCentreHaut+ (carteWidth-1)*carteTileSize ) {




            return true;
        }
        return false;
    }

    public boolean InTheBox(int x, int y){
        int i,j;

        for(i=0;i<carteWidth;i++)
            for(j=0;j<carteHeight;i++){
                if(x ==  carteCentreGauche+ j*carteTileSize ) {
                    x = carteCentreGauche + j* carteTileSize;


                    return true;
                }
                if( y  ==  carteCentreHaut + i *carteTileSize) {
                    y = carteCentreHaut + i * carteTileSize;
                    return true;
                }


                else return false;

            }

        return false;
    }


}
