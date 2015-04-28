/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gotech.gotechfactorytest;



import android.app.Activity;  
import android.media.MediaPlayer;  
import android.net.Uri;  
import android.os.Bundle;  
import android.os.Environment;  
import android.util.Log;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.MediaController;  
import android.widget.TextView;  
import android.widget.VideoView;  
 
public class VideoBurning extends Activity {  
 

    Button mPlayVido = null;  
    Button mStopVido = null;  
 
    TextView mTextView = null;  
    VideoView mVideoView;  
    String mUri ;  
 
 
    MediaPlayer mMediaVido = null;  
 
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.videoview);  
   


 
         mUri = "android.resource://" + getPackageName() + "/" + R.raw.testvideo;

 

          //vv.setVideoURI(Uri.parse(uri)); 
       
 
        // 创建视频播放视图  
        mVideoView = (VideoView) findViewById(R.id.vidoView);  
 
        // 设置MediaController  
       // mVideoView.setMediaController( new MediaController(this));  
 
        
     //   mTextView = (TextView) findViewById(R.id.textView);  
 
        
        
 
        mVideoView.setVideoURI(Uri.parse(mUri));  
                mVideoView.start(); 



     mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
  
                    @Override  
                    public void onCompletion(MediaPlayer mp) {  
                        mVideoView.setVideoURI(Uri.parse(mUri));  
                mVideoView.start();  
  
                    } 
                }); 


 /*
        // 关闭视频  
        mStopVido.setOnClickListener(new OnClickListener() {  
 
            @Override  
            public void onClick(View v) {  
                mTextView.setText("停止播放视频");  
                mVideoView.stopPlayback();  
            }  
        });  
*/
    }  
    
    
    /////////////////////////////////////////////////////////////////////
    @Override  
     protected void onStop() {  
        Log.d("TAG-onStop", "onStop()----VideoBurning--------" );  
		if(mVideoView!=null)
        mVideoView.stopPlayback();
		mVideoView = null;
        super.onRestart();  
    }
    
    @Override  
    protected void onResume() {  
       Log.d("TAG-onResume", "onResume()------VideoBurning------" ); 
       if( mVideoView != null)
    	   mVideoView.start(); 
       super.onRestart();  
   }
    ///////////////////////////////////////////////////////////////////////////////////
}
