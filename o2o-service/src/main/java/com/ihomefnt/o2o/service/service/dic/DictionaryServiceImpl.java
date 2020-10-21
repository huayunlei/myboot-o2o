package com.ihomefnt.o2o.service.service.dic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.dic.DictionaryService;
import com.ihomefnt.o2o.intf.dao.dic.DictionaryDao;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CouponRemarkDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.vo.request.HttpKeyRequest;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.AjbHelpDicResponseVo;
import com.ihomefnt.o2o.intf.domain.dic.vo.response.CashCouponDicResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.dic.DicConstant;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.domain.user.dto.AjbRemark;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private DicProxy dicProxy;

	@Override
	public CashCouponDicResponseVo getCashHelpDesc(HttpBaseRequest baseRequest) {
		String value = dictionaryDao.getValueByKey(DicConstant.CASH_HELP_DESC);
		if(StringUtils.isNotBlank(value)){
			String[] sets = value.split("<question>");
			List<CouponRemarkDto> remarkList = new ArrayList<CouponRemarkDto>();
			for(int i=1;i<sets.length;i++){
				CouponRemarkDto couponRemark = new CouponRemarkDto();
				String[] set = sets[i].split("<answer>");
				if(set.length>=2){
					couponRemark.setQuestion(set[0]);
					couponRemark.setAnswer(set[1]);
					remarkList.add(couponRemark);
				}				
			}
			if(remarkList.isEmpty()){
				throw new BusinessException(DicConstant.QUERY_EMPTY);
			}
			
			CashCouponDicResponseVo cashCoupon = new CashCouponDicResponseVo();
			cashCoupon.setRemarkList(remarkList);
			return cashCoupon;
		}
		return null;
	}

	@Override
	public AjbHelpDicResponseVo getAjbHelpDesc(HttpBaseRequest baseRequest) {
		String value = dictionaryDao.getValueByKey(DicConstant.AJB_HELP_DESC);
		if(StringUtils.isNotBlank(value)){
			String[] sets = value.split("<question>");
            List<AjbRemark> remarkList = new ArrayList<AjbRemark>();
            for(int i=1;i<sets.length;i++){
                AjbRemark ajbRemark = new AjbRemark();
                String[] set = sets[i].split("<answer>");
                if(set.length>=2){
                    ajbRemark.setQuestion(set[0]);
                    ajbRemark.setAnswer(set[1]);
                    remarkList.add(ajbRemark);
                }               
            }
            if(remarkList.isEmpty()){
				throw new BusinessException(DicConstant.QUERY_EMPTY);
			}
            
            AjbHelpDicResponseVo ajbHelp = new AjbHelpDicResponseVo();
            ajbHelp.setRemarkList(remarkList);
            return ajbHelp;
		}
		return null;
	}

	@Override
	public String[] getTextDescByKey(HttpKeyRequest baseRequest) {
		if (baseRequest.getKeyType().intValue() != DicConstant.GLOBAL_DEPOSIT_MONEY_TERM_KEY) {
			throw new BusinessException(DicConstant.QUERY_EMPTY);
		}
		DicDto dicVo = dicProxy.queryDicByKey(DicConstant.GLOBAL_DEPOSIT_MONEY_TERM_VALUE);
		String depositTerm = "";
		if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
			depositTerm = dicVo.getValueDesc();
		}
		if (StringUtils.isBlank(depositTerm)) {
			throw new BusinessException(DicConstant.QUERY_EMPTY);
		}
		
		return depositTerm.split("<question>");
	}
	
	@Override
	public String getValueByKey(String key) {
		return dictionaryDao.getValueByKey(key);
	}

}
