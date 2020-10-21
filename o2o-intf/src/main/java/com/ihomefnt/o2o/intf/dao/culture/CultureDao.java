package com.ihomefnt.o2o.intf.dao.culture;

public interface CultureDao {
	
	/**
	 * 查询该用户购买指定商品的数量
	 * @param userId
	 * @return
	 */
	int queryProductCount(Integer userId,Integer productId);
	
	/**
	 * 插入用户购买文旅商品记录
	 * @param userId
	 * @param productId
	 * @return
	 */
	boolean addUserPurchaseCultureRecord(Integer userId,Integer productId,Integer status);
	
	/**
	 * 更新文旅商品状态
	 * @param userId
	 * @param productId
	 * @param status
	 * @return
	 */
	boolean updateUserPurchaseCultureRecord(Integer userId, Integer productId, Integer status);
	
}
