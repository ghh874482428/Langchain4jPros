package com.sinontech.study.dao;

import com.sinontech.study.entity.RingOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RingOrderDao {
    void insert(RingOrder order);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int deleteById(Long id);
    RingOrder findById(Long id);
    List<RingOrder> findByUserId(String userId);
}
