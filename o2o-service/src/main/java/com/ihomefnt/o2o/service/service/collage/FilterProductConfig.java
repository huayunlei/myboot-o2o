package com.ihomefnt.o2o.service.service.collage;

import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jerfan cang
 * @date 2018/10/20 14:13
 */
@Service
public class FilterProductConfig {

    //活动商品 艾商城不展示
    @ApiModelProperty("艺术品列表中需要过滤的商品")
    private static volatile Set<Integer> filterProductList = new HashSet<>();


    /**
     * 添加要过滤的商品 到过滤列表
     * @param id 商品id  skuId
     * @return filterProductList Set<Integer>
     * @throws O2oException O2oException
     */
    public Set<Integer> addFilterProduct(List<Integer> ids) {
        try{
            synchronized ( filterProductList ){
                filterProductList.addAll(ids);
                return filterProductList;
            }
        }catch (Exception e){
        	throw new BusinessException("添加过滤商品异常");
        }
    }

    /**
     * 删除要过滤商品 从过滤列表中
     * @param id skuId
     * @return  filterProductList Set<Integer>
     * @throws O2oException O2oException
     */
    public Set<Integer> removeFilterProduct(List<Integer> ids) {
        try{
            synchronized ( filterProductList ){
                filterProductList.removeAll(ids);
                return filterProductList;
            }
        }catch (Exception e){
        	throw new BusinessException("删除过滤商品异常");
        }
    }


    public Set<Integer> getFilterProduct(){
            return filterProductList;
    }
}
