/*This class handles the intent received from InputActivity... 
 * then pass all received resources to DrawGrap class...
 * future plan ----> animating the drawing...
 */
package com.habib.dfs.app;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SubActivity extends Activity {
	
	private int height_grap;
	private int[] par;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		height_grap = getIntent().getExtras().getInt("height_grap");
		par = getIntent().getExtras().getIntArray("par");
		

		DrawView drawView = new DrawView(this);
		drawView.set(height_grap, par);
		
		setContentView(drawView);
		
	}

}
