package io.github.xr86.android_musicalstructure;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMediaPlayer();
    }

    private void setupMediaPlayer() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.the_complex);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + "the_complex";
        final Uri uri = Uri.parse(uriPath);
        mmr.setDataSource(getApplication(), uri);

        final String artistName =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        final String trackName =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        //final byte[] trackArt =
        //        mmr.getEmbeddedPicture();

        final Button play = (Button) findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            boolean trackOpened = false;

            public void onClick(View arg0) {
                if (!trackOpened) {
                    TextView trackTitle = (TextView) findViewById(R.id.track_title);
                    if (artistName != "") {
                        trackTitle.setText(artistName + " - " + trackName);
                    } else {
                        trackTitle.setText(getResources().getResourceEntryName(R.raw.the_complex));
                    }
                    trackOpened = true;
                }
                if (mp.isPlaying()) {
                    mp.pause();
                    play.setText("Start");
                } else {
                    mp.start();
                    play.setText("Pause");
                }
            }
        });
    }

    public void buttonClickPlaceholder(View v) {
        Toast.makeText(this, "Feature not implemented !", Toast.LENGTH_SHORT).show();
    }
}
