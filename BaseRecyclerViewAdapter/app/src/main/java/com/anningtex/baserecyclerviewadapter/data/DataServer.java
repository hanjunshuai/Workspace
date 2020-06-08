package com.anningtex.baserecyclerviewadapter.data;

import com.anningtex.baserecyclerviewadapter.entity.MySection;
import com.anningtex.baserecyclerviewadapter.entity.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DataServer
 * @Description: 模拟数据
 * @Author: alvis
 * @CreateDate: 2020/6/8 11:20
 */
public class DataServer {
    public static List<MySection> getSampleData() {
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(true, "2016/4/8", true));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection(true, "2016/4/7", false));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection(true, "2016/4/6", false));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        list.add(new MySection("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460"));
        return list;
    }

    public static List<Status> getSampleData(int lenth) {
        List<Status> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Status status = new Status();
            status.setUserName("alvis" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText("Powerful and flexible RecyclerAdapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper");
            list.add(status);
        }
        return list;
    }

    public static List<Status> addData(List list, int dataSize) {
        for (int i = 0; i < dataSize; i++) {
            Status status = new Status();
            status.setUserName("alvis" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText("Powerful and flexible RecyclerAdapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper");
            list.add(status);
        }
        return list;
    }
}
