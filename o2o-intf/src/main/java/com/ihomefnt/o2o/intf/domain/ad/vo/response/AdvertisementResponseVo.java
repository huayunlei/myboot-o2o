package com.ihomefnt.o2o.intf.domain.ad.vo.response;

import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponseVo {

    private String bannerUrl;
    private String jumpUrl;
    private Integer logon;
    private String imageUrl2;

	public AdvertisementResponseVo(AdvertisementDto ad) {
        this.bannerUrl = ad.getImgUrl();
        this.jumpUrl = ad.getrHttpUrl();
        this.logon = ad.getLogon();
        this.imageUrl2 = ad.getImgUrl2();
    }
}
