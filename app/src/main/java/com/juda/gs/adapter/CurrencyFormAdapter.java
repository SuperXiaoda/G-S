package com.juda.gs.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.CurrencyFormBean;
import com.juda.gs.bean.KeyValueData;
import com.juda.gs.bean.SelectImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;


/**
 * @Description: 通用表单数据适配器
 * @author: CodingDa
 * @Date : 2020/5/16 10:15
 */
public class CurrencyFormAdapter extends BaseMultiItemQuickAdapter<CurrencyFormBean, BaseViewHolder> {

    /**
     * 条目类型
     */
// 输入类型
    public static final int ITEM_TYPE_INPUT = 0;
    // 单选类型
    public static final int ITEM_TYPE_RADIO_GROUP = 1;
    // 多选类型
    public static final int ITEM_TYPE_CHECK_BOX = 2;
    // 图片类型
    public static final int ITEM_TYPE_IMAGE = 3;
    // 跳转选择类型
    public static final int ITEM_TYPE_CHECK = 4;
    // 选择视频类型
    public static final int ITEM_TYPE_VIDEO = 5;

    // 默认添加图标
    private Drawable mDefaultAdd;

    public CurrencyFormAdapter() {

        // 绑定 layout 对应的 type
        addItemType(ITEM_TYPE_INPUT, R.layout.item_label_edittext);
        addItemType(ITEM_TYPE_RADIO_GROUP, R.layout.item_radio_group);
        addItemType(ITEM_TYPE_CHECK_BOX, R.layout.item_check_box);
        addItemType(ITEM_TYPE_IMAGE, R.layout.item_select_image);
        addItemType(ITEM_TYPE_CHECK, R.layout.item_label_check);
        addItemType(ITEM_TYPE_VIDEO, R.layout.item_video);
    }

    // 更新跳转选择值
    public void updateJumpItem(int position, String jumpValue) {
        getData().get(position).setJumpValue(jumpValue);
        notifyItemChanged(position);
    }

