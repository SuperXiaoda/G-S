package com.juda.gs.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.juda.gs.R;
import com.juda.gs.adapter.FunctionListAdapter;
import com.juda.gs.api.APIConstants;
import com.juda.gs.bean.Function;
import com.juda.gs.util.KeyBoardUtil;
import com.juda.gs.util.StringUtil;
import com.youngfeng.snake.annotations.EnableDragToClose;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @description: 自定义视图页面
 * @author: CodingDa
 * @date : 2019/12/4 15:30
 */
@EnableDragToClose
public class CustomViewActivity extends BaseActivity implements View.OnClickListener {

    // 返回按钮
    @BindView(R.id.back)
    AppCompatImageButton mBack;
    // 搜索输入框
    @BindView(R.id.search_src_text)
    AppCompatEditText mSearchSrcText;
    // 列表
    @BindView(R.id.custom_view_recycler)
    RecyclerView mCustomViewRecycler;

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


        mCustomViewRecycler.hasFixedSize();
        mCustomViewRecycler.setVerticalScrollBarEnabled(true);
        mCustomViewRecycler.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mCustomViewRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCustomViewRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .colorResId(R.color.windowBackgroundGrey)
                        .sizeResId(R.dimen.DIP_0_5)
                        .showLastDivider()
                        .margin(30, 30)
                        .build());
        mAdapter = new FunctionListAdapter();
        mCustomViewRecycler.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
        mSearchSrcText.setOnEditorActionListener(((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 判断是否存在文字
                if (StringUtil.isEmpty(mSearchSrcText.getText().toString())) {
                    mAdapter.setList(mData);
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
                    mAdapter.setList(mData);
                    KeyBoardUtil.closeKeyboard(mSearchSrcText, getApplicationContext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAdapter.setOnItemClickListener((((adapter, view, position) -> {
            Function function = (Function) adapter.getData().get(position);
            Intent intent = new Intent();
            switch (function.getFunctionPosition()) {
                case APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_DASHBOARD:
                    intent.setClass(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                    break;
                case APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_AUTOGRAPH:
                    intent.setClass(getApplicationContext(), AutographActivity.class);
                    startActivity(intent);
                    break;
                case APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_CURRENCY_FORM:
                    intent.setClass(getApplicationContext(), CurrencyFormActivity.class);
                    startActivity(intent);
                    break;
            }
        })));
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
        mData.add(new Function(getString(R.string.dashboard), APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_DASHBOARD));
        mData.add(new Function(getString(R.string.autograph), APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_AUTOGRAPH));
        mData.add(new Function(getString(R.string.currency_form), APIConstants.FUNCTION_INDEX_CUSTOM_VIEW_CURRENCY_FORM));
        mAdapter.setList(mData);
    }

    // 匹配搜索文字
    private void matcherSearchText(String searchText) {
        for (Function function : mData) {
            if (function.getName().contains(searchText)) {
                mSearchData.add(new Function(StringUtil.matcherSearchText(getResources().getColor(R.color.colorAccent), function.getName(), searchText), function.getFunctionPosition()));
            }
        }
        mAdapter.setList(mSearchData);
    }
}
