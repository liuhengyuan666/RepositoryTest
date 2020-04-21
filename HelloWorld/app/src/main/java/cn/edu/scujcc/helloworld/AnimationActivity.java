package cn.edu.scujcc.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {
    private ImageView sxc;
    private Button button1,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        sxc = findViewById(R.id.sxc);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener( (v) -> {
            //启动渐变动画
            Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
            sxc.startAnimation(alpha);
        } );

        button2.setOnClickListener( (v) -> {
            //启动旋转动画
            Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
            sxc.startAnimation(rotate);
        } );

        button3.setOnClickListener( (v) -> {
            //启动缩放动画
            Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
            sxc.startAnimation(scale);
        } );

        button4.setOnClickListener( (v) -> {
            //启动平移动画
            Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
            sxc.startAnimation(translate);
        } );
    }
}
