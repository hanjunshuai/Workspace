package com.anningtex.baserecyclerviewadapter.entity;

import com.anningtex.library.entity.SectionEntity;

/**
 * @ClassName: MySection
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 17:49
 */
public class MySection<T> extends SectionEntity {
    public boolean isMroe;

    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMroe = isMroe;
    }

    public MySection(T t) {
        super(t);
    }
}
