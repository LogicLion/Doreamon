package com.example.doreamon.provider;

import android.view.View;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.doreamon.R;
import com.example.doreamon.tree.SecondNode;

import org.jetbrains.annotations.NotNull;

public class SecondProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        SecondNode entity = (SecondNode) data;
        helper.setText(R.id.title, entity.getTitle());

        if (entity.isExpanded()) {
            View cl = helper.getView(R.id.cl);
            cl.setBackgroundResource(R.drawable.shape_b4f1ee_radius_16);
        } else {
            View cl = helper.getView(R.id.cl);
            cl.setBackgroundResource(R.drawable.shape_white_radius_10);
        }

    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;

        if (entity.isCanExpand()) {
            if (entity.isExpanded()) {
                getAdapter().collapse(position);
            } else {
                getAdapter().expandAndCollapseOther(position);
            }
        }


    }
}
