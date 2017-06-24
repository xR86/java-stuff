package io.github.xr86.singlescreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.coffee_icon);
        //set alpha programatically so the animation can occur (check...)
        image.setAlpha(0.0f);
    }

    public void coffeeAnimationTrigger(View view){
        Log.v("MainActivity.java", "Animation triggered !");

        ImageView image = (ImageView) findViewById(R.id.coffee_icon);
        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
        //make animation effects persist (in this case, alpha will remain 0 after animation)
        hyperspaceJump.setFillAfter(true);

        //make image visible (Android ignores images that are invisible, and will set alpha in animation relative to the alpha of the image, not to 1f)
        //referenced here: https://stackoverflow.com/questions/29473355/android-doesnt-animate-alpha-if-its-initially-zero
        image.setAlpha(1f);
        image.startAnimation(hyperspaceJump);
    }
}
