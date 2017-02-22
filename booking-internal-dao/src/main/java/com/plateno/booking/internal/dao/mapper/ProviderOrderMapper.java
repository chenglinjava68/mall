package com.plateno.booking.internal.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.dao.pojo.ProviderOrderResponse;

public interface ProviderOrderMapper {

    List<ProviderOrderResponse> queryProviderOrder(@Param("record")ProviderOrderParam param);
}
