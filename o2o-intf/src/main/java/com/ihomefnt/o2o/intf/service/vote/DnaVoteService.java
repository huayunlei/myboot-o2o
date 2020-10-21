package com.ihomefnt.o2o.intf.service.vote;

import com.ihomefnt.o2o.intf.domain.common.http.PageInfo;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteWorksDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.DnaVoteTopResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.UserVoteRecordResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.WinnerListResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * dna投票
 *
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
public interface DnaVoteService {


    /**
     * 投票列表页查询
     *
     * @param userId
     * @return
     */
    PageInfo<DnaVoteWorksDto> queryVoteDnaList(Integer userId,String mobile, Integer pageNum, Integer pageSize);

    /**
     * @param dnaId
     * @param userId
     * @return
     */
    DnaVoteWorksDto queryVoteDnaSimpleInfo(Integer dnaId, Integer userId);

    /**
     * @param dnaId
     * @return
     */
    DnaVoteWorksDto queryVoteDnaInfo(Integer userId,Integer dnaId);

    /**
     * 进行投票
     *
     * @param dnaId
     * @param userId
     * @return
     */
    DnaVoteWorksDto doVote(Integer userId, Integer dnaId, String mobile);

    /**
     * 列表顶部数据
     *
     * @return
     */
    DnaVoteTopResponse queryDnaVoteTopData(Integer osType);

    /**
     * 排行榜数据
     *
     * @param request
     * @return
     */
    PageInfo<DnaVoteWorksDto> queryDnaRanking(PageRequest request);

    /**
     * 用户投票记录查询
     *
     * @param userId
     * @return
     */
    UserVoteRecordResponse queryUserVoteRecord(Integer userId);

    /**
     * 判断用户是否有参与活动的资格
     *
     * @param userId
     * @return true 有资格
     */
    Boolean handlerUserOpenRule(Integer userId);

    /**
     * 获奖名单查询
     *
     * @return
     */
    WinnerListResponse queryWinnerList();
}
