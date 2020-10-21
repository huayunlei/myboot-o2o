package com.ihomefnt.o2o.mapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ihomefnt.o2o.intf.proxy.sales.vo.response.InviteResponse;
import com.ihomefnt.o2o.intf.service.sales.SalesService;
import io.swagger.annotations.Api;

/**
 * Created by hvk687 on 10/20/15.
 */
@Api(value="M站销售详情API",description="M站销售详情老接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/sales")
public class MapiSalesController {


    @Value("@{host}")
    public String host;

    @Autowired
    SalesService salesService;

    @RequestMapping(value = "/invite/{salesId}", method = RequestMethod.GET)
    String inviteHome(@PathVariable Long salesId, Model model) {

        model.addAttribute("bindUrl", host + "/sales/bind/" + salesId);
        return "marketing/addInviteUser.ftl";
    }

    /**
     * bind user and sales
     *
     * @param salesId
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/bind/{salesId}/{mobile}", method = RequestMethod.GET)
    ModelAndView bind(@PathVariable Long salesId, @PathVariable String mobile) {

        InviteResponse response = salesService.inviteUser(salesId, mobile);
        return new ModelAndView(new RedirectView("http://m.ihomefnt.com/app/20000"));
    }
}
