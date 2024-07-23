package com.rongzer.suzhou.scm.dao;

import com.rongzer.suzhou.scm.pojo.Inventory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMapper {
    List<Inventory> selectInventoryList(@Param("companyId") String companyId);

    List<Inventory> selectInventoryByMainId(@Param("mainId") String mainId,@Param("companyId") String companyId);

    int addInventory(Inventory inventory);
}
