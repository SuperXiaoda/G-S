package com.juda.gs.activity;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juda.gs.R;
import com.juda.gs.adapter.CurrencyFormAdapter;
import com.juda.gs.bean.CurrencyFormBean;
import com.juda.gs.bean.KeyValueData;
import com.youngfeng.snake.annotations.EnableDragToClose;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @Description: 通用表单页面
 * @author: CodingDa
 * @Date : 2020/5/16 9:58
 */
@EnableDragToClose
public class CurrencyFormActivity extends BaseActivity {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 标题
    @BindView(R.id.title)
    AppCompatTextView mTitle;
    // 操作
    @BindView(R.id.operation)
    AppCompatTextView mOperation;
    // 通用表单列表
    @BindView(R.id.form_recycler)
    RecyclerView mFormRecycler;

    // 通用表单数据适配器
    private CurrencyFormAdapter mAdapter;
    // 数据
    private List<CurrencyFormBean> mDatas = new ArrayList<>();

    // 跳转选择数据条目position
    private int mJumpSelectFormPosition;
    // 选择视频条目position
    private int mSelectVideoPosition;
    // 打开跳转选择数据页面Code
    private int mOpenJumpSelectCode = 1001;

    @Override
    protected int getContentView() {
        return R.layout.activity_currency_form;
    }

    @Override
    protected void init() {
        mTitle.setText(getString(R.string.currency_form));
        mOperation.setText(getString(R.string.save));
        mFormRecycler.hasFixedSize();
        mFormRecycler.setVerticalScrollBarEnabled(true);
        mFormRecycler.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mFormRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new CurrencyFormAdapter();
        mFormRecycler.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void setListener() {
        // 添加跳转选择点击事件
        mAdapter.addChildClickViewIds(R.id.label_check_root_layout);
        // 添加视频选择点击事件
        mAdapter.addChildClickViewIds(R.id.video_image);
        // 输入框布局监听
        mAdapter.setOnTextChangeLister((position, value) -> Toast.makeText(getApplicationContext(), "这是第【" + position + "】个输入框的值-->" + value, Toast.LENGTH_LONG).show());
        // 单选布局监听
        mAdapter.setOnRadioChangeLister((formPosition, radioId, radioValue) -> Toast.makeText(getApplicationContext(), "这是表单第【" + formPosition + "】个单选的值-->" + radioId + "---->" + radioValue, Toast.LENGTH_LONG).show());
        // 多选布局监听
        mAdapter.setOnCheckBoxChangeLister((formPosition, radioId, radioValue, checkState) -> Toast.makeText(getApplicationContext(), "这是表单第【" + formPosition + "】个多选的值-->" + radioId + "---->" + radioValue + "---->" + (checkState ? "选中" : "未选中"), Toast.LENGTH_LONG).show());
        // 图片选择监听
        mAdapter.setOnImageSelectChangeLister((formPosition, photos) -> Toast.makeText(getApplicationContext(), "这是表单第【" + formPosition + "】个单选的值-->" + photos.toString(), Toast.LENGTH_LONG).show());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.label_check_root_layout:
                    mJumpSelectFormPosition = position;
                    // 跳转选择数据
                    CurrencyFormBean currencyFormJumpSelectBean = mAdapter.getData().get(position);
                    Intent intent = new Intent(getApplicationContext(), JumpSelectActivity.class);
                    intent.putExtra("ItemPosition", String.valueOf(position));
                    intent.putExtra("GetDataKey", String.valueOf(currencyFormJumpSelectBean.getCheckId()));
                    startActivityForResult(intent, mOpenJumpSelectCode);
                    break;
                case R.id.video_image:
                    mSelectVideoPosition = position;
                    // 跳转到图片选择器
                    RxGalleryFinalApi.getInstance(CurrencyFormActivity.this)
                            .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                            .setVDRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                                @Override
                                protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                    Toast.makeText(getApplicationContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
                                    mAdapter.updateVideoItem(mSelectVideoPosition, imageRadioResultEvent.getResult().getOriginalPath());
                                }
                            })
                            .open();
                    break;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == mOpenJumpSelectCode && data != null) {
                String jumpSelectValue = data.getStringExtra("JumpSelectValue");
                mAdapter.updateJumpItem(mJumpSelectFormPosition, jumpSelectValue);
            }
        }
    }

    private void initData() {
        // 普通输入框一
        CurrencyFormBean inputForm = new CurrencyFormBean();
        inputForm.setLabel("输入框布局一");
        inputForm.setHint("请输入文字一");
        inputForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_INPUT);
        mDatas.add(inputForm);
        // 单选布局
        CurrencyFormBean radioForm = new CurrencyFormBean();
        radioForm.setLabel("单选布局");
        radioForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_RADIO_GROUP);
        List<KeyValueData> radioData = new ArrayList<>();
        radioData.add(new KeyValueData("0", "女", false));
        radioData.add(new KeyValueData("1", "男", false));
        radioForm.setDatas(radioData);
        mDatas.add(radioForm);
        // 多选布局
        CurrencyFormBean checkBoxForm = new CurrencyFormBean();
        checkBoxForm.setLabel("多选布局");
        checkBoxForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_CHECK_BOX);
        List<KeyValueData> checkBoxData = new ArrayList<>();
        checkBoxData.add(new KeyValueData("0", "毛将军", false));
        checkBoxData.add(new KeyValueData("1", "陈一凡", false));
        checkBoxData.add(new KeyValueData("2", "梁宏达", false));
        checkBoxData.add(new KeyValueData("3", "孔庆阳", false));
        checkBoxForm.setDatas(checkBoxData);
        mDatas.add(checkBoxForm);
        //  选择图片布局
        CurrencyFormBean selectImageForm = new CurrencyFormBean();
        selectImageForm.setLabel("选择图片布局");
        selectImageForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_IMAGE);
        mDatas.add(selectImageForm);
        // 跳转选择布局
        CurrencyFormBean checkForm = new CurrencyFormBean();
        checkForm.setLabel("跳转选择布局");
        checkForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_CHECK);
        checkForm.setCheckId(1);
        mDatas.add(checkForm);
        // 普通输入框二
        CurrencyFormBean inputFormTwo = new CurrencyFormBean();
        inputFormTwo.setLabel("输入框布局二");
        inputFormTwo.setHint("请输入文字二");
        inputFormTwo.setItemType(CurrencyFormAdapter.ITEM_TYPE_INPUT);
        mDatas.add(inputFormTwo);

        // 跳转选择布局
        CurrencyFormBean checkTwoForm = new CurrencyFormBean();
        checkTwoForm.setLabel("跳转选择布局二");
        checkTwoForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_CHECK);
        checkTwoForm.setCheckId(2);
        mDatas.add(checkTwoForm);

        // 选择视频布局
        CurrencyFormBean videoForm = new CurrencyFormBean();
        videoForm.setLabel("选择视频");
        videoForm.setItemType(CurrencyFormAdapter.ITEM_TYPE_VIDEO);
        mDatas.add(videoForm);

        mAdapter.setList(mDatas);
    }
}
