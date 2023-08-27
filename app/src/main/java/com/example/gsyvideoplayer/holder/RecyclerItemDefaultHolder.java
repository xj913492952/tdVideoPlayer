package com.example.gsyvideoplayer.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.model.VideoModel;
import com.example.gsyvideoplayer.video.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerItemDefaultHolder extends RecyclerView.ViewHolder {

    protected Context context;

    @BindView(R.id.video_item_player)
    StandardGSYVideoPlayer gsyVideoPlayer;
    private ImageView faceView;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public RecyclerItemDefaultHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        faceView = new ImageView(context);
        faceView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void onBind(final int position, VideoModel v) {
        //增加封面
        if (faceView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) faceView.getParent();
            viewGroup.removeView(faceView);
        }
        Glide.with(context).load(v.videoPicUrl).into(faceView);
        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(faceView)
                .setUrl(v.videoUrl)
                .setVideoTitle(v.title)
                .setCacheWithPlay(false)
                .setAutoFullWithSize(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setMapHeadData(header)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            //GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        //GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        //GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                    }
                }).build(gsyVideoPlayer);
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

}