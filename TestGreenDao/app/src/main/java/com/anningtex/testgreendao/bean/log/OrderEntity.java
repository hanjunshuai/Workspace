package com.anningtex.testgreendao.bean.log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.anningtex.testgreendao.bean.DaoSession;

/**
 * @ClassName: OrderEntity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:39
 */
@Entity
public class OrderEntity {
    @Id
    Long id;

    Long orderId;
    @NotNull
    String orderName;

    @ToMany(referencedJoinProperty = "cOrderId")
    List<PackNoEntity> mPackNoEntities;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 395471210)
    private transient OrderEntityDao myDao;

    @Generated(hash = 358853092)

    public OrderEntity(Long id, Long orderId, @NotNull String orderName) {
        this.id = id;
        this.orderId = orderId;
        this.orderName = orderName;
    }

    @Generated(hash = 959685193)
    public OrderEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return this.orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1376549752)
    public List<PackNoEntity> getMPackNoEntities() {
        if (mPackNoEntities == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PackNoEntityDao targetDao = daoSession.getPackNoEntityDao();
            List<PackNoEntity> mPackNoEntitiesNew = targetDao
                    ._queryOrderEntity_MPackNoEntities(id);
            synchronized (this) {
                if (mPackNoEntities == null) {
                    mPackNoEntities = mPackNoEntitiesNew;
                }
            }
        }
        return mPackNoEntities;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1340585133)
    public synchronized void resetMPackNoEntities() {
        mPackNoEntities = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1571399466)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderEntityDao() : null;
    }

}
