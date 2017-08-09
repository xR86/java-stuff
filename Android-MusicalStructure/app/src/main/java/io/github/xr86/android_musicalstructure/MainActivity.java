package io.github.xr86.android_musicalstructure;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMediaPlayer();
    }

    private void setupMediaPlayer() {
        final MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.the_complex);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + "the_complex";
        final Uri uri = Uri.parse(uriPath);
        mmr.setDataSource(getApplication(), uri);

        final String artistName =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        final String trackName =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        final String trackDurationStr =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        //final byte[] trackArt =
        //        mmr.getEmbeddedPicture();
        long trackDuration = 0;
        if(trackDurationStr != null){
            Log.v("MainActivity.java", String.format("trackDurationStr = %s", trackDurationStr));
            trackDuration = Long.parseLong(trackDurationStr);
            Log.v("MainActivity.java", "trackDuration = " + trackDuration);
        }
        final long trackDurationCopy = trackDuration;

        final long seconds = trackDuration % 60000 / 1000;
        final long minutes = trackDuration / 60000;
        final long hours = minutes / 60;
        Log.v("MainActivity.java", "seconds = " + seconds);
        Log.v("MainActivity.java", "minutes = " + minutes);
        Log.v("MainActivity.java", "hours = " + hours);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.track_seekbar);
        seekBar.setMax(mPlayer.getDuration());

        TextView durView = (TextView) findViewById(R.id.track_time_final);
        durView.setText("" + minutes + ":" + seconds);

        final Button playBtn = (Button) findViewById(R.id.play);

        playBtn.setOnClickListener(new View.OnClickListener() {
            boolean trackOpened = false;

            public void onClick(View arg0) {
                if (!trackOpened) {
                    TextView trackTitle = (TextView) findViewById(R.id.track_title);
                    if (artistName != "") {
                        trackTitle.setText(artistName + " - " +  trackName); //Long.toString(minutes)
                    } else {
                        trackTitle.setText(getResources().getResourceEntryName(R.raw.the_complex));
                    }
                    trackOpened = true;
                }
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    playBtn.setText("Start");
                } else {
                    mPlayer.start();
                    playBtn.setText("Pause");
                }
            }
        });

//        final Handler mHandler = new Handler();//Make sure you update Seekbar on UI thread
//        MainActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(mPlayer != null){
//                    int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
//                    seekBar.setProgress(mCurrentPosition);
//                }
//                mHandler.postDelayed(this, 1000);
//            }
//        });
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(mPlayer != null && fromUser){
//                    mPlayer.seekTo(progress * 1000);
//                }
//            }
//        });
    }

    public void buttonClickPlaceholder(View v) {
        Toast.makeText(this, "Feature not implemented !", Toast.LENGTH_SHORT).show();
    }
}
