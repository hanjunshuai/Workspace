package com.anningtex.testgreendao.bean.log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName: PackNoEntity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 17:40
 */
@Entity
public class PackNoEntity {
    @Id
    Long id;

    Long cOrderId;
    String packNo;
    String orderName;


    @Override
    public String toString() {
        return "PackNoEntity{" +
                "packNo='" + packNo + '\'' +
                ", orderName='" + orderName + '\'' +
                '}';
    }

    @Generated(hash = 679879222)
    public PackNoEntity(Long id, Long cOrderId, String packNo, String orderName) {
        this.id = id;
        this.cOrderId = cOrderId;
        this.packNo = packNo;
        this.orderName = orderName;
    }
    @Generated(hash = 386902309)
    public PackNoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCOrderId() {
        return this.cOrderId;
    }
    public void setCOrderId(Long cOrderId) {
        this.cOrderId = cOrderId;
    }
    public String getPackNo() {
        return this.packNo;
    }
    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }
    public String getOrderName() {
        return this.orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

}
