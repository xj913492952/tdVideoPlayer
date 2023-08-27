package com.example.gsyvideoplayer;


import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;

import com.example.gsyvideoplayer.adapter.RecyclerBaseAdapter;
import com.example.gsyvideoplayer.adapter.RecyclerDefaultAdapter;
import com.example.gsyvideoplayer.adapter.RecyclerNormalAdapter;

import com.example.gsyvideoplayer.holder.RecyclerItemDefaultHolder;
import com.example.gsyvideoplayer.holder.RecyclerItemNormalHolder;
import com.example.gsyvideoplayer.model.VideoModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {


    @BindView(R.id.list_item_recycler)
    RecyclerView videoList;

    LinearLayoutManager linearLayoutManager;

    RecyclerDefaultAdapter recyclerBaseAdapter;

    List<VideoModel> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        recyclerBaseAdapter = new RecyclerDefaultAdapter(this, dataList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        videoList.setLayoutManager(linearLayoutManager);
        videoList.setAdapter(recyclerBaseAdapter);

        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                int p = GSYVideoManager.instance().getPlayPosition();
                if (p >= 0 && (p < firstVisibleItem || p > lastVisibleItem)) {
                    //可以保存下最近播放的两个视频播放进度，以便回看体验更好
                    GSYVideoManager.releaseAllVideos();
                    recyclerBaseAdapter.notifyItemChanged(p);
                }
            }
        });

        resolveData();

    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //GSYVideoManager.onResume(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    private void resolveData() {
        String face = "http://test-assets.wujinpu.cn/goods/goodsInfoImg/1a8e3067505640458199991ead4ee66e/4811568189101270.jpg?x-oss-process=style/280";
        String url = "https://res.exexm.com/cw_145225549855002";
        String title = "这是title";
        String url2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title2 = "哦？Title？";

        for (int i = 0; i < 19; i++) {
            VideoModel v = new VideoModel();
            v.videoPicUrl = face;
            if (i % 2 == 0) {
                v.videoUrl = url;
                v.title = title;
            } else {
                v.videoUrl = url2;
                v.title = title2;
            }
            dataList.add(v);
        }
        recyclerBaseAdapter.notifyDataSetChanged();
    }

}
