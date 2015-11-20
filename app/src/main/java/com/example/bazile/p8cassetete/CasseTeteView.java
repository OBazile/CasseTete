package com.example.bazile.p8cassetete;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;

public class CasseTeteView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    Bitmap  casseVide,casseRouge,casseJaune,casseTurquoise,Fondecran,casseBleu,casseBlack;
    Thread  cv_thread;
    private Resources Res;
    private Context 	Context;

    // taille de la carte
    static final int    carteWidth    = 8;
    static final int    carteHeight   = 8;
    static final int    carteTileSize = 105;
    int carteCentreGauche,carteCentreHaut;


    int Score = 0;
    Random r = new Random();

    int [][] Tab;
    int [][] forme;
    int nombreAléatoire;



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
        for (i=0; i < carteWidth; i++ )
            for(j=0; j < carteHeight; j++){
                switch (forme[i][j]){

                   /* case 1:
                        canvas.drawBitmap(casseBleu, 0, 0, null);
                        break;*/
                    case 2:
                        canvas.drawBitmap(casseTurquoise,carteCentreGauche+j*carteTileSize,carteCentreHaut+i*carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune,carteCentreGauche+j*carteTileSize,carteCentreHaut + i*carteTileSize, null);
                        break;
                    case 4:
                        canvas.drawBitmap(casseRouge, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
                        break;
                    /*default:
                        canvas.drawBitmap(casseBlack, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
                        break;*/
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
                forme[i][j] = 1;
            }
        }
    }

    public void CreePlateforme(){
        int i,j;
        int compteur = 2;
        boolean gg = true;
        nombreAléatoire = r.nextInt(carteWidth-1);

        forme[nombreAléatoire][nombreAléatoire] = 2;

        while(compteur <= 4 ) {


            for (i = 0; i < carteWidth; i++) {
                for (j = 0; j < carteHeight; j++) {

                    if (forme[nombreAléatoire][nombreAléatoire] == 4) {
                        if (forme[nombreAléatoire][nombreAléatoire + 1] == 1 &&
                                forme[nombreAléatoire][nombreAléatoire + 2] == 1 &&
                                forme[nombreAléatoire][nombreAléatoire + 3] == 1) {

                            forme[nombreAléatoire][nombreAléatoire + 1] = 4;
                            forme[nombreAléatoire][nombreAléatoire + 2] = 4;
                            forme[nombreAléatoire][nombreAléatoire + 3] = 4;

                        }else{
                            forme[nombreAléatoire][nombreAléatoire] = compteur;
                        }

                    }


                    if (forme[nombreAléatoire][nombreAléatoire] == 3) {
                        if (forme[nombreAléatoire + 1 ][nombreAléatoire] == 1 &&
                                forme[nombreAléatoire + 2 ][nombreAléatoire] == 1 &&
                                        forme[nombreAléatoire + 3 ][nombreAléatoire] == 1) {

                            forme[nombreAléatoire + 1 ][nombreAléatoire] = 3;
                            forme[nombreAléatoire + 2 ][nombreAléatoire] = 3;

                        }else{
                            forme[nombreAléatoire][nombreAléatoire] = compteur;
                        }

                    }

                    if (forme[nombreAléatoire][nombreAléatoire] == 2) {
                        if (forme[nombreAléatoire + 1][nombreAléatoire] == 1 &&
                                forme[nombreAléatoire + 1][nombreAléatoire + 1] == 1 &&
                                forme[nombreAléatoire][nombreAléatoire + 1] == 1) {

                            forme[nombreAléatoire + 1][nombreAléatoire] = 2;
                            forme[nombreAléatoire + 1][nombreAléatoire + 1] = 2;
                            forme[nombreAléatoire][nombreAléatoire + 1] = 2;

                        }

                    }
                }
            }
            compteur++;
            nombreAléatoire = r.nextInt(carteWidth - 1);
            while(forme[nombreAléatoire][nombreAléatoire] != 1) nombreAléatoire = r.nextInt(carteWidth-1);
            forme[nombreAléatoire][nombreAléatoire] = compteur;
        }
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
}
