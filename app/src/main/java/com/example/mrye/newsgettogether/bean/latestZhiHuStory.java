package com.example.mrye.newsgettogether.bean;


import java.util.List;

/**
 * Created by Mr.Ye on 2016/11/26.
 */

public class latestZhiHuStory {//知乎消息包括两部分数据


    private String date;//日期
    private List<StoriesBean> stories;//普通stories
    private List<TopStoriesBean> top_stories;//顶部RollViewPager上的stories


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }



}
