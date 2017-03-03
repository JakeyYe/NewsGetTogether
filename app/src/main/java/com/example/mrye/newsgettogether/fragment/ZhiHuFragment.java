package com.example.mrye.newsgettogether.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.activity.ZhiHuDescribeActivity;
import com.example.mrye.newsgettogether.api.ApiService;
import com.example.mrye.newsgettogether.api.ServiceCreator;
import com.example.mrye.newsgettogether.bean.beforeZhiHuStory;
import com.example.mrye.newsgettogether.bean.latestZhiHuStory;
import com.example.mrye.newsgettogether.easyrecyclerview.BannerAdapter;
import com.example.mrye.newsgettogether.easyrecyclerview.ZhiHuAdapter;
import com.example.mrye.newsgettogether.utils.DisplayUtil;
import com.example.mrye.newsgettogether.utils.GetIdUtil;
import com.example.mrye.newsgettogether.utils.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ZhiHuFragment extends Fragment implements
        RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG="ZhiHuFragment";

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    private ZhiHuAdapter adapter;
    Subscription mSubscriptionLatest, mSubscriptionBefore;

    private int page = 1;

    private Handler handler = new Handler();


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zhi_hu, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter = new ZhiHuAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //加载最新数据
        getLatestData();//addAll,addHeader

        //加载更多
        adapter.setMore(R.layout.view_more, this);

        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();//继续尝试加载更多
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });

        adapter.setError(R.layout.view_more, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();//重新加载更多
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });

        //刷新事件，一开始就加载了最新的News，所以触发刷新，也不用处理什么事
        recyclerView.setRefreshListener(this);


        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ZhiHuDescribeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", adapter.getAllData().get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return view;   //创建该Fragment的视图
    }

    //该方法在onCreateView()方法后调用，可以在该方法中对Fragment中的组件进行操作
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //加载最新的数据
    private void getLatestData() {
        ApiService apiManager = ServiceCreator.getInstance().createService();

        mSubscriptionLatest = apiManager.getLatestZhiHuStory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<latestZhiHuStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(getActivity(),"网络连接失败！");
                        e.printStackTrace();
                    }

                    //要在这里判断获取的数据是同步获得的，还是异步获取的，也就是分别获取的，这样就可能导致这样的问题
                    @Override
                    public void onNext(final latestZhiHuStory data) {
                        //顺序位置应该没有错啊，是先adapter.addAll()，再adapter.addHeader(),可是为什么还是不对？
                        adapter.addAll(data.getStories());

                        //获取数据后直接在这里加载RollViewPager，数据为TopStoriesBean
                        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                            @Override
                            public View onCreateView(ViewGroup parent) {
                                //TODO 怎样在进入界面就看到这个RollPagerView，在可视界面出现,question
                                RollPagerView header = new RollPagerView(getActivity());
                                //设置RollViewPager的指示器（圆点）
                                header.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.GRAY));
                                //设置指示View的位置，设置四个方向上的Padding值
                                header.setHintPadding(0, 0, 0, DisplayUtil.dip2px(getActivity(), 8f));
                                header.setPlayDelay(3000);//设置轮播时间，默认为0，表示不循环播放
                                header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        DisplayUtil.dip2px(getActivity(), 200)));
                                header.setAdapter(new BannerAdapter(getActivity(), data.getTop_stories()));
                                return header;
                            }

                            @Override
                            public void onBindView(View headerView) {

                            }
                        });
                        Log.i(TAG, "onNext: here");//查看onNext是不是一次性完成的？
                        recyclerView.scrollToPosition(0);
                        Log.i(TAG, "onNext: scrollToPosition");
                    }
                });
    }

    //加载当天以前的数据,加载更多数据
    private void getMoreData() {
        ApiService apiManager = ServiceCreator.getInstance().createService();
        Log.i("getMoreData", GetIdUtil.getId(page));
        mSubscriptionBefore = apiManager.getBeforeZhiHuStory(GetIdUtil.getId(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<beforeZhiHuStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        adapter.pauseMore();//暂停加载更多，显示错误View,R.layout.view_error
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(beforeZhiHuStory data) {
                        adapter.addAll(data.getStories());
                        //注意一定当添加0条数据或null时,会结束加载更多,显示没有更多。
                        Log.i("getMoreData", "Story " + data.getStories().size());
                    }
                });
        page++;

    }

    @Override
    public void onMoreShow() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMoreData();
            }
        }, 1000);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSubscriptionLatest != null) {
            if (mSubscriptionLatest.isUnsubscribed()) {
                mSubscriptionLatest.unsubscribe();//取消订阅，防止内存泄漏
            }
        }
        if (mSubscriptionBefore != null) {
            if (mSubscriptionBefore.isUnsubscribed()) {
                mSubscriptionBefore.unsubscribe();
            }
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {//这里可以判断一下是否一直在当天，在当天就不会再有更新的数据了
            @Override
            public void run() {
                recyclerView.setRefreshing(false);
//                ToastUtil.showToast(getActivity(),"没有更多最新数据了！");//这里向下滑动刷新什么都不显示，因为已经加载了最新的数据了
            }
        }, 2000);
    }
}
