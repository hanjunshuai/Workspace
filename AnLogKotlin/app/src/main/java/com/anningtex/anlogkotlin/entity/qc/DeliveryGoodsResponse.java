package com.anningtex.anlogkotlin.entity.qc;

import java.util.List;

/**
 * @ClassName: DeliveryGoodsResponse
 * @Description: QC专属已排单和路上货物实体类
 * @Author: alvis
 * @CreateDate: 2020/5/13 13:52
 */
public class DeliveryGoodsResponse {

    /**
     * ID : 85
     * PlanDate : 2020-04-13 00:00:00.000
     * LoadDate : 2020-04-13 00:00:00.000
     * ToWarehouseID : 2
     * DeliveryNo : YD-AN200418A
     * IsDelivery : 0
     * IsDel : 0
     * DNID : 85
     * List_GoodsList : [{"ID":"292","DNID":"85","OrderNo":"SJ181206A","SupplierID":"250","BalesQuantity":"1","BalesIncomplete":"0","MetersPerBale":600,"MetersPerBaleUnitID":"1","Notes":"zz","IsDelivery":"0","IsDel":"0","DLGID":"292","MetersPerBaleUnitName":"码","MetersPerBaleUnitNameEN":"y","SupplierName":"临清三利纺织有限公司","SupplierNameShort":"三利"}]
     * Status : Load
     * Supplier : ["临清三利纺织有限公司"]
     */

    private String ID;
    private String PlanDate;
    private String LoadDate;
    private String ToWarehouseID;
    private String DeliveryNo;
    private String IsDelivery;
    private String IsDel;
    private String DNID;
    private String Status;
    private List<ListGoodsListBean> List_GoodsList;
    private List<String> Supplier;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String PlanDate) {
        this.PlanDate = PlanDate;
    }

    public String getLoadDate() {
        return LoadDate;
    }

    public void setLoadDate(String LoadDate) {
        this.LoadDate = LoadDate;
    }

    public String getToWarehouseID() {
        return ToWarehouseID;
    }

    public void setToWarehouseID(String ToWarehouseID) {
        this.ToWarehouseID = ToWarehouseID;
    }

    public String getDeliveryNo() {
        return DeliveryNo;
    }

    public void setDeliveryNo(String DeliveryNo) {
        this.DeliveryNo = DeliveryNo;
    }

    public String getIsDelivery() {
        return IsDelivery;
    }

    public void setIsDelivery(String IsDelivery) {
        this.IsDelivery = IsDelivery;
    }

    public String getIsDel() {
        return IsDel;
    }

    public void setIsDel(String IsDel) {
        this.IsDel = IsDel;
    }

    public String getDNID() {
        return DNID;
    }

    public void setDNID(String DNID) {
        this.DNID = DNID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public List<ListGoodsListBean> getList_GoodsList() {
        return List_GoodsList;
    }

    public void setList_GoodsList(List<ListGoodsListBean> List_GoodsList) {
        this.List_GoodsList = List_GoodsList;
    }

    public List<String> getSupplier() {
        return Supplier;
    }

    public void setSupplier(List<String> Supplier) {
        this.Supplier = Supplier;
    }

    public static class ListGoodsListBean {
        /**
         * ID : 292
         * DNID : 85
         * OrderNo : SJ181206A
         * SupplierID : 250
         * BalesQuantity : 1
         * BalesIncomplete : 0
         * MetersPerBale : 600
         * MetersPerBaleUnitID : 1
         * Notes : zz
         * IsDelivery : 0
         * IsDel : 0
         * DLGID : 292
         * MetersPerBaleUnitName : 码
         * MetersPerBaleUnitNameEN : y
         * SupplierName : 临清三利纺织有限公司
         * SupplierNameShort : 三利
         */

        private String ID;
        private String DNID;
        private String OrderNo;
        private String SupplierID;
        private String BalesQuantity;
        private String BalesIncomplete;
        private int MetersPerBale;
        private String MetersPerBaleUnitID;
        private String Notes;
        private String IsDelivery;
        private String IsDel;
        private String DLGID;
        private String MetersPerBaleUnitName;
        private String MetersPerBaleUnitNameEN;
        private String SupplierName;
        private String SupplierNameShort;
        private String OLID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getDNID() {
            return DNID;
        }

        public void setDNID(String DNID) {
            this.DNID = DNID;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String OrderNo) {
            this.OrderNo = OrderNo;
        }

        public String getSupplierID() {
            return SupplierID;
        }

        public void setSupplierID(String SupplierID) {
            this.SupplierID = SupplierID;
        }

        public String getBalesQuantity() {
            return BalesQuantity;
        }

        public void setBalesQuantity(String BalesQuantity) {
            this.BalesQuantity = BalesQuantity;
        }

        public String getBalesIncomplete() {
            return BalesIncomplete;
        }

        public void setBalesIncomplete(String BalesIncomplete) {
            this.BalesIncomplete = BalesIncomplete;
        }

        public int getMetersPerBale() {
            return MetersPerBale;
        }

        public void setMetersPerBale(int MetersPerBale) {
            this.MetersPerBale = MetersPerBale;
        }

        public String getMetersPerBaleUnitID() {
            return MetersPerBaleUnitID;
        }

        public void setMetersPerBaleUnitID(String MetersPerBaleUnitID) {
            this.MetersPerBaleUnitID = MetersPerBaleUnitID;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String Notes) {
            this.Notes = Notes;
        }

        public String getIsDelivery() {
            return IsDelivery;
        }

        public void setIsDelivery(String IsDelivery) {
            this.IsDelivery = IsDelivery;
        }

        public String getIsDel() {
            return IsDel;
        }

        public void setIsDel(String IsDel) {
            this.IsDel = IsDel;
        }

        public String getDLGID() {
            return DLGID;
        }

        public void setDLGID(String DLGID) {
            this.DLGID = DLGID;
        }

        public String getMetersPerBaleUnitName() {
            return MetersPerBaleUnitName;
        }

        public void setMetersPerBaleUnitName(String MetersPerBaleUnitName) {
            this.MetersPerBaleUnitName = MetersPerBaleUnitName;
        }

        public String getMetersPerBaleUnitNameEN() {
            return MetersPerBaleUnitNameEN;
        }

        public void setMetersPerBaleUnitNameEN(String MetersPerBaleUnitNameEN) {
            this.MetersPerBaleUnitNameEN = MetersPerBaleUnitNameEN;
        }

        public String getSupplierName() {
            return SupplierName;
        }

        public void setSupplierName(String SupplierName) {
            this.SupplierName = SupplierName;
        }

        public String getSupplierNameShort() {
            return SupplierNameShort;
        }

        public void setSupplierNameShort(String SupplierNameShort) {
            this.SupplierNameShort = SupplierNameShort;
        }

        public String getOLID() {
            return OLID;
        }

        public void setOLID(String OLID) {
            this.OLID = OLID;
        }
    }
}
