package com.example.mrye.newsgettogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrye.newsgettogether.R;
import com.example.mrye.newsgettogether.api.ApiService;
import com.example.mrye.newsgettogether.api.ServiceCreator;
import com.example.mrye.newsgettogether.bean.zhiHuData;
import com.example.mrye.newsgettogether.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


//TODO Toolbar用自定义ScrollView试过了，但是效果不好，再找其他的方法解决这个，或者直接让其不随着滑动
public class ZhiHuDescribeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_describe)
    ImageView imgDescribe;
    @BindView(R.id.tv_from_describe)
    TextView tvFromDescribe;
    @BindView(R.id.tv_title_describe)
    TextView tvTitleDescribe;
    @BindView(R.id.web_describe)
    WebView webDescribe;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private Integer id;
    String mShareUrl;
    Subscription mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_hu_describe);
        ButterKnife.bind(this);

        //获取触发滑动事件的最小移动距离
        id = getIntent().getIntExtra("id", 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示Label
        //设置ToolBar上的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData();

    }


    private void getData() {
        ApiService lZhiHuDataManager = ServiceCreator.getInstance().createService();

        mSubscription = lZhiHuDataManager.getZhiHuData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<zhiHuData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(ZhiHuDescribeActivity.this, "网络连接失败");
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(zhiHuData data) {

                        Glide.with(ZhiHuDescribeActivity.this)
                                .load(data.getImage())
                                .placeholder(R.drawable.ic_favorite)
                                .centerCrop()
                                .into(imgDescribe);
                        tvTitleDescribe.setText(data.getTitle());
                        tvFromDescribe.setText(data.getImage_source());
                        //设置WebView,并加载URL
                        webDescribe.getSettings()
                                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        webDescribe.setVerticalScrollBarEnabled(false);
                        webDescribe.setHorizontalScrollBarEnabled(true);
                        webDescribe.loadData(getHtmlData(data.getCss().get(0), data.getBody()),
                                "text/html;charset=UTF-8", null);
                        mShareUrl = data.getShare_url();
                    }
                });
    }

    private String getHtmlData(String css, String bodyHtml) {
        String head = "<head>" +
                "<link href=\"" + css + "\" rel=\" stylesheet\">" + "<head>";
        String body = bodyHtml.replace("class=\"headline\"", "");//去掉这部分
        String html = "<html>" + head + "<body>" + body + "</body></html>";
        return html;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {//分享事件
            if (!TextUtils.isEmpty(mShareUrl)) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);//Intent.ACTION_SEND分享Action
                shareIntent.putExtra(Intent.EXTRA_TEXT, mShareUrl);
                shareIntent.setType("text/plain");//设置数据类型
                startActivity(shareIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mSubscription != null) {
            if (mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
                //解除订阅关系，避免内存泄漏
            }
        }
    }
}
