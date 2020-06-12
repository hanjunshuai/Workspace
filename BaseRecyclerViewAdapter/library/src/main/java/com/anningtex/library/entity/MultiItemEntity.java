package com.anningtex.library.entity;

/**
 * @ClassName: MultiItemEntity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/10 11:33
 */
public abstract class MultiItemEntity {
    protected int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
