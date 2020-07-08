package com.anningtex.testgreendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName: IdCard
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 15:51
 */
@Entity
public class IdCard {
    @Id
    String userName;//用户名
    @Unique
    String idNo;//身份证号
    @Generated(hash = 1028827110)
    public IdCard(String userName, String idNo) {
        this.userName = userName;
        this.idNo = idNo;
    }
    @Generated(hash = 1500073048)
    public IdCard() {
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getIdNo() {
        return this.idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
