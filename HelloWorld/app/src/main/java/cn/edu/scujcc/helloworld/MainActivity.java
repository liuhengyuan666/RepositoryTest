package cn.edu.scujcc.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView channelRv;
    private ChannelRvAdapter rvAdapter;
    private ChannelLab lab = ChannelLab.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.channelRv = findViewById(R.id.channel_rv);
        //Lambda简化
        rvAdapter = new ChannelRvAdapter(this, p -> {
            //跳转到新界面，使用意图Intent
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            //TODO 传递用户选中的频道到下一个界面
            //通过位置p得到当前频道channel
            Channel c = lab.getChannel(p);
            intent.putExtra("channel", c);
            startActivity(intent);
        });

        initData();

        this.channelRv.setAdapter(rvAdapter);
        this.channelRv.setLayoutManager(new LinearLayoutManager(this));

    }


    //初始化即将显示的数据
    private void initData() {
        //得到网络上的数据后，去更新界面
        Handler handler = new Handler() {
            //快捷键Ctrl o
            @Override
            public void handleMessage(@NonNull Message msg) {
                //若收到来自其他线程的数据，则运行以下代码
                List<Channel> channels = (List<Channel>) msg.obj;
                lab.setData(channels);
                rvAdapter.notifyDataSetChanged();
            }
        };
        lab.getData(handler);
    }
}
