package com.anningtex.baserecyclerviewadapter.entity;

import com.anningtex.library.entity.MultiItemEntity;

/**
 * @ClassName: MultipleItem
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/12 14:45
 */
public class MultipleItem extends MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MultipleItem{" +
                "content='" + content + '\'' +
                '}';
    }
}
