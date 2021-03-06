package com.iguitar.xiaoxiaozhitan.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.VideoUrls;
import com.iguitar.xiaoxiaozhitan.ui.adapter.MyRecyclerViewVideoAdapter;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;

/**
 * 视频播放页面
 * Created by Jiang on 17/9/24.
 */
public class MyVideoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecyclerViewVideoAdapter adapterVideoList;

    private TextView tv_tittle;
    private ImageButton button;

    private List<VideoUrls> videoUrlsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setTitle("NormalRecyclerView");
        setContentView(R.layout.activity_video_content);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        videoUrlsList = (List<VideoUrls>) getIntent().getSerializableExtra("videoUrls");
        initViews();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        tv_tittle = (TextView) findViewById(R.id.tv_top_title);
        button = (ImageButton) findViewById(R.id.btn_back);
        tv_tittle.setText("视频播放");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMyActivity();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterVideoList = new MyRecyclerViewVideoAdapter(this, videoUrlsList);
        recyclerView.setAdapter(adapterVideoList);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer jzvd = (JZVideoPlayer) view.findViewById(R.id.videoplayer);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    JZVideoPlayer.releaseAllVideos();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提供统一的关闭方式
     * <p>
     * （另一个Activity）
     */
    public void finishMyActivity() {
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

}
