package com.example.doreamon.tree;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FourthNode extends BaseNode {
    private String title;

    public FourthNode(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
