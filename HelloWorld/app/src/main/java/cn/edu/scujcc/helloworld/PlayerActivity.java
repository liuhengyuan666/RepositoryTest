package cn.edu.scujcc.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.Serializable;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView tvPlayerView;
    private SimpleExoPlayer player;
    private Channel channel;
    private MediaSource videoSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        channel = (Channel) getIntent().getSerializableExtra("channel");
        updateUI();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clean();
    }

    //自定义方法初始化播放器
    private void init() {
        player = ExoPlayerFactory.newSimpleInstance(this);
        player.setPlayWhenReady(true);
        //从界面查找视图
        PlayerView playerView = findViewById(R.id.tv_player);
        //关联视图与播放器
        playerView.setPlayer(player);
        //准备播放的媒体
        Uri videoUrl = Uri.parse(channel.getUrl());
        DataSource.Factory factory =
                new DefaultDataSourceFactory(this,"DianDian");
        MediaSource videoSource = new HlsMediaSource.Factory(factory).createMediaSource(videoUrl);
        player.prepare(videoSource);
    }

    //初始化界面
    private void updateUI() {
        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(this.channel.getTitle());
        TextView tvQuality = findViewById(R.id.tv_quality);
        tvQuality.setText(this.channel.getQuality());
    }

    //自定义方法，清理不用的资源
    private void clean() {
        if (player != null) {
            player.release();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (player == null) {
            initPlayer();
            if (tvPlayerView != null) {
                tvPlayerView.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tvPlayerView != null) {
            tvPlayerView.onPause();
        }
        clean();
    }

    //重构。初始化播放器（旧）
    private void initPlayer() {
        if (player == null) {
            tvPlayerView = findViewById(R.id.tv_player);
            //创建播放器
            player = ExoPlayerFactory.newSimpleInstance(this);
            player.setPlayWhenReady(true);
            //绑定界面与播放器
            tvPlayerView.setPlayer(player);
            //准备播放源
            Uri uri = Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8");
            DataSource.Factory factory =
                    new DefaultDataSourceFactory(this,"DianDian");
            videoSource = new HlsMediaSource.Factory(factory).createMediaSource(uri);
        }
        //把播放源传给播放器（并开始播放）
        player.prepare(videoSource);
    }


}
