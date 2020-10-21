package com.ihomefnt.o2o.intf.proxy.vote;

import com.ihomefnt.o2o.intf.domain.common.http.PageInfo;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteRecordDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteWorksDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.DnaVoteTopResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.UserVoteRecordResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.WinnerListResponse;

import java.util.List;

/**
 * dna投票
 *
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
public interface DnaVoteProxy {

    PageInfo<DnaVoteWorksDto> queryVoteDnaList(Integer userId, String mobile, Integer pageNum, Integer pageSize);

    DnaVoteWorksDto queryVoteDnaSimpleInfo(Integer dnaId);

    DnaVoteWorksDto queryVoteDnaInfo(Integer dnaId);

    DnaVoteWorksDto doVote(Integer userId, Integer dnaId, String mobile);

    DnaVoteTopResponse queryDnaVoteTopData();

    List<DnaVoteRecordDto> queryVoteRecordByUserIdAndDnaIdList(Integer userId, List<Integer> dnaIdList);

    PageInfo<DnaVoteWorksDto> queryDnaRanking(PageRequest request);

    UserVoteRecordResponse queryUserVoteRecord(Integer userId);

    WinnerListResponse queryWinnerList();
}
