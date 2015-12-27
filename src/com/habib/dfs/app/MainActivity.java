package com.habib.dfs.app;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	
	public static ArrayList<ArrayList<Integer>> grap;
	public static int gSize;
	private int heigth_grap;
	private ArrayList<ArrayList<Integer>> dfstree;
	Context context;
	private int[] par;
	private int[] taken;
	private int[] level;
	private DrawGrapView drawGrapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = MainActivity.this;
		drawGrapView = new DrawGrapView(this);
		setContentView(drawGrapView);
	}

	private void drawDFS() {
		Intent intent = new Intent(this,SubActivity.class);
		intent.putExtra("height_grap", heigth_grap);
		intent.putExtra("par", par);
		//intent.putIntegerArrayListExtra("dfsTree", dfsTree);
		startActivity(intent);
	}
	
	private void dfsalgo(int u){
		
		dfstree.get(level[u]).add(u);
		
		for(int i = 0; i < grap.get(u).size(); i++){
			int v = grap.get(u).get(i);
			if (taken[v] == 0) {
				taken[v] = 1;
				level[v] = level[u] + 1;
				
				par[v] = u;
				if (level[v] > heigth_grap) {
					heigth_grap = level[v];
				}
				dfsalgo(v);
			}
		}
	}

	public void init(int src){
		taken = new int[gSize];
		par = new int[gSize];
		level = new int[gSize];
		heigth_grap = 0;
		dfstree = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < gSize; i++){
			taken[i] = 0;
			par[i] = i;
			dfstree.add(new ArrayList<Integer>());
		}
		taken[src] = 1;
		par[src] = src;
		level[src] = 0;
		dfsalgo(src);
		DrawGrap.dfstree = dfstree;
		drawDFS();
		
	}
}
