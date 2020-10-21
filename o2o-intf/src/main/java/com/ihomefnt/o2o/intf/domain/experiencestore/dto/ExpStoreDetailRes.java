package com.ihomefnt.o2o.intf.domain.experiencestore.dto;

import lombok.Data;

import java.util.List;
@Data
public class ExpStoreDetailRes {

    public BuildingExpStore store;
    
    public List<Suit> list;
}
