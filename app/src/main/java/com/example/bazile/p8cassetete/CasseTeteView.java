package com.example.bazile.p8cassetete;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;

public class CasseTeteView extends SurfaceView implements SurfaceHolder.Callback, Runnable{


    private Timer Temps;
    Bitmap  casseVide,caseBlue,casseRouge,casseJaune,casseTurquoise,Fondecran;
    Thread  cv_thread;
    private Resources Res;
    private Context 	Context;

    // taille de la carte
    static final int    carteWidth    = 10;
    static final int    carteHeight   = 10;
    static final int    carteTileSize = 105;
    int carteCentreGauche,carteCentreHaut;


    Float Time;
    int Score = 0;

    int [][] Tab;
    Paint  paint;
    private boolean go = true;
    private  SurfaceHolder holder;



    public CasseTeteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        Context	= context;
        Res 		= Context.getResources();
        Fondecran = BitmapFactory.decodeResource(Res, R.drawable.fond_ecran);
        caseBlue = BitmapFactory.decodeResource(Res, R.drawable.cube);
        casseTurquoise = BitmapFactory.decodeResource(Res,R.drawable.cube1);
        casseRouge = BitmapFactory.decodeResource(Res, R.drawable.cube2);
        casseJaune = BitmapFactory.decodeResource(Res, R.drawable.cube3);
        casseVide = BitmapFactory.decodeResource(Res,R.drawable.cube4);
        initparameters();
        cv_thread = new Thread(this);
    }


    private void paintCarte(Canvas canvas) {
        int i,j;
        for (i=0; i < carteWidth; i++ )
            for(j=0; j < carteHeight; j++){
                switch (Tab[i][j]){
                    case 1:
                        canvas.drawBitmap(casseRouge, carteCentreGauche+ j*carteTileSize, carteCentreHaut+ i*carteTileSize, null);
                        break;
                    default:
                        canvas.drawBitmap(casseRouge, carteCentreGauche+ j*carteTileSize,carteCentreHaut+ i*carteTileSize, null);
                        break;
                }
            }
    }

    private void nDraw(Canvas canvas) {
        canvas.drawBitmap(Fondecran,getHeight(),getWidth(),null);
        paintCarte(canvas);

    }

    public void initialisation ( /*int minLine,, int maxCol/*int minCol*/){
        int i,j;
        for (i = 0; i < carteWidth;i++ )
            for (j = 0; j < carteHeight; j++)
                Tab[i][j] = 1;
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
        initialisation();

        carteCentreHaut = (getHeight() - carteHeight * carteTileSize) / 4;
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
