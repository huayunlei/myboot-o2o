/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.house.dto;

import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import lombok.Data;

import java.util.List;
/**
 * @author Administrator
 *
 */
@Data
public class House {

    private Long idtHouse;
    private String name;//户型
    private String images;
    
    private int suitCount;//套装数量
    private String houseStr;//户型
    private double size;//面积
    private String houseImg;//户型图
    private List<Suit> suitList;//套装列表
}
