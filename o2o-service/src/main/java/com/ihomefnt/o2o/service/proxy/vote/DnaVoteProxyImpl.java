package com.ihomefnt.o2o.service.proxy.vote;

import com.beust.jcommander.internal.Maps;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.PageInfo;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteRecordDto;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteWorksDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.DnaVoteTopResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.UserVoteRecordResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.WinnerListResponse;
import com.ihomefnt.o2o.intf.proxy.vote.DnaVoteProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * dna 投票
 *
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
@Repository
public class DnaVoteProxyImpl implements DnaVoteProxy {

    @Autowired
    private StrongSercviceCaller sercviceCaller;

    @Override
    public PageInfo<DnaVoteWorksDto> queryVoteDnaList(Integer userId, String mobile, Integer pageNum, Integer pageSize) {
        return ((HttpBaseResponse<PageInfo<DnaVoteWorksDto>>) sercviceCaller.post("wcm-web.dnaVote.queryVoteDnaList", Maps.newHashMap("userId", userId, "pageNum", pageNum, "pageSize", pageSize, "mobile", mobile), new TypeReference<HttpBaseResponse<PageInfo<DnaVoteWorksDto>>>() {
        })).getObj();
    }

    @Override
    public DnaVoteWorksDto queryVoteDnaSimpleInfo(Integer dnaId) {
        return ((HttpBaseResponse<DnaVoteWorksDto>) sercviceCaller.post("wcm-web.dnaVote.queryVoteDnaSimpleInfo", Maps.newHashMap("dnaId", dnaId), new TypeReference<HttpBaseResponse<DnaVoteWorksDto>>() {
        })).getObj();
    }

    @Override
    public DnaVoteWorksDto queryVoteDnaInfo(Integer dnaId) {
        return ((HttpBaseResponse<DnaVoteWorksDto>) sercviceCaller.post("wcm-web.dnaVote.queryVoteDnaInfo", Maps.newHashMap("dnaId", dnaId), new TypeReference<HttpBaseResponse<DnaVoteWorksDto>>() {
        })).getObj();
    }

    @Override
    public DnaVoteWorksDto doVote(Integer userId, Integer dnaId, String mobile) {
        return ((HttpBaseResponse<DnaVoteWorksDto>) sercviceCaller.post("wcm-web.dnaVote.doVote", Maps.newHashMap("dnaId", dnaId, "userId", userId, "mobile", mobile), new TypeReference<HttpBaseResponse<DnaVoteWorksDto>>() {
        })).getObj();
    }

    @Override
    public DnaVoteTopResponse queryDnaVoteTopData() {
        return ((HttpBaseResponse<DnaVoteTopResponse>) sercviceCaller.post("wcm-web.dnaVote.queryDnaVoteTopData", null, new TypeReference<HttpBaseResponse<DnaVoteTopResponse>>() {
        })).getObj();
    }

    @Override
    public List<DnaVoteRecordDto> queryVoteRecordByUserIdAndDnaIdList(Integer userId, List<Integer> dnaIdList) {
        return ((HttpBaseResponse<List<DnaVoteRecordDto>>) sercviceCaller.post("wcm-web.dnaVote.queryVoteRecordByUserIdAndDnaIdList", Maps.newHashMap("userId", userId, "dnaIdList", dnaIdList), new TypeReference<HttpBaseResponse<List<DnaVoteRecordDto>>>() {
        })).getObj();
    }

    @Override
    public PageInfo<DnaVoteWorksDto> queryDnaRanking(PageRequest request) {
        return ((HttpBaseResponse<PageInfo<DnaVoteWorksDto>>) sercviceCaller.post("wcm-web.dnaVote.queryDnaRanking", Maps.newHashMap("pageNum", request.getPageNo(), "pageSize", request.getPageSize()), new TypeReference<HttpBaseResponse<PageInfo<DnaVoteWorksDto>>>() {
        })).getObj();
    }

    @Override
    public UserVoteRecordResponse queryUserVoteRecord(Integer userId) {
        return ((HttpBaseResponse<UserVoteRecordResponse>) sercviceCaller.post("wcm-web.dnaVote.queryUserVoteRecord", Maps.newHashMap("userId", userId), new TypeReference<HttpBaseResponse<UserVoteRecordResponse>>() {
        })).getObj();
    }

    @Override
    public WinnerListResponse queryWinnerList() {
        return ((HttpBaseResponse<WinnerListResponse>) sercviceCaller.post("wcm-web.dnaVote.queryWinnerList", null, new TypeReference<HttpBaseResponse<WinnerListResponse>>() {
        })).getObj();
    }
}
