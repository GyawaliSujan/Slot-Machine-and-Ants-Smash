package com.example.myapplication;

import com.example.myapplication.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/*___________________________________________________________________
|
| Class: main_activity
|__________________________________________________________________*/
public class main_activity extends Activity 
{
	OpenGLSurfaceView view;
    MediaPlayer mp;
	/*___________________________________________________________________
	|
	| Function: onCreate
	|__________________________________________________________________*/
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
		
    	// Disable program title bar
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	// Enable full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		
        // ***** Init the 3D view programmatically *****
        //view = new OpenGLSurfaceView(this);
   		//setContentView(view);

        mp=null;
        mp=MediaPlayer.create(this,R.raw.booing);
        mp.setLooping(false);
        // ***** Init the view using a layout (allows mixing 3D view with other view controls)*****
        setContentView (R.layout.main);
        view = (OpenGLSurfaceView) this.findViewById (R.id.glSurface);
    }
    
    /*___________________________________________________________________
    |
    | Function: onPause
    |__________________________________________________________________*/
    @Override
    protected void onPause () 
    {
    	super.onPause ();
    	view.onPause(); 
    }

    /*___________________________________________________________________
    |
    | Function: onResume
    |__________________________________________________________________*/
    @Override
    protected void onResume () 
    {
    	super.onResume ();
      	view.onResume(); 
    }
    
    /*___________________________________________________________________
    |
    | Function: onBackPressed
    |__________________________________________________________________*/
    @Override
    public void onBackPressed () 
    {
    	new AlertDialog.Builder(this)
        .setMessage("Do you want to quit this application?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        	@Override
        	public void onClick(DialogInterface dialog, int which) {
                mp.start();
                mp=null;
        		main_activity.super.onBackPressed ();
        		return;
            } 
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
        	@Override
        	public void onClick(DialogInterface dialog, int which) {
        		return;
           }
        })
        .show ();
    }
}
    