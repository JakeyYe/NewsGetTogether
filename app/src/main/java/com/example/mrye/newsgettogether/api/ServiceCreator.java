package com.example.mrye.newsgettogether.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Mr.Ye on 2016/11/27.
 */

public class ServiceCreator {

    private Retrofit mRetrofit;

    public ServiceCreator() {  //因为采用的都是同样的Retrofit框架加载数据，所以将其封装起来直接调用
        if(mRetrofit==null){
            mRetrofit=new Retrofit.Builder()
                    .baseUrl(ApiService.API)//baseUrl()的参数的URL必须以/结尾
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    private static class Holder{
        private static final ServiceCreator instance=new ServiceCreator();
    }

    public static ServiceCreator getInstance(){//采用静态工厂方法创建实例
        return Holder.instance;
    }

    public ApiService createService(){
        return mRetrofit.create(ApiService.class);
    }


}
