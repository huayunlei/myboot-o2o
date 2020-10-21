package com.ihomefnt.o2o.service.service.vote;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.collect.Lists;
import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.common.http.PageInfo;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import com.ihomefnt.o2o.intf.domain.dna.dto.DnaBrowseRecordDto;
import com.ihomefnt.o2o.intf.domain.main.vo.SolutionInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.AladdinUserHouseInfo;
import com.ihomefnt.o2o.intf.domain.program.dto.MasterOrderSimpleInfo;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteRecordDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteWorksDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.DnaVoteTopResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.UserVoteRecordResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.VoteUserCacheDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.WinnerListResponse;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.dna.DecorationQuotationProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.vote.DnaVoteProxy;
import com.ihomefnt.o2o.intf.service.vote.DnaVoteService;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * dna投票
 *
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
@Slf4j
@Service
public class DnaVoteServiceImpl implements DnaVoteService {

    @Autowired
    private DnaVoteProxy dnaVoteProxy;

    @Autowired
    private DecorationQuotationProxy decorationQuotationProxy;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.url}", autoRefreshed = true)
    private String dnaVoteHomePageBanner;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.startTime}", autoRefreshed = true)
    private String dnaVoteHomePageBannerStartTime;

    @NacosValue(value = "${xishe.dna.vote.homePage.banner.endTime}", autoRefreshed = true)
    private String dnaVoteHomePageBannerEndTime;

    @NacosValue(value = "${xishe.dna.vote.deliverTime}", autoRefreshed = true)
    private String xisheDnaVoteDeliverTime;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProgramOrderProxyImpl orderProxy;

    @Override
    public PageInfo<DnaVoteWorksDto> queryVoteDnaList(Integer userId, String mobile, Integer pageNum, Integer pageSize) {
        PageInfo<DnaVoteWorksDto> dnaVoteWorksDtoPageInfo = dnaVoteProxy.queryVoteDnaList(userId, mobile, pageNum, pageSize);
        if (dnaVoteWorksDtoPageInfo != null && CollectionUtils.isNotEmpty(dnaVoteWorksDtoPageInfo.getList())) {
            dnaVoteWorksDtoPageInfo.getList().parallelStream().forEach(dnaVoteWorksDto -> dnaVoteWorksDto.setPictureUrl(AliImageUtil.imageCompress(dnaVoteWorksDto.getPictureUrl(), 1, 750, ImageConstant.SIZE_SMALL)));
            List<Integer> dnaIdList = dnaVoteWorksDtoPageInfo.getList().stream().map(dnaVoteWorksDto -> dnaVoteWorksDto.getId()).collect(Collectors.toList());
            if (userId != null) {
                List<DnaVoteRecordDto> list = dnaVoteProxy.queryVoteRecordByUserIdAndDnaIdList(userId, dnaIdList);
                if (CollectionUtils.isNotEmpty(list)) {
                    List<Integer> userDnaList = list.stream().map(dnaVoteRecordDto -> dnaVoteRecordDto.getDnaId()).collect(Collectors.toList());
                    dnaVoteWorksDtoPageInfo.getList().forEach(dnaVoteWorksDto -> {
                        if (userDnaList.contains(dnaVoteWorksDto.getId())) {
                            dnaVoteWorksDto.setHasVote(Boolean.TRUE);
                        }
                    });
                }
            }
        }
        return dnaVoteWorksDtoPageInfo;
    }

    @Override
    public DnaVoteWorksDto queryVoteDnaSimpleInfo(Integer dnaId, Integer userId) {
        DnaVoteWorksDto dnaVoteWorksDto = dnaVoteProxy.queryVoteDnaSimpleInfo(dnaId);
        if (dnaVoteWorksDto != null) {
            List<DnaVoteRecordDto> list = dnaVoteProxy.queryVoteRecordByUserIdAndDnaIdList(userId, Lists.newArrayList(dnaId));
            if (CollectionUtils.isNotEmpty(list)) {
                dnaVoteWorksDto.setHasVote(Boolean.TRUE);
            }
        }
        return dnaVoteWorksDto;
    }

    //13：接触阶段，14：意向阶段，15：定金阶段，16：签约阶段，17：交付中，2：已完成，3：已取消
    @Override
    public Boolean handlerUserOpenRule(Integer userId) {


        String qualificationCache = RedisUtil.get(RedisKey.DnaVote.XISHE_DNA_VOTE_QUALIFICATION + userId);
        if (StringUtils.isNotBlank(qualificationCache)) {
            return true;
        }
        List<AladdinUserHouseInfo> houseInfoResponseVos = productProgramProxy.queryAladdinUserHouseInfo(userId);
        if (CollectionUtils.isNotEmpty(houseInfoResponseVos)) {
            List<Integer> houseIdList = houseInfoResponseVos.stream().map(aladdinUserHouseInfo -> aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()).collect(Collectors.toList());
            List<MasterOrderSimpleInfo> masterOrderSimpleInfos = productProgramProxy.queryMasterOrderIdsByHouseIds(houseIdList);
            if (CollectionUtils.isNotEmpty(masterOrderSimpleInfos)) {
                Map<Integer, MasterOrderSimpleInfo> collectMap = masterOrderSimpleInfos.stream().collect(Collectors.toMap(o -> o.getHouseId(), o -> o));
                houseInfoResponseVos.forEach(aladdinUserHouseInfo -> aladdinUserHouseInfo.setOrderStatus(collectMap.get(aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()) == null ? 0 : collectMap.get(aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()).getMasterOrderStatus()).setOrderId(collectMap.get(aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()) == null ? 0 : collectMap.get(aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()).getMasterOrderNum()));
                houseInfoResponseVos.sort(Comparator.comparingInt(AladdinUserHouseInfo::getOrderStatus));
                for (AladdinUserHouseInfo aladdinUserHouseInfo : houseInfoResponseVos) {
                    try {
                        if (aladdinUserHouseInfo.getOrderStatus().equals(14) || aladdinUserHouseInfo.getOrderStatus().equals(15)) {
                        } else if (aladdinUserHouseInfo.getOrderStatus().equals(16)) {
                            SolutionInfo solutionInfo = orderProxy.querySolutionInfo(aladdinUserHouseInfo.getOrderId());
                            if (solutionInfo != null && solutionInfo.getSolutionId()!=null && !solutionInfo.getSolutionId().equals(3232)) {
                                continue;
                            }
                        } else {
                            continue;
                        }
                        if (aladdinUserHouseInfo != null && StringUtils.isNotBlank(aladdinUserHouseInfo.getHousePropertyInfoExtResultDto().getDeliverTime())) {
                            String deliverTime = aladdinUserHouseInfo.getHousePropertyInfoExtResultDto().getDeliverTime();
                            Date deliverDate = DateUtils.parseDate(deliverTime, "yyyy-MM-dd");
                            Date deliverRuleDate = DateUtils.parseDate(xisheDnaVoteDeliverTime, "yyyy-MM-dd HH:mm:ss");
                            if (deliverDate.getTime() >= deliverRuleDate.getTime()) {
                                RedisUtil.set(RedisKey.DnaVote.XISHE_DNA_VOTE_QUALIFICATION + userId, JSON.toJSONString(new VoteUserCacheDto().setUserId(userId).setHouseId(aladdinUserHouseInfo.getHousePropertyInfoResultDto().getCustomerHouseId()).setOrderId(aladdinUserHouseInfo.getOrderId())), 0);
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        log.error("dna vote enter o2o-exception , more info :", e);
                    }

                }
            }
        }
        return false;
    }

    @Override
    public WinnerListResponse queryWinnerList() {
        WinnerListResponse winnerListResponse;
        String json = RedisUtil.get("DNA_VOTE_WINNER_LIST_KEY");
        if (StringUtils.isNotBlank(json)) {
            winnerListResponse = JSON.parseObject(json, WinnerListResponse.class);
        } else {
            winnerListResponse = dnaVoteProxy.queryWinnerList();
            if (winnerListResponse != null) {
                RedisUtil.set("DNA_VOTE_WINNER_LIST_KEY", JSON.toJSONString(winnerListResponse), 600);
            }
        }
        return winnerListResponse;
    }

    @Override
    public DnaVoteWorksDto queryVoteDnaInfo(Integer userId, Integer dnaId) {
        DnaVoteWorksDto dnaVoteWorksDto = dnaVoteProxy.queryVoteDnaInfo(dnaId);
        if (dnaVoteWorksDto != null && userId != null) {
            dnaVoteWorksDto.setPictureUrl(AliImageUtil.imageCompress(dnaVoteWorksDto.getPictureUrl(), 1, 750, ImageConstant.SIZE_MIDDLE));
            UserVoteRecordResponse userVoteRecordResponse = dnaVoteProxy.queryUserVoteRecord(userId);
            if (userVoteRecordResponse != null && CollectionUtils.isNotEmpty(userVoteRecordResponse.getRecordList()) && userVoteRecordResponse.getRecordList().stream().map(dnaVoteRecordDto -> dnaVoteRecordDto.getDnaId()).collect(Collectors.toList()).contains(dnaId)) {
                dnaVoteWorksDto.setHasVote(Boolean.TRUE);
            }
        }
        return dnaVoteWorksDto;
    }

    @Override
    public DnaVoteWorksDto doVote(Integer userId, Integer dnaId, String mobile) {

        try {
            if (!this.handlerUserOpenRule(userId)) {
                throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.NO_QUALIFICATION);
            }
            DnaVoteWorksDto dnaVoteWorksDto = dnaVoteProxy.doVote(userId, dnaId, mobile);
            if (dnaVoteWorksDto == null) {
                throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.FAILED);
            }
            if (StringUtils.isNotBlank(dnaVoteWorksDto.getErrorMessage())) {
                throw new BusinessException(HttpResponseCode.FAILED, dnaVoteWorksDto.getErrorMessage());
            }
            return dnaVoteWorksDto.setHasVote(Boolean.TRUE);
        } catch (Exception e) {
            log.error("dna投票出现异常,error:{}", e);
            throw new BusinessException(HttpResponseCode.FAILED, e.getMessage());
        }
    }

    @Override
    public DnaVoteTopResponse queryDnaVoteTopData(Integer osTyoe) {
        return dnaVoteProxy.queryDnaVoteTopData();
    }

    @Override
    public PageInfo<DnaVoteWorksDto> queryDnaRanking(PageRequest request) {
        PageInfo<DnaVoteWorksDto> dnaVoteWorksDtoPageInfo = dnaVoteProxy.queryDnaRanking(request);
        if (dnaVoteWorksDtoPageInfo != null && CollectionUtils.isNotEmpty(dnaVoteWorksDtoPageInfo.getList())) {
            dnaVoteWorksDtoPageInfo.getList().parallelStream().forEach(dnaVoteWorksDto -> dnaVoteWorksDto.setPictureUrl(AliImageUtil.imageCompress(dnaVoteWorksDto.getPictureUrl(), 1, 750, ImageConstant.SIZE_SMALL)));
        }
        return dnaVoteWorksDtoPageInfo;
    }

    @Override
    public UserVoteRecordResponse queryUserVoteRecord(Integer userId) {
        return dnaVoteProxy.queryUserVoteRecord(userId);
    }


}
