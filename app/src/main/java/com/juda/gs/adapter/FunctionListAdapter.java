package com.juda.gs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juda.gs.R;
import com.juda.gs.bean.Function;
import com.juda.gs.util.StringUtil;


/**
 * @description: 功能列表Adapter
 * @author: CodingDa
 * @date : 2019/12/4 14:46
 */
public class FunctionListAdapter extends BaseQuickAdapter<Function, BaseViewHolder> {

    public FunctionListAdapter() {
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
