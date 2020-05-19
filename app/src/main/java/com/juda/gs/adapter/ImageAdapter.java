package com.juda.gs.adapter;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.SelectImage;

import java.util.ArrayList;
import java.util.List;



/**
 * @Description: 图片选择数据适配器
 * @author: CodingDa
 * @Date : 2020/3/23 16:14
 */
public class ImageAdapter extends BaseQuickAdapter<SelectImage, BaseViewHolder> {

    // 照片数量
    private int mImageCount;

    public ImageAdapter(int imageCount) {
        super(R.layout.item_image, null);
        this.mImageCount = imageCount;
    }

    // 删除图片
    public void deleteImage(int position) {
        if (getData().size() == mImageCount) {
            getData().remove(position);
            // 判断最后一张图片是否是默认图片
            if (!getData().get(getData().size() - 1).isDefault()) {
                // 判断如果不足九张则添加默认图片
                getData().add(getData().size(), new SelectImage(true, getContext().getResources().getDrawable(R.drawable.ic_add_d8_800_24dp)));
            }
        } else {
            getData().remove(position);
        }
        notifyDataSetChanged();
    }

    // 获取图片
    public List<SelectImage> getFiles() {
        if (getData().size() == mImageCount) {
            // 判断最后一条数据是否为默认添加
            if (getData().get(getData().size() - 1).isDefault()) {
                getData().remove(getData().size() - 1);
                return getData();
            } else {
                return getData();
            }
        } else {
            getData().remove(getData().size() - 1);
            return getData();
        }
    }

    // 通用表单获取图片
    public List<SelectImage> getFilesToCurrencyForm() {
        List<SelectImage> selectImages = new ArrayList<>();
        // 复制数据
        for (SelectImage selectImage : getData()) {
            SelectImage copySelectImage = new SelectImage();
            copySelectImage.setDefault(selectImage.isDefault());
            copySelectImage.setPath(selectImage.getPath());
            selectImages.add(copySelectImage);
        }
        if (selectImages.size() == mImageCount) {
            // 判断最后一条数据是否为默认添加
            if (selectImages.get(selectImages.size() - 1).isDefault()) {
                selectImages.remove(selectImages.size() - 1);
                return selectImages;
            } else {
                return selectImages;
            }
        } else {
            selectImages.remove(selectImages.size() - 1);
            return selectImages;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectImage item) {
        AppCompatImageView imageView = helper.getView(R.id.image);
        AppCompatImageView deleteView = helper.getView(R.id.delete);
        if (item.isDefault()) {
            deleteView.setVisibility(View.GONE);

            helper.setImageDrawable(R.id.image, item.getDefaultAdd());
        } else {
            deleteView.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(item.getPath())
                    .into(imageView);
        }
    }
}
