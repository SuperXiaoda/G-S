package com.juda.gs.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.juda.gs.R;
import com.juda.gs.activity.CustomViewActivity;
import com.juda.gs.activity.LayoutActivity;
import com.juda.gs.adapter.FunctionListAdapter;
import com.juda.gs.api.APIConstants;
import com.juda.gs.bean.Function;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

/**
 * @description: 功能页面
 * @author: CodingDa
 * @date : 2019/12/4 14:09
 */
public class FunctionFragment extends BaseFragment {

    // 功能列表
    @BindView(R.id.function_recycler)
    RecyclerView mFunctionRecycler;

    // 数据适配器
    private FunctionListAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_function;
    }

    @Override
    protected void init(View rootView) {
        mFunctionRecycler.hasFixedSize();
        mFunctionRecycler.setVerticalScrollBarEnabled(true);
        mFunctionRecycler.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mFunctionRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mFunctionRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(mActivity)
                        .colorResId(R.color.windowBackgroundGrey)
                        .sizeResId(R.dimen.DIP_0_5)
                        .showLastDivider()
                        .margin(30, 30)
                        .build());
        mAdapter = new FunctionListAdapter();
        mFunctionRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Function function = (Function) adapter.getData().get(position);
            Intent intent = new Intent();
            switch (function.getFunctionPosition()) {
                case APIConstants.FUNCTION_INDEX_CUSTOM_VIEW:
                    intent.setClass(mActivity, CustomViewActivity.class);
                    startActivity(intent);
                    break;
                case APIConstants.FUNCTION_INDEX_LAYOUT:
                    intent.setClass(mActivity, LayoutActivity.class);
                    startActivity(intent);
                    break;
            }
        }));
    }

    @Override
    protected void loadData() {
        ArrayList<Function> data = new ArrayList<>();
        data.add(new Function(getString(R.string.custom_view), APIConstants.FUNCTION_INDEX_CUSTOM_VIEW));
        data.add(new Function(getString(R.string.layout), APIConstants.FUNCTION_INDEX_LAYOUT));
        mAdapter.setNewData(data);
    }
}
