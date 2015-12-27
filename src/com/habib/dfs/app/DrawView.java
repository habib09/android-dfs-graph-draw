/*This class is used to draw the custom view..By extending SarfaceView class
 * and implementing Callback.... 
 */
package com.habib.dfs.app;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class DrawView extends SurfaceView implements Callback{
	
	int height_grap;
	int[] par;

	private SurfaceHolder holder;
	Context context;
	
	public DrawView(Context context) {
		super(context);
		this.context = context;
		holder = getHolder();
		holder.addCallback(this);
	}
	
	
	public void set(int height_grap, int[] par) {
		this.height_grap = height_grap;
		this.par = par;
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		DrawGrap drawGrap = new DrawGrap(holder,getHeight(),getWidth());
		drawGrap.setdata(par, height_grap);
		
		drawGrap.draw();
		drawGrap.stop();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}
