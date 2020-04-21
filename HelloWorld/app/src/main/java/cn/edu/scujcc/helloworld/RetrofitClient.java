package cn.edu.scujcc.helloworld;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

//使用单例模式创建Retrofit，避免浪费资源
public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit get() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://47.112.236.48:8080")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
