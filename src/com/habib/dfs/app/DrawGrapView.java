package com.habib.dfs.app;


import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class DrawGrapView extends SurfaceView implements Callback {

	SurfaceHolder holder;
	private Paint paint = new Paint();
	private Canvas canvas;
	private Boolean already_drawn = false;
	private Paint textpaint = new Paint();
	private int screenWidth = 0,screenHeight = 0, u, v,helper, INF = Integer.MAX_VALUE;
	private int startx, starty, endx, endy;//for edge draw
	private int left = 640, top = 20, right = 760, bottom = 100; //rectangle parameter
	private ArrayList<Point> vertexPoint = new ArrayList<Point>();
	private ArrayList<Point> edgeU = new ArrayList<Point>();
	private ArrayList<Point> edgeV = new ArrayList<Point>();
	private ArrayList<ArrayList<Integer>> grap;
	private Boolean root = false;
	private MainActivity mainActivity;
	
	public DrawGrapView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity;
		holder = getHolder();
		holder.addCallback(this);
		textpaint.setColor(Color.GREEN);
		textpaint.setTextSize(45.0f);
		paint.setColor(Color.RED);
		grap = new ArrayList<ArrayList<Integer>>();
	}
	
	private boolean boundaryCheck(int cx,int cy){
		//Log.d("my tag", cx +"  " + cy);
		int lowerlimit_x = 0;
		int upperlimit_x = 0;
		int lowerlimit_y = 0;
		int upperlimit_y = 0;
		for(int i = 0; i < vertexPoint.size(); i++){ 
			lowerlimit_x = vertexPoint.get(i).x - 40;
			lowerlimit_y = vertexPoint.get(i).y - 40;
			if (lowerlimit_x < 0) {
				lowerlimit_x = 0;
			}
			if (lowerlimit_y < 0) {
				lowerlimit_y = 0;
			}
			upperlimit_x = vertexPoint.get(i).x + 40;
			upperlimit_y = vertexPoint.get(i).y + 40;
			if (upperlimit_x > screenWidth) {
				upperlimit_x = screenWidth;
			}
			if (upperlimit_y > screenHeight) {
				upperlimit_y = screenHeight;
			}
			if (cx >= lowerlimit_x && cx <= upperlimit_x && cy >= lowerlimit_y && cy <= upperlimit_y) {
				// vertex i contains the point
				// draw edge
				already_drawn = true;
				helper = i;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {	
			startx = (int)event.getX();
			starty = (int)event.getY();
			
			if (startx >= left && startx <= right && starty >= top && starty <= bottom) {
				Toast.makeText(mainActivity.context, "Touch a vertex for "
						+ "source", Toast.LENGTH_SHORT).show();
				root = true;
				return true;
			}
			//0, top, 130, bottom
			if (startx >= 0 && startx <= 130 && starty >= top && starty <= bottom) {
				// reset graph....
				resetGrap();

				draw();
				return true;
			}
			//Log.d("my tag", "not return");
			if (boundaryCheck(startx,starty) == false) {
				vertexPoint.add(new Point(startx,starty));
				grap.add(new ArrayList<Integer>());
				draw();// draw new node to canvas
				already_drawn = false;
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			endx = (int)event.getX();
			endy = (int)event.getY();
			
			if (already_drawn) {
				u = helper;
				if (boundaryCheck(startx,starty) == true && boundaryCheck(endx,endy) == true) {
					v = helper;
					if (root && u == v) {
						constructGrap(helper);
						root = false;
						return true;
					}
					edgeU.add(new Point(vertexPoint.get(u).x,vertexPoint.get(u).y));
					edgeV.add(new Point(vertexPoint.get(v).x,vertexPoint.get(v).y));
					grap.get(u).add(v);
					grap.get(v).add(u);//undirected graph
					draw();// draw an edge to canvas
					u = v = INF;
				}
			}
		}
		return true;
	}	
	private void constructGrap(int src) {
		MainActivity.gSize = grap.size();
		MainActivity.grap = grap;
		mainActivity.init(src);
	}

	private void draw(){
		canvas = holder.lockCanvas();
		canvas.drawColor(Color.BLACK);//if this is not set reset button doesn't work :(
		
		Rect r = new Rect(left, top, right, bottom);
		canvas.drawRect(r, paint);
		canvas.drawText("DFS", left + 25, 70, textpaint);
		
		r.set(0, top, 150, bottom);
		canvas.drawRect(r, paint);
		canvas.drawText("restart", 2, 70, textpaint);
		
		for(int i = 0; i < edgeU.size(); i++){
			Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			linePaint.setAntiAlias(true);
			linePaint.setStrokeWidth(6.0f);
			linePaint.setColor(Color.RED);
			canvas.drawLine(edgeU.get(i).x,edgeU.get(i).y,edgeV.get(i).x,edgeV.get(i).y, linePaint);
		}
		for(int i = 0; i < vertexPoint.size(); i++){
			int x = vertexPoint.get(i).x;
			int y = vertexPoint.get(i).y;
			
			canvas.drawCircle(x, y, 40, paint);
			canvas.drawText(""+ i, x - 15, y + 15,textpaint);
			//r.set();
		}
		holder.unlockCanvasAndPost(canvas);
	}
	private void resetGrap(){
		for(int i = 0; i < grap.size(); i++){
			grap.get(i).clear();
		}
		grap.clear();
		edgeU.clear();
		edgeV.clear();
		vertexPoint.clear();
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
			screenWidth = getWidth();
			screenHeight = getHeight();
			left = screenWidth - 130;
			right = screenWidth;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		//this.holder = holder;
		draw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//this.holder = holder;
	}

}
