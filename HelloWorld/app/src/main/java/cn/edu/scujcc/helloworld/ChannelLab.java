package cn.edu.scujcc.helloworld;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.strictmode.InstanceCountViolation;
import android.util.Log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * 单例数据源，提供频道数据
 */
public class ChannelLab {
    //单例第1步
    private static ChannelLab INSTANCE = null;

    private List<Channel> data;

    //单例第二步
    private ChannelLab() {
        //初始化空白列表
        data = new ArrayList<>();
        //删除网络访问
        //getData();
    }

    //单例第3步
    public static ChannelLab getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChannelLab();
        }
        return INSTANCE;
    }

    public void setData(List<Channel> newData) {
        this.data = newData;
    }

    /**
     * 生成测试数据，用于引入网络前的项目
     */


    /**
     * 获取当前数据源中总共有多少个频道
     *
     * @return
     */
    public int getSize() {
        return data.size();
    }

    /**
     * 获取一个指定频道
     *
     * @param position 频道的序号,从0开始
     * @return 频道对象Channel
     */
    public Channel getChannel(int position) {
        return data.get(position);
    }


    //访问网络得到真实数据，代替以前的test()方法
    public void getData(Handler handler) {
        Retrofit retrofit = RetrofitClient.get();
        ChannelApi api = retrofit.create(ChannelApi.class);
        Call<List<Channel>> call = api.getAllChannels();
        //enqueue把代码放在子线程中去运行
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response) {
                if (null != response && null != response.body()) {
                    Log.d("DianDian", "从阿里云得到的数据是:");
                    Log.d("DianDian",response.body().toString());
                    //不能在此操作RecyclerView刷新界面，只能使用线程通讯将数据传递到主线程
                    Message msg = new Message();
                    msg.obj = response.body();
                    handler.sendMessage(msg);
                } else {
                    Log.w("DianDian", "response没有数据!");
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {

            }
        });

    }
}







