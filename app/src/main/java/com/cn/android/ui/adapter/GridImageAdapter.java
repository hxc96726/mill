package com.cn.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.hjq.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector.adapter
 * email：893855882@qq.com
 * data：16/7/27
 */
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<String> list = new ArrayList<>();
    private int selectMax = 9;
    private Context context;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<String> list) {
        if (this.list.size() > 0) {
            this.list.clear();
        }
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
//          tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.gv_filter_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.mipmap.img_add_icon);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemDeleteListener(position, view);
                    }
                }
            });
//            LocalMedia media = list.get(position);
//            int mimeType = media.getMimeType();
//            String path = "";
//            if (media.isCut() && !media.isCompressed()) {
//                // 裁剪过
//                path = media.getCutPath();
//            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//                path = media.getCompressPath();
//            } else {
//                // 原图
//                path = media.getPath();
//            }
//            // 图片
//            if (media.isCompressed()) {
//                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
//                Log.i("压缩地址::", media.getCompressPath());
//            }
//
//            Log.i("原图地址::", media.getPath());
//            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
//            if (media.isCut()) {
//                Log.i("裁剪地址::", media.getCutPath());
//            }
//            long duration = media.getDuration();
//            viewHolder.tv_duration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
//                    ? View.VISIBLE : View.GONE);
//            if (mimeType == PictureMimeType.ofAudio()) {
//                viewHolder.tv_duration.setVisibility(View.VISIBLE);
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
//                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
//            } else {
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
//                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
//            }
//            viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
//            if (mimeType == PictureMimeType.ofAudio()) {
//                viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
//            } else {

            ImageLoader.with(viewHolder.itemView.getContext())
                    .load(list.get(position)).circle(10)
                    .into(viewHolder.mImg);
//            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(position, v);
                    }
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);

        void onItemDeleteListener(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}