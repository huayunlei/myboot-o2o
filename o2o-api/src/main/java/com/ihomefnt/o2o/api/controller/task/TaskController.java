package com.ihomefnt.o2o.api.controller.task;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.task.vo.request.TaskRequest;
import com.ihomefnt.o2o.intf.domain.task.vo.response.TaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "【任务API】")
@RestController
@RequestMapping("/task")
public class TaskController {

    @ApiOperation(value = "设计任务催一催", notes = "设计任务催一催")
    @PostMapping(value = "/urge/designDemand")
    public HttpBaseResponse<TaskResponse> urgeDesignDemand(@RequestBody TaskRequest request) {
        return HttpBaseResponse.success(new TaskResponse("已发送给设计师", "你的专属方案将安排加紧制作"));
    }

    @ApiOperation(value = "交付计划催一催", notes = "交付计划催一催")
    @PostMapping(value = "/urge/deliveryPlan")
    public HttpBaseResponse<TaskResponse> mainFrame(@RequestBody TaskRequest request) {
        return HttpBaseResponse.success(new TaskResponse("已收到排期催促", "你家的施工计划将安排加紧排期"));
    }
}
