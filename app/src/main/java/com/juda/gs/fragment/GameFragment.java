package com.juda.gs.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juda.gs.R;
import com.juda.gs.activity.GameJigsawPuzzleActivity;
import com.juda.gs.adapter.GameAdapter;
import com.juda.gs.api.APIConstants;
import com.juda.gs.bean.Function;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @description: 游戏页面
 * @author: CodingDa
 * @date : 2019/12/4 14:12
 */
public class GameFragment extends BaseFragment {

    // 游戏列表
    @BindView(R.id.game_recycler)
    RecyclerView mGameRecycler;
    // 游戏数据适配器
    private GameAdapter mAdapter;


    @Override
    protected int getContentView() {
        return R.layout.fragment_game;
    }

    @Override
    protected void init(View rootView) {
        mGameRecycler.hasFixedSize();
        mGameRecycler.setVerticalScrollBarEnabled(true);
        mGameRecycler.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mGameRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mGameRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(mActivity)
                        .colorResId(R.color.windowBackgroundGrey)
                        .sizeResId(R.dimen.DIP_0_5)
                        .showLastDivider()
                        .margin(30, 30)
                        .build());
        mAdapter = new GameAdapter();
        mGameRecycler.setAdapter(mAdapter);
        mGameRecycler.post(this::initData);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Function data = (Function) adapter.getData().get(position);
            Intent intent = new Intent();
            switch (data.getFunctionPosition()) {
                case APIConstants.GAME_INDEX_JIGSAW_PUZZLE:
                    intent.setClass(mActivity, GameJigsawPuzzleActivity.class);
                    startActivity(intent);

                    break;
            }
        });
    }

    // 初始化数据
    private void initData() {
        ArrayList<Function> data = new ArrayList<>();
        data.add(new Function(getString(R.string.jigsaw_puzzle), APIConstants.GAME_INDEX_JIGSAW_PUZZLE));
        mAdapter.setNewData(data);
    }


}
