package com.mvp.demo.module.news.binder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mvp.demo.IntentAction;
import com.mvp.demo.R;
import com.mvp.demo.module.news.model.MultiNewsArticleDataBean;
import com.mvp.demo.utils.ImageLoader;
import com.mvp.demo.utils.LogUtils;
import com.mvp.demo.utils.SettingUtils;
import com.mvp.demo.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by luotao
 * 2018/1/25
 * emil:luotaosc@foxmail.com
 * qq:751423471
 *
 * @author 罗涛
 */

public class NewsArticleVideoViewBinder extends ItemViewBinder<MultiNewsArticleDataBean, NewsArticleVideoViewBinder.ViewHolder> {

    private static final String TAG = NewsArticleVideoViewBinder.class.getName();

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_news_article_video_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final MultiNewsArticleDataBean item) {

        final Context context = holder.itemView.getContext();

        try {
            if (null != item.getVideo_detail_info()) {
                if (null != item.getVideo_detail_info().getDetail_video_large_image()) {
                    String image = item.getVideo_detail_info().getDetail_video_large_image().getUrl();
                    if (!TextUtils.isEmpty(image)) {
                        ImageLoader.loadCenterCrop(context, image, holder.mIvVideoImage, R.color.viewBackground, R.mipmap.error_image);
                    }
                }
            } else {
                holder.mIvVideoImage.setImageResource(R.mipmap.error_image);
            }

            if (null != item.getUser_info()) {
                String avatar_url = item.getUser_info().getAvatar_url();
                if (!TextUtils.isEmpty(avatar_url)) {
                    ImageLoader.loadCenterCrop(context, avatar_url, holder.mCivMedia, R.color.viewBackground);
                }
            }

            String tv_title = item.getTitle();
            holder.mTvTitle.setTextSize(SettingUtils.getInstance().getTextSize());
            String tv_source = item.getSource();
            String tv_comment_count = item.getComment_count() + "评论";
            String tv_datetime = item.getBehot_time() + "";
            if (!TextUtils.isEmpty(tv_datetime)) {
                tv_datetime = TimeUtils.getTimesStampAgo(tv_datetime);
            }
            int video_duration = item.getVideo_duration();
            String min = String.valueOf(video_duration / 60);
            String second = String.valueOf(video_duration % 10);
            if (Integer.parseInt(second) < 10) {
                second = "0" + second;
            }
            String tv_video_time = min + ":" + second;

            holder.mTvTitle.setText(tv_title);
            holder.mTvPublishInfo.setText(tv_source + " - " + tv_comment_count + " - " + tv_datetime);
            holder.mTvVideoTime.setText(tv_video_time);
            holder.mIvDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,
                            holder.mIvDots, Gravity.END, 0, R.style.MyPopupMenu);
                    popupMenu.inflate(R.menu.menu_share);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menu) {
                            int itemId = menu.getItemId();
                            if (itemId == R.id.action_share) {
                                IntentAction.send(context, item.getTitle() + "\n" + item.getShare_url());
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

            RxView.clicks(holder.itemView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                            //                            VideoContentActivity.launch(item);
                        }
                    });
        } catch (Exception e) {
            LogUtils.printLogError(TAG, e.getMessage());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.civ_media)
        CircleImageView mCivMedia;
        //        @BindView(R.id.tv_publish_info)
        AppCompatTextView mTvPublishInfo;
        //        @BindView(R.id.iv_dots)
        ImageView mIvDots;
        //        @BindView(R.id.tv_title)
        TextView mTvTitle;
        //        @BindView(R.id.iv_video_image)
        AppCompatImageView mIvVideoImage;
        //        @BindView(R.id.tv_video_time)
        TextView mTvVideoTime;

        public ViewHolder(View itemView) {
            super(itemView);
            //            ButterKnife.bind(itemView);
            mCivMedia = itemView.findViewById(R.id.civ_media);
            mTvPublishInfo = itemView.findViewById(R.id.tv_publish_info);
            mIvDots = itemView.findViewById(R.id.iv_dots);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mIvVideoImage = itemView.findViewById(R.id.iv_video_image);
            mTvVideoTime = itemView.findViewById(R.id.tv_video_time);

        }
    }
}