    // 更新选择视频跳转回值
    public void updateVideoItem(int position, String videoPath) {
        getData().get(position).setVideoPath(videoPath);
        notifyItemChanged(position);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, CurrencyFormBean currencyFormBean) {
        baseViewHolder.setText(R.id.label, currencyFormBean.getLabel());
        // 根据返回的 type 分别设置数据
        switch (baseViewHolder.getItemViewType()) {
            case ITEM_TYPE_INPUT:
                // 普通输入
                final EditText inputEdit = baseViewHolder.getView(R.id.input);
                inputEdit.setHint(currencyFormBean.getHint());
                inputEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (inputEdit.getText().toString().trim().length() > 0) {
                            mOnTextChangeLister.onTextChangeLister(baseViewHolder.getLayoutPosition(), inputEdit.getText().toString().trim());
                        }
                    }
                });
                break;
            case ITEM_TYPE_RADIO_GROUP:
                // 单选列表
                RecyclerView radioRecycle = baseViewHolder.findView(R.id.radio_recycle);
                GridLayoutManager radioLayoutManager = new GridLayoutManager(getContext(), 2);
                radioRecycle.setLayoutManager(radioLayoutManager);
                final RadioAdapter radioAdapter = new RadioAdapter(currencyFormBean.getDatas());
                radioAdapter.addChildClickViewIds(R.id.radio_root_layout);
                radioRecycle.setAdapter(radioAdapter);
                // 设置子控件点击监听
                radioAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    if (view.getId() == R.id.radio_root_layout) {
                        radioAdapter.changeCheck(position);
                        KeyValueData keyValueData = radioAdapter.getData().get(position);
                        mOnRadioChangeLister.onRadioChangeLister(baseViewHolder.getLayoutPosition(), keyValueData.getId(), keyValueData.getName());
                    }
                });
                break;
            case ITEM_TYPE_CHECK_BOX:
                // 多选列表
                RecyclerView checkBoxRecycle = baseViewHolder.findView(R.id.check_box_recycler);
                GridLayoutManager checkLayoutManager = new GridLayoutManager(getContext(), 2);
                checkBoxRecycle.setLayoutManager(checkLayoutManager);
                final CheckBoxAdapter checkBoxAdapter = new CheckBoxAdapter(currencyFormBean.getDatas());
                checkBoxAdapter.addChildClickViewIds(R.id.check_root_layout);
                checkBoxRecycle.setAdapter(checkBoxAdapter);
                // 设置子控件点击监听
                checkBoxAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    if (view.getId() == R.id.check_root_layout) {
                        checkBoxAdapter.changeCheck(position);
                        KeyValueData keyValueData = checkBoxAdapter.getData().get(position);
                        mOnCheckBoxChangeLister.onCheckBoxChangeLister(baseViewHolder.getLayoutPosition(), keyValueData.getId(), keyValueData.getName(), keyValueData.isCheck());
                    }
                });
                break;
            case ITEM_TYPE_IMAGE:
                mDefaultAdd = getContext().getResources().getDrawable(R.drawable.ic_add_d8_800_24dp);
                // 图片列表
                RecyclerView imageRecycle = baseViewHolder.findView(R.id.image_recycler);
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                imageRecycle.setLayoutManager(layoutManager);
                ImageAdapter imageAdapter = new ImageAdapter(9);
                imageRecycle.setAdapter(imageAdapter);
                List<SelectImage> defaultSelect = new ArrayList<>();
                defaultSelect.add(0, new SelectImage(true, mDefaultAdd));
                imageAdapter.setList(defaultSelect);
                imageAdapter.addChildClickViewIds(R.id.image);
                imageAdapter.addChildClickViewIds(R.id.delete);
                imageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    SelectImage selectImage = (SelectImage) adapter.getData().get(position);
                    switch (view.getId()) {
                        case R.id.image:
                            if (selectImage.isDefault()) {
                                // 跳转到图片选择器
                                RxGalleryFinal.with(getContext())
                                        .image()
                                        .maxSize(9)
                                        .imageLoader(ImageLoaderType.GLIDE)
                                        .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                                            @Override
                                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                                // 选中列表
                                                List<SelectImage> selectImages = imageAdapter.getData();
                                                // 移除默认添加照片
                                                selectImages.remove(selectImages.size() - 1);
                                                for (MediaBean photoInfo : imageMultipleResultEvent.getResult()) {
                                                    selectImages.add(0, new SelectImage(photoInfo.getOriginalPath(), false));
                                                }
                                                // 判断如果不足九张则添加默认图片
                                                if (selectImages.size() < 9) {
                                                    selectImages.add(selectImages.size(), new SelectImage(true, mDefaultAdd));
                                                }
                                                imageAdapter.setList(selectImages);
                                                mOnImageSelectChangeLister.onImageSelectChangeLister(baseViewHolder.getLayoutPosition(), imageAdapter.getFilesToCurrencyForm());
                                            }

                                            @Override
                                            public void onComplete() {
                                                super.onComplete();
                                                Toast.makeText(getContext(), "OVER", Toast.LENGTH_SHORT).show();
                                            }
                                        }).openGallery();
                            }
                            break;
                        case R.id.delete:
                            imageAdapter.deleteImage(position);
                            mOnImageSelectChangeLister.onImageSelectChangeLister(baseViewHolder.getLayoutPosition(), imageAdapter.getFilesToCurrencyForm());
                            break;
                    }
                });
                break;
            case ITEM_TYPE_CHECK:
                baseViewHolder.setText(R.id.check, currencyFormBean.getJumpValue());
                break;
            case ITEM_TYPE_VIDEO:
                if (currencyFormBean.getVideoPath() != null) {
                    ImageView videoImage = baseViewHolder.findView(R.id.video_image);
                    Glide.with(getContext()).load(Uri.fromFile(new File(currencyFormBean.getVideoPath()))).into(videoImage);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 文字改变监听
     */
    private OnTextChangeLister mOnTextChangeLister;

    public void setOnTextChangeLister(OnTextChangeLister onItemClickLister) {
        this.mOnTextChangeLister = onItemClickLister;
    }

    public interface OnTextChangeLister {
        void onTextChangeLister(int position, String value);
    }

    /**
     * 单选按钮改变状态监听
     */
    private OnRadioChangeLister mOnRadioChangeLister;

    public void setOnRadioChangeLister(OnRadioChangeLister onRadioChangeLister) {
        this.mOnRadioChangeLister = onRadioChangeLister;
    }

    public interface OnRadioChangeLister {
        void onRadioChangeLister(int formPosition, String radioId, String radioValue);
    }

    /**
     * 多选按钮改变状态监听
     */
    private OnCheckBoxChangeLister mOnCheckBoxChangeLister;

    public void setOnCheckBoxChangeLister(OnCheckBoxChangeLister onCheckBoxChangeLister) {
        this.mOnCheckBoxChangeLister = onCheckBoxChangeLister;
    }

    public interface OnCheckBoxChangeLister {
        void onCheckBoxChangeLister(int formPosition, String radioId, String radioValue, boolean checkState);
    }

    /**
     * 图片选择变化监听
     */
    private OnImageSelectChangeLister mOnImageSelectChangeLister;

    public void setOnImageSelectChangeLister(OnImageSelectChangeLister onImageSelectChangeLister) {
        this.mOnImageSelectChangeLister = onImageSelectChangeLister;
    }

    public interface OnImageSelectChangeLister {
        void onImageSelectChangeLister(int formPosition, List<SelectImage> photos);
    }

}
