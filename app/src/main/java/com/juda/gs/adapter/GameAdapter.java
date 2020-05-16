package com.juda.gs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.Function;
import com.juda.gs.util.StringUtil;

/**
 * @description: 游戏数据适配器
 * @author: CodingDa
 * @date : 2020/1/9 14:27
 */
public class GameAdapter extends BaseQuickAdapter<Function, BaseViewHolder> {

    public GameAdapter() {
        super(R.layout.item_function, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, Function item) {
        // 判断显示那种文字
        if (StringUtil.isEmpty(item.getName())) {
            holder.setText(R.id.name, item.getSpannableStringName());
        } else {
            holder.setText(R.id.name, item.getName());
        }
    }
}
