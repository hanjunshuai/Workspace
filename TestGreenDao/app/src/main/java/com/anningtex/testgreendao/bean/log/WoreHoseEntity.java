package com.anningtex.testgreendao.bean.log;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.anningtex.testgreendao.bean.DaoSession;

/**
 * @ClassName: WoreHoseEntity
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 16:36
 */
@Entity
public class WoreHoseEntity {
    @Id
    Long id;
    String wereHoseName;
    @ToMany(referencedJoinProperty = "orderId")
    List<OrderEntity> creditCardsList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1052810750)
    private transient WoreHoseEntityDao myDao;
    @Generated(hash = 170917811)
    public WoreHoseEntity(Long id, String wereHoseName) {
        this.id = id;
        this.wereHoseName = wereHoseName;
    }
    @Generated(hash = 379665657)
    public WoreHoseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWereHoseName() {
        return this.wereHoseName;
    }
    public void setWereHoseName(String wereHoseName) {
        this.wereHoseName = wereHoseName;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 539761045)
    public List<OrderEntity> getCreditCardsList() {
        if (creditCardsList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrderEntityDao targetDao = daoSession.getOrderEntityDao();
            List<OrderEntity> creditCardsListNew = targetDao
                    ._queryWoreHoseEntity_CreditCardsList(id);
            synchronized (this) {
                if (creditCardsList == null) {
                    creditCardsList = creditCardsListNew;
                }
            }
        }
        return creditCardsList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 441911208)
    public synchronized void resetCreditCardsList() {
        creditCardsList = null;
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
    @Generated(hash = 510906676)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWoreHoseEntityDao() : null;
    }

}
