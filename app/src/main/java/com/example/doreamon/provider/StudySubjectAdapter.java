package com.example.doreamon.provider;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.example.doreamon.tree.FirstNode;
import com.example.doreamon.tree.FourthNode;
import com.example.doreamon.tree.SecondNode;
import com.example.doreamon.tree.ThirdNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StudySubjectAdapter extends BaseNodeAdapter {

    public StudySubjectAdapter() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
        addNodeProvider(new ThirdProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        } else if (node instanceof ThirdNode) {
            return 3;
        }
        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
}
