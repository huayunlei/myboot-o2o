package com.ihomefnt.o2o.intf.domain.homepage.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingSchemeRecord;
import com.ihomefnt.o2o.intf.domain.homecard.dto.VideoEntity;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.RecommendBoardResponse;
import com.ihomefnt.o2o.intf.domain.homepage.dto.ConventionalActionsVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.UserDetailStatus;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SelectSolutionInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionOrderInfo;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.SoftAndHardListResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * App首页接口返回数据
 *
 * @author jiangjun
 * @version 2.0, 2018-04-10 下午5:15
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class HomePageDataVo {


    //订单状态 1接触 2意向 3定金 4签约 5交付 6已完成 7已取消
    private Integer state;

    //首页常规操作区 按钮
    private List<ConventionalActionsVo> conventionalActionsList;

    //首页卡片信息
    private List<RecommendBoardResponse> recommendBoards;

    //首页方案信息(户型对应的方案列表)
    private SelectSolutionInfo selectSolutionInfo;

    //户型信息
    private String houseInfo = "";

    // 已选方案信息
    private SolutionOrderInfo solutionOrderInfo;

    // 交付清单(????? 是否用得到)
    private SoftAndHardListResponse aladdinOrderDetail;

    //施工进度
    private HardScheduleVo hardSchedule;

    //用户根据首页业务逻辑细分的状态
    private UserDetailStatus userDetailStatus;

    //楼盘小区服务记录
    private BuildingSchemeRecord buildingSchemeRecord;

    //代课下单默认示意图
    private String valetOrdersDefaultImg;

    //维修知会信息
    private String informServiceInfo;

    //欢迎语
    private String greet = "从房子到家，成就美好生活";

    //跳转支付页面所需要的参数 json字符串
    private String payParam;

    private boolean isAlreadyComment;

    //是否可以开始验收
    private boolean canBeAccept = false;

    //置家顾问电话
    private String adviserMobileNum;

    //期望交付日期
    private String exceptTime;//期望交付时间
    
    private boolean personalDemandFlag = false;
    
    private List<VideoEntity> videoList;//小艾课堂视频

    private String appVersion;
}
