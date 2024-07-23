package com.rongzer.suzhou.scm.dao;

import com.rongzer.suzhou.scm.pojo.RealTimeBlockChain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeBlockChainMapper {
    int insertBlockChainOnChain(RealTimeBlockChain realTimeBlockChain);
    Long selectUpchainsNumber(@Param("upchainType") Integer upchainType);
    Long selectCreditDataUpchainsNumber(@Param("createDate") String createDate);
    Long selectRealTimeDataUpchainsNumber(@Param("createDate") String createDate);
}
