package com.ihomefnt.o2o.service.service.common;

import org.springframework.stereotype.Service;


@Service
public class CommonService {

    public boolean judgeMobileIsTest(String userMobile){
        if(userMobile == null){
            return false;
        }
        return userMobile.indexOf("17777777") != -1 || userMobile.indexOf("18888888")!=-1;
    }
}
