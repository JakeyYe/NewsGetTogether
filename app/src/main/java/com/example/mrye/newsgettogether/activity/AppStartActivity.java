package com.example.mrye.newsgettogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mrye.newsgettogether.MainActivity;
import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.api.ApiService;
import com.example.mrye.newsgettogether.api.ServiceCreator;
import com.example.mrye.newsgettogether.bean.StartImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppStartActivity extends AppCompatActivity {//展现一个图片扩张的启动页效果

    @BindView(R.id.start_image)
    ImageView start_image;
    @BindView(R.id.start_image_author)
    TextView start_image_Author;//启动页Activity

    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        ButterKnife.bind(this);

        addData();
        //启动页图片一个扩张的动画效果
        mCountDownTimer.start();


    }

    //CountDownTimeer倒计时
    private CountDownTimer mCountDownTimer = new CountDownTimer(3000, 1200) {
        boolean flag = false;

        @Override
        public void onTick(long millisUntilFinished) {

            if (flag) {//在过了3秒后执行动画
                start_image.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setInterpolator(new AccelerateDecelerateInterpolator())//先加速后减速
                        .setDuration(3000)
                        .start();
            }
            flag = !flag;

        }

        @Override
        public void onFinish() {
            startActivity(new Intent(AppStartActivity.this, MainActivity.class));
            finish();
        }
    };

    public void addData() {

        ApiService apiManager = ServiceCreator.getInstance().createService();

        //被观察者Observable 订阅subscribe 观察者 Observer/Subscriber
        mSubscription = apiManager.getStartImageBean()//返回的Observable<StartImageBean>对象
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(AppStartActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                        Glide.with(AppStartActivity.this)
                                .load(R.drawable.ic_favorite)
                                .into(start_image);
                        start_image_Author.setText("知乎");
                    }

                    @Override
                    public void onNext(StartImageBean startImageBean) {
                        //将数据填充到视图中
                        Glide.with(AppStartActivity.this)
                                .load(startImageBean.getImg())
                                .placeholder(R.drawable.ic_favorite)
                                .into(start_image);
                        start_image_Author.setText(startImageBean.getText());

                        /*TODO
                        *不需要每次打开都用网络加载网上的图片，从本地获取同一天要显示的图片
                        *而且也不需要每次都打开这个启动页Activity
                        */

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mSubscription!=null){
            if(mSubscription.isUnsubscribed()){
                mSubscription.unsubscribe();
                //调用unsubscribe()方法解除Observable对Subscriber的引用关系，以避免内存的泄漏
            }
        }
    }
}
