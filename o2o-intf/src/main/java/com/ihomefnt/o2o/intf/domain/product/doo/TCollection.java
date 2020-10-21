package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Timestamp;


/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class TCollection {

    /**
     * 主鍵
     */
    private Long collectionId;
    private Long userId;
    private Long productId;
    private Long type;
    private Timestamp createTime;
    private Timestamp updateTime;
    /**
     * 1-添加收藏 0-取消收藏
     */
    private Long status;

	public Timestamp getCreateTime() {
        return (null == createTime) ? null : ((Timestamp) createTime.clone());
    }

    public void setCreateTime(Timestamp createTime) {
        if (null == createTime) {
            this.createTime = null;
        } else {
            this.createTime = (Timestamp) createTime.clone();
        }
    }

    public Timestamp getUpdateTime() {
        return (null == updateTime) ? null : ((Timestamp) updateTime.clone());
    }

    public void setUpdateTime(Timestamp updateTime) {
        if (null == updateTime) {
            this.updateTime = null;
        } else {
            this.updateTime = (Timestamp) updateTime.clone();
        }
    }
}
