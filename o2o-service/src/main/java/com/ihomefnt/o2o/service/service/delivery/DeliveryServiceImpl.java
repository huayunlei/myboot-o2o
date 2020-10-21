package com.ihomefnt.o2o.service.service.delivery;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.constant.FamilyOrderStatusEnum;
import com.ihomefnt.o2o.intf.domain.delivery.dto.SolfSimpleInfo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.GetDeviceListResultVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.delivery.DeliveryProxy;
import com.ihomefnt.o2o.intf.proxy.lechange.HbmsProxy;
import com.ihomefnt.o2o.intf.service.delivery.DeliveryService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryProxy deliveryProxy;

    @Autowired
    private ProductProgramOrderProxyImpl productProgramOrderProxy;

    @Autowired
    private HbmsProxy hbmsProxy;

    @NacosValue(value = "${delivery.notice}", autoRefreshed = true)
    private String deliveryNotice;

    /**
     * 交付单信息接口
     *
     * @param orderId
     * @return
     */
    @Override
    public HardScheduleVo getSchedule(String orderId) {
        return deliveryProxy.getSchedule(orderId);
    }

    /**
     * 硬装进度接口
     *
     * @param orderId
     * @return
     */
    @Override
    public HardDetailVo getHardDetail(String orderId, String appVersion) {
        HardDetailVo hardDetail = deliveryProxy.getHardDetail(appVersion, orderId);//查询硬装进度
        if (hardDetail == null) {
            return null;
        }
        AppOrderBaseInfoResponseVo appOrderBaseInfoResponseVo = productProgramOrderProxy.queryAppOrderBaseInfo(Integer.parseInt(orderId));//查询订单信息，获取软装数
        if (StringUtils.isNotBlank(deliveryNotice) && appOrderBaseInfoResponseVo.getOrderStatus().equals(FamilyOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus())) {
            NoticeBoardDto noticeBoardDto = JSON.parseObject(deliveryNotice, NoticeBoardDto.class);
            if (noticeBoardDto.getShow()) {
                hardDetail.setNoticeBoard(noticeBoardDto);
            }
        }
        if (!VersionUtil.mustUpdate(appVersion, "5.3.2")) {
            List<GetDeviceListResultVo> cameraList = hbmsProxy.getDeviceByOrderId(orderId);
            GetDeviceListResultVo camera = null;
            if (CollectionUtils.isNotEmpty(cameraList)) {
                for (GetDeviceListResultVo resultVo : cameraList) {
                    if (resultVo.getBrand() == 2) {
                        //有摩看摄像头优先展示摩看摄像头
                        camera = resultVo;
                        break;
                    }
                }
                if (camera == null) {
                    //没有摩看摄像头就取第一个
                    camera = cameraList.get(0);
                }
                if (camera != null) {
                    CameraDetailVo cameraDetailVo = new CameraDetailVo();
                    cameraDetailVo.setUrl(camera.getNewSnapshot() == null ? camera.getUrl() : camera.getNewSnapshot());
                    cameraDetailVo.setBrand(camera.getBrand());
                    cameraDetailVo.setMokanToken(camera.getAccessToken());
                    cameraDetailVo.setBindStatus(0);
                    cameraDetailVo.setCameraSn(camera.getCameraSn());
                    hardDetail.setCameraDetailVo(cameraDetailVo);
                }
            }
        }
        if (appOrderBaseInfoResponseVo == null) {
            return hardDetail;
        }
        SolfSimpleInfo solfSimpleInfo = new SolfSimpleInfo();
        if (appOrderBaseInfoResponseVo.getTotalProductCount() != null) {
            solfSimpleInfo.setSum(appOrderBaseInfoResponseVo.getTotalProductCount());//软装总数
        }
        if (appOrderBaseInfoResponseVo.getCompleteDelivery() != null) {
            solfSimpleInfo.setFinished(appOrderBaseInfoResponseVo.getCompleteDelivery());//软装送货完成数
        }
        hardDetail.setSolfSimpleInfo(solfSimpleInfo);
        return hardDetail;
    }

    /**
     * 节点详细信息
     *
     * @param request
     * @return
     * @deprecated 已废弃
     */
    @Override
    @Deprecated
    public NodeDetailVo getNodeDetail(GetNodeDetailRequest request) {
        NodeDetailVo nodeDetail = deliveryProxy.getNodeDetail(request);
        if (nodeDetail != null) {
            List<NodeProcessRecordVo> nodeProcessRecordVos = nodeDetail.getNodeProcessRecordVos();
            if (CollectionUtils.isNotEmpty(nodeProcessRecordVos)) {
                nodeProcessRecordVos.forEach(nodeProcessRecordVo -> {
                    List<PicRecordDetailVo> picRecordDetailVos = nodeProcessRecordVo.getPicRecordDetailVos();
                    if (CollectionUtils.isNotEmpty(picRecordDetailVos)) {
                        picRecordDetailVos.forEach(picRecordDetailVo -> {
                            List<String> pics = picRecordDetailVo.getPics();
                            if (CollectionUtils.isNotEmpty(pics)) {
                                picRecordDetailVo.setPics(pics.stream().map(s -> QiniuImageUtils.rmArgsAndCompressImage(s, request.getWidth(), -1)).collect(Collectors.toList()));
                            }
                        });
                    }
                });
            }
        }
        return nodeDetail;
    }

    /**
     * 客户节点验收接口
     *
     * @param request
     */
    @Override
    public void confirmNode(ConfirmNodeRequest request) {

        boolean result = deliveryProxy.confirmNode(request);
        if (!result) {
            throw new BusinessException("提交失败！");
        }
    }

    /**
     * 完整计划日历接口
     *
     * @param orderId
     * @return
     */
    @Override
    public GetHardScheduleVo getHardSchedule(String orderId) {
        GetHardScheduleVo hardSchedule = deliveryProxy.getHardSchedule(orderId);
        if (hardSchedule != null && CollectionUtils.isNotEmpty(hardSchedule.getHolidays())) {
            hardSchedule.setHolidaysResponse(new NodeScheduleListResponse().setHolidays(hardSchedule.getHolidays()).setNodeName("休").setNodeDesc("休假"));
        } else {
            hardSchedule.setHolidaysResponse(new NodeScheduleListResponse().setHolidays(Lists.newArrayList()).setNodeName("").setNodeDesc(""));
        }
        return hardSchedule;
    }

    /**
     * 整体验收接口
     *
     * @param request
     */
    @Override
    public void finalCheck(FinalCheckParamRequest request) {
        boolean result = deliveryProxy.finalCheck(request);
        if (!result) {
            throw new BusinessException("提交失败！");
        }
    }


    @Override
    public NodeDetailVo getNodeDetailV2(GetNodeDetailRequest request) {
        NodeDetailVo nodeDetailVo = deliveryProxy.getNodeDetailV2(request.getNodeId(), request.getOrderId());
        if (nodeDetailVo != null && CollectionUtils.isNotEmpty(nodeDetailVo.getCheckNodeVos())) {
            for (NodeProcessRecordVoV2 checkNodeVo : nodeDetailVo.getCheckNodeVos()) {
                if (CollectionUtils.isNotEmpty(checkNodeVo.getNodeProcessRecordVos())) {
                    for (NodeProcessRecordVoV2.NodeProcessRecordVos nodeProcessRecordVo : checkNodeVo.getNodeProcessRecordVos()) {
                        if (CollectionUtils.isNotEmpty(nodeProcessRecordVo.getPicRecordDetailVos())) {
                            for (NodeProcessRecordVoV2.NodeProcessRecordVos.PicRecordDetailVos picRecordDetailVo : nodeProcessRecordVo.getPicRecordDetailVos()) {
                                if (CollectionUtils.isNotEmpty(picRecordDetailVo.getPics())) {
                                    picRecordDetailVo.setPics(picRecordDetailVo.getPics().stream()
                                            .map(url -> AliImageUtil.imageCompress(url, request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL)).collect(Collectors.toList()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return nodeDetailVo;
    }
}