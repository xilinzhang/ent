package com.lianchuan.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lianchuan.common.entity.po.BasePO;

public interface BaseDao<T extends BasePO> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
