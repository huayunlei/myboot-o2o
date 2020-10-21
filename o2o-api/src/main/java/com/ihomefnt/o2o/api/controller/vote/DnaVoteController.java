package com.ihomefnt.o2o.api.controller.vote;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import com.ihomefnt.o2o.intf.domain.vote.dto.DnaVoteWorksDto;
import com.ihomefnt.o2o.intf.domain.vote.vo.request.DnaRequest;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.DnaVoteTopResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.UserVoteRecordResponse;
import com.ihomefnt.o2o.intf.domain.vote.vo.response.WinnerListResponse;
import com.ihomefnt.o2o.intf.service.vote.DnaVoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyonggang
 * @create 2019-05-06 16:35
 */
@Api(tags = "【喜舍DNA投票API】",hidden = true)
@RestController
@RequestMapping("/dnaVote")
public class DnaVoteController {

    @Autowired
    private DnaVoteService dnaVoteService;


    @ApiOperation(value = "查询投票dna集合", notes = "查询投票dna集合")
    @PostMapping("/queryVoteDnaList")
    public HttpBaseResponse<PageInfo<DnaVoteWorksDto>> queryVoteDnaList(@RequestBody PageRequest request) {
        HttpUserInfoRequest userByToken = request.getUserInfo();
        return HttpBaseResponse.success(dnaVoteService.queryVoteDnaList(userByToken == null ? null : userByToken.getId(), userByToken == null ? null : userByToken.getMobile(), request.getPageNo(), request.getPageSize()));
    }


    @ApiOperation(value = "查询单个dna简单信息", notes = "查询单个dna简单信息")
    @PostMapping("/queryVoteDnaSimpleInfo")
    public HttpBaseResponse<DnaVoteWorksDto> queryVoteDnaSimpleInfo(@RequestBody DnaRequest request) {
        HttpUserInfoRequest userByToken = request.getUserInfo();
        if (userByToken == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(dnaVoteService.queryVoteDnaSimpleInfo(request.getDnaId(), userByToken.getId()));
    }


    @ApiOperation(value = "查询dna详情", notes = "查询dna详情")
    @PostMapping("/queryVoteDnaInfo")
    public HttpBaseResponse<DnaVoteWorksDto> queryVoteDnaInfo(@RequestBody DnaRequest request) {
        HttpUserInfoRequest userByToken = request.getUserInfo();
        return HttpBaseResponse.success(dnaVoteService.queryVoteDnaInfo(userByToken == null ? null : userByToken.getId(), request.getDnaId()));
    }

    @ApiOperation(value = "进行投票", notes = "进行投票")
    @PostMapping("/doVote")
    public HttpBaseResponse<DnaVoteWorksDto> doVote(@RequestBody DnaRequest request) {
        HttpUserInfoRequest userByToken = request.getUserInfo();
        if (userByToken == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(dnaVoteService.doVote(userByToken.getId(), request.getDnaId(), userByToken.getMobile()));
    }

    @ApiOperation("查询列表顶部数据")
    @PostMapping("/queryDnaVoteTopData")
    public HttpBaseResponse<DnaVoteTopResponse> queryDnaVoteTopData(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(dnaVoteService.queryDnaVoteTopData(request.getOsType()));
    }

    @ApiOperation("投票dna排行榜")
    @PostMapping("/queryDnaRanking")
    public HttpBaseResponse<PageInfo<DnaVoteWorksDto>> queryDnaRanking(@RequestBody PageRequest request) {
        return HttpBaseResponse.success(dnaVoteService.queryDnaRanking(request));
    }

    @ApiOperation("查询用户投票记录")
    @PostMapping("/queryUserVoteRecord")
    public HttpBaseResponse<UserVoteRecordResponse> queryUserVoteRecord(@RequestBody HttpBaseRequest request) {
        HttpUserInfoRequest userByToken = request.getUserInfo();
        if (userByToken == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
        }
        return HttpBaseResponse.success(dnaVoteService.queryUserVoteRecord(userByToken.getId()));
    }

    @ApiOperation("获奖名单")
    @PostMapping("/winnerList")
    public HttpBaseResponse<WinnerListResponse> queryWinnerList(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(dnaVoteService.queryWinnerList());
    }
}
