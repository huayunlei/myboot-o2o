package com.ihomefnt.o2o.service.proxy.demo;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.dms.vo.DemoDeliveryRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.demo.DemoProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoProxyImpl implements DemoProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public String scheduleDate(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.SCHEDULE_DATE, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }
    }

    @Override
    public String softScheduleDate(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.SOFT_SCHEDULE_DATE, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }
    }

    @Override
    public String startSchedule(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.START_SCHEDULE, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }
    }

    @Override
    public String finishHydropower(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_HYDROPOWER, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }
    }

    @Override
    public String finishWooden(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_WOODEN, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }
    }

    @Override
    public String finishHard(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_HARD, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishPurchase(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_PURCHASE, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishLogistic(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_LOGISTIC, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishInstall(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_INSTALL, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishSoftCheck(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_SOFT_CHECK, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishCheck(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_CHECK, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }

    @Override
    public String finishFastCheck(DemoDeliveryRequestVo request) {
        try {
            ResponseVo<?> responseVo = strongSercviceCaller.post(
                    AladdinDmsServiceNameConstants.FINISH_FAST_CHECK, request, ResponseVo.class);
            if (responseVo == null) {
                return "操作失败";
            }
            return responseVo.getMsg();
        } catch (Exception e) {
            return "操作失败2";
        }    }
}
