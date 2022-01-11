package com.kumarsunil17.opengldemo.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kumarsunil17.opengldemo.MainActivity;
import com.kumarsunil17.opengldemo.controller.TouchController;


import java.io.IOException;

/**
 * This is the actual opengl view. From here we can detect touch gestures for example
 * 
 * @author andresoviedo
 *
 */
public class ModelSurfaceView extends GLSurfaceView {

	private MainActivity parent;
	private  ModelRenderer mRenderer;
	private TouchController touchHandler;

	public ModelSurfaceView(Context context) throws IOException, IllegalAccessException {
		super(context);
		initView((MainActivity) context);

	}

	public ModelSurfaceView(Context context, AttributeSet attrs) throws IOException, IllegalAccessException {
		super(context, attrs);
		initView((MainActivity) context);
	}

	public void initView(MainActivity parent) throws IllegalAccessException, IOException {

		// parent component
		this.parent = parent;

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// This is the actual renderer of the 3D space
		mRenderer = new  ModelRenderer(this);
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		// TODO: enable this?
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		touchHandler = new TouchController(this, mRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return touchHandler.onTouchEvent(event);
	}

	public MainActivity getModelActivity() {
		return parent;
	}

	public  ModelRenderer getModelRenderer(){
		return mRenderer;
	}

}