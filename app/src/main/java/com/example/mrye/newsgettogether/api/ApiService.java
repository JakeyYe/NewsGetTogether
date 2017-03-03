package com.example.mrye.newsgettogether.api;

import com.example.mrye.newsgettogether.bean.StartImageBean;
import com.example.mrye.newsgettogether.bean.beforeZhiHuStory;
import com.example.mrye.newsgettogether.bean.latestZhiHuStory;
import com.example.mrye.newsgettogether.bean.zhiHuData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Mr.Ye on 2016/11/26.
 */

public interface ApiService {//api接口

    String API = "http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Observable<latestZhiHuStory> getLatestZhiHuStory();
    //http://news-at.zhihu.com/api/4/news/latest

    @GET("news/{id}")
    Observable<zhiHuData> getZhiHuData(@Path("id") int id);
    //http://news-at.zhihu.com/api/4/news/id

    @GET("news/before/{date}")
    Observable<beforeZhiHuStory> getBeforeZhiHuStory(@Path("date") String date);
    //http://news-at.zhihu.com/api/4/news/before/date

    @GET("start-image/1080*1920")
    Observable<StartImageBean> getStartImageBean();
    //http://news-at.zhihu.com/api/4/start-image/1080*1776
}
