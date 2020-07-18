package com.anningtex.anlogkotlin.entity

import java.io.Serializable

/**
 * @ClassName: LoginResponse
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/4/23 16:36
 */
class LoginResponse : Serializable {
    /**
     * menu : {"module":[{"name":"合同","id":8},{"name":"订单跟踪表","id":40},{"name":"报表","id":41},{"name":"布产单列表","id":10}],"urlList":[{"url":"/api/v1/getOrderNo","name":"获取布产单号"},{"url":"/api/v1/placeOrderForm","name":"提交布产单form"},{"url":"/api/v1/getInfoOrderListTheLast","name":"获取推荐列表"},{"url":"/api/v1/getUnitList","name":"获取布产单单位"},{"url":"/api/v1/getCompanyList","name":"获取布产单公司列表"},{"url":"/api/v1/getTexInfoList","name":"获取布产单品名列表"},{"url":"/api/v1/getTexInNameList","name":"获取布产单中文品名列表"},{"url":"/api/v1/getModelInfoList","name":"获取布产单型号列表"},{"url":"/api/v1/placeColorOrderForm","name":"提交色布布产单form"},{"url":"/api/v1/set_trademark","name":"布产单设置商标图片接口"},{"url":"/api/v1/search_order","name":"获取布产单列表"},{"url":"/api/v1/getOrderForm","name":"获取布产单form"},{"url":"/api/v1/getBuyerGroupList","name":"获取布产单采购组列表"},{"url":"/api/v1/get_trademark_list","name":"布产单获取商标列表"},{"url":"/api/v1/del_trademark","name":"删除商标接口"},{"url":"/api/v1/getContractForm","name":"合同form"},{"url":"/api/v1/getContractTexInName","name":"合同中文品名列表"},{"url":"/api/v1/getContractModelName","name":"合同规格列表"},{"url":"/api/v1/getContractBuyerName","name":"合同买方列表"},{"url":"/api/v1/getContractToSupplierInfo","name":"合同发货到详情"},{"url":"/api/v1/getContractProvisionInfoTheLast","name":"合同最后一次条款详情"},{"url":"/api/v1/saveContractForm","name":"保存修改合同form"},{"url":"/api/v1/getContractList","name":"获取合同列表"},{"url":"/api/v1/getMakeContract","name":"做合同界面数据"},{"url":"/api/v1/getContractSupplierName","name":"合同卖方列表"},{"url":"/api/v1/delContract","name":"删除合同"},{"url":"/api/v1/delItem","name":"删除条目"},{"url":"/api/v1/wordContract","name":"导出合同word"},{"url":"/api/v1/wordWarehouse","name":"导出进仓通知单"},{"url":"/api/v1/goodsReceipt","name":"导出货物签收单"},{"url":"/api/v1/batchGenerate","name":"单个合同文件批量导出"},{"url":"/api/v1/download_all","name":"单个布产单所有合同文件导出"},{"url":"/api/v1/upload_order_trademark","name":"上传商标图片"},{"url":"/api/v1/url_delete","name":"删除url"},{"url":"/api/v1/url_update","name":"更新url"},{"url":"/api/v1/url_add","name":"新增url"},{"url":"/api/v1/user_add","name":"添加用户"},{"url":"/api/v1/change_status","name":"禁止用户登录"},{"url":"/api/v1/allocate_roles","name":"用户分配角色"},{"url":"/api/v1/url_group_add","name":"新增权限资源组"},{"url":"/api/v1/url_group_update","name":"修改权限资源组"},{"url":"/api/v1/url_group_delete","name":"删除权限资源组"},{"url":"/api/v1/getSupplierList","name":"获取生产厂家列表"},{"url":"/api/v1/getTrackUnitList","name":"获取订单跟踪表单位列表"},{"url":"/api/v1/getOrderList","name":"获取布产单列表"},{"url":"/api/v1/getOrderRemarks","name":"获取布产单备注列表"},{"url":"/api/v1/trackRemark","name":"订单跟踪表写备注"},{"url":"/api/v1/cancleTrackRemark","name":"订单跟踪表撤回备注"},{"url":"/api/v1/trackScore","name":"订单跟踪表打分"},{"url":"/api/v1/getProductFinishedList","name":"获取生产完成清单"},{"url":"/api/v1/getTruckInGoodsList","name":"获取入仓清单"},{"url":"/api/v1/getContainerInfoGoodsList","name":"获取装箱清单"},{"url":"/api/v1/changeUrgent","name":"订单跟踪表修改优先级"},{"url":"/api/v1/getCodeWord","name":"生成跟踪码word"},{"url":"/api/v1/dataEntry","name":"录入细码单数据"},{"url":"/api/v1/getTrackPackageInfo","name":"获取跟踪码包号详情信息"},{"url":"/api/v1/getTrackPackage","name":"app扫码获取一包详情"},{"url":"/api/v1/getQcUserList","name":"获取QC/跟单用户"},{"url":"/api/v1/insertTrackUser","name":"录入跟单用户"},{"url":"/api/v1/insertQcUser","name":"录入QC用户"},{"url":"/api/v1/insertExpireDate","name":"设置计划交货期"},{"url":"/api/v1/insertSupplierInContract","name":"添加坯布厂/印染厂"},{"url":"/api/v1/getTrackQcInfo","name":"获取qc详情"},{"url":"/api/v1/insertQcRecord","name":"添加QC数据"},{"url":"/api/v1/getQbalesFinishedList","name":"获取已生产完成打包详情列表"},{"url":"/api/v1/saveInCart","name":"加入购物车"},{"url":"/api/v1/getCartInfo","name":"获取购物车列表"},{"url":"/api/v1/changeInCartToPreLoad","name":"排单"},{"url":"/api/v1/getDeliveryPreloadList","name":"获取已排单(等待装车)列表"},{"url":"/api/v1/changePreLoadToOnWay","name":"装车"},{"url":"/api/v1/getDeliveryOnwayList","name":"获取路上货物列表"},{"url":"/api/v1/changeOnWayToTruckIn","name":"入仓"},{"url":"/api/v1/editDeliveryNo","name":"修改入仓通知单号"},{"url":"/api/v1/getOrderDesignPic","name":"获取订单跟踪设计图列表"},{"url":"/api/v1/getOrderFinshedPic","name":"获取订单跟踪成品图列表"},{"url":"/api/v1/uploadTrackFiles","name":"订单跟踪表上传文件"},{"url":"/api/v1/deleteTrackFiles","name":"订单跟踪表删除文件信息"},{"url":"/api/v1/getUploadFileInfo","name":"获取上传文件信息"},{"url":"/api/v1/insertFlagDate","name":"设置Flag交货期"},{"url":"/api/v1/getReport01","name":"获取报表1基础数据"},{"url":"/api/v1/getReport02","name":"获取报表2基础数据"},{"url":"/api/v1/getReport03","name":"获取报表3基础数据"},{"url":"/api/v1/getReport08","name":"获取报表8基础数据"},{"url":"/api/v1/getReport11","name":"获取报表11基础数据"},{"url":"/api/v1/getReport12","name":"获取报表12基础数据"},{"url":"/api/v1/getReportCountryList","name":"报表获取国别列表"},{"url":"/api/v1/getReportTexInfoList","name":"报表获取品名列表"},{"url":"/api/v1/getClientList","name":"报表1获取客户列表"},{"url":"/api/v1/getFlowerFinshed","name":"报表8获取成品图数据"},{"url":"/api/v1/getContainerSchedule","name":"报表11获取装箱单"},{"url":"/api/v1/getThinCode","name":"报表11获取细码单"},{"url":"/api/v1/getForwarderList","name":"报表12获取转运商列表"},{"url":"/api/v1/getLogiTitle","name":"报表12获取状态标题列表"},{"url":"/api/v1/setForwarder","name":"报表12设置/修改转运商"},{"url":"/api/v1/setStatus","name":"报表12设置转运状态"},{"url":"/api/v1/getContainerLogInfoList","name":"报表12获取集装箱状态列表"},{"url":"/api/v1/getMskInfo","name":"报表11获取MSK物流数据"},{"url":"/api/v1/getReport15","name":"获取报表15基础数据"},{"url":"/api/v1/getReport09","name":"获取报表9基础数据"},{"url":"/api/v1/change_password","name":"修改密码"}]}
     * IsSimplePWD : 1
     */
    var role: String? = null
    var menu: MenuBean? = null
    var isSimplePWD = 0

    class MenuBean : Serializable {
        var module: List<ModuleBean>? = null
        var urlList: List<UrlListBean>? = null

        class ModuleBean : Serializable {
            /**
             * name : 合同
             * id : 8
             */
            var name: String? = null
            var id = 0
            var logo = 0
            override fun toString(): String {
                return "ModuleBean(name=$name, id=$id, logo=$logo)"
            }


        }

        class UrlListBean : Serializable {
            /**
             * url : /api/v1/getOrderNo
             * name : 获取布产单号
             */
            var url: String? = null
            var name: String? = null
            override fun toString(): String {
                return "UrlListBean(url=$url, name=$name)"
            }


        }
    }
}