package com.juda.gs.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juda.gs.R;
import com.juda.gs.adapter.FunctionListAdapter;
import com.juda.gs.api.APIConstants;
import com.juda.gs.bean.Function;
import com.juda.gs.util.KeyBoardUtil;
import com.juda.gs.util.StringUtil;
import com.youngfeng.snake.annotations.EnableDragToClose;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Description: 布局页面
 * @author: CodingDa
 * @Date : 2020/4/24 10:00
 */
@EnableDragToClose
public class LayoutActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 搜索输入框
    @BindView(R.id.search_src_text)
    AppCompatEditText mSearchSrcText;
    // 列表
    @BindView(R.id.custom_view_recycler)
    RecyclerView mLayoutRecycler;

    // 数据
    private ArrayList<Function> mData = new ArrayList<>();
    // 搜索数据
    private ArrayList<Function> mSearchData = new ArrayList<>();
    // 数据适配器
    private FunctionListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_custom_view;
    }


    @Override
    protected void init() {
        mLayoutRecycler.hasFixedSize();
        mLayoutRecycler.setVerticalScrollBarEnabled(true);
        mLayoutRecycler.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mLayoutRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .colorResId(R.color.windowBackgroundGrey)
                        .sizeResId(R.dimen.DIP_0_5)
                        .showLastDivider()
                        .margin(30, 30)
                        .build());
        mAdapter = new FunctionListAdapter();
        mLayoutRecycler.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
        mSearchSrcText.setOnEditorActionListener(((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 判断是否存在文字
                if (StringUtil.isEmpty(mSearchSrcText.getText().toString())) {
                    mAdapter.setNewData(mData);
                } else {
                    mSearchData.clear();
                    matcherSearchText(mSearchSrcText.getText().toString());
                }
                return true;
            } else {
                return false;
            }
        }));

        mSearchSrcText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtil.isEmpty(mSearchSrcText.getText().toString())) {
                    mAdapter.setNewData(mData);
                    KeyBoardUtil.closeKeyboard(mSearchSrcText, getApplicationContext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Function function = (Function) adapter.getData().get(position);
            Intent intent = new Intent();
            switch (function.getFunctionPosition()) {
                case APIConstants.FUNCTION_INDEX_LAYOUT_CONSTRAIN_LAYOUT:
                    intent.setClass(getApplicationContext(), ConstrainLayoutActivity.class);
                    startActivity(intent);
                    break;
            }

        }));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    // 初始化数据
    private void initData() {
        mData.add(new Function(getString(R.string.constrain_layout), APIConstants.FUNCTION_INDEX_LAYOUT_CONSTRAIN_LAYOUT));
        mAdapter.setNewData(mData);
    }

    // 匹配搜索文字
    private void matcherSearchText(String searchText) {
        for (Function function : mData) {
            if (function.getName().contains(searchText)) {
                mSearchData.add(new Function(StringUtil.matcherSearchText(getResources().getColor(R.color.colorAccent), function.getName(), searchText), function.getFunctionPosition()));
            }
        }
        mAdapter.setNewData(mSearchData);
    }
}
