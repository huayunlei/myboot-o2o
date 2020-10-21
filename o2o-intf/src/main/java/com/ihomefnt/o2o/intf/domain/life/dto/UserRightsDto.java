package com.ihomefnt.o2o.intf.domain.life.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liyonggang
 * @create 2018-11-05 17:29
 */
@Data
public class UserRightsDto implements Serializable {
    private static final long serialVersionUID = 1717379121684212991L;

  private Long  userId ;//客户id
  private String  name ;//客户名称
  private Long  maxGradeId ;//最高权益等级
  private String  maxGradeName ;//最高权益等级名称
}
