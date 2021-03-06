package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.VideoBottomBean;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import java.util.List;

/**
 * 视频页面底部栏目Adapter
 * Created by Jiang on 2017-09-21.
 */

public class MyRecyclerViewVideoBottomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoBottomBean> videoBottomBeanList;
    private Context context;
    private LayoutInflater inflater;
    private OnVideoClickListenner onVideoClickListenner;

    public interface OnVideoClickListenner {
        void OnVideoClickListener(int position);
    }

    public void setOnVideoClickListenner(OnVideoClickListenner onVideoClickListenner) {
        this.onVideoClickListenner = onVideoClickListenner;
    }

    public MyRecyclerViewVideoBottomItemAdapter(Context context, List<VideoBottomBean> videoBottomBeanList) {
        this.context = context;
        this.videoBottomBeanList = videoBottomBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new ViewHolderBottomItem(View.inflate(context, R.layout.fragment_video_bottom_layout, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderBottomItem) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return videoBottomBeanList.size();
    }

    public class ViewHolderBottomItem extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_description;
        private LinearLayout ll_bottom_item;

        public ViewHolderBottomItem(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fragment_video_bottom_item);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            ll_bottom_item = (LinearLayout) itemView.findViewById(R.id.ll_bottom_item);

        }

        public void setData(final int position) {
            ll_bottom_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVideoClickListenner != null) {
                        onVideoClickListenner.OnVideoClickListener(position);
                    }
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtil.getDisplayWidth((Activity) context) / 2, CommonUtil.getDisplayHeight((Activity) context) / 4 - 40);
            params.setMargins(10, 10, 10, 0);
            imageView.setLayoutParams(params);
            Glide.with(context)
                    .load(videoBottomBeanList.get(position).getImageUrl())
                    .placeholder(R.mipmap.myicon_mini)
                    .dontAnimate()
                    .into(imageView);
            tv_description.setText(videoBottomBeanList.get(position).getDescription());
        }
    }

}
