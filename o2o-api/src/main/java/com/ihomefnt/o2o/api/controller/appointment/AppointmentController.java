package com.ihomefnt.o2o.api.controller.appointment;

import com.ihomefnt.o2o.intf.domain.appointment.vo.response.AppointmentTime;
import com.ihomefnt.o2o.intf.domain.appointment.vo.response.HttpAppointmentTimeResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.service.appointment.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
@Deprecated
@Api(tags = "预约API",hidden = true)
@ApiIgnore
@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	AppointmentService appointmentService;
	
	@ApiOperation(value="getDateTime",notes="getDateTime")
    @RequestMapping(value = "/getDateTime", method = RequestMethod.POST)
    public HttpBaseResponse<HttpAppointmentTimeResponse> getDateTime() {
        HttpAppointmentTimeResponse res = new HttpAppointmentTimeResponse();
        List<AppointmentTime> result = appointmentService.getDateTime();
        res.setAppointmentTimeList(result);
        return HttpBaseResponse.success(res);
    }
}
