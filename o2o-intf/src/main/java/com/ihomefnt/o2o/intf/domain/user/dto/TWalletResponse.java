package com.ihomefnt.o2o.intf.domain.user.dto;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.user.doo.WalletDo;
import lombok.Data;

@Data
public class TWalletResponse {
	 private List<WalletDo> myWallets;
	 private Long count;//我的钱袋子总记录数
	 private Double myWalletTotal;//总额
	 private Double myWalletSum;//余额
	 private Double myFrozenSum;//冻结
	 
}
