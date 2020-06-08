package com.anningtex.library.entity;

/**
 * @ClassName: SectionEntity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 17:42
 */
public abstract class SectionEntity<T> {
    public boolean isHeader;
    public T t;
    public String header;

    public SectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
