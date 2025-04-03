package com.kulushev.Orders.transformer;

import com.kulushev.Orders.dto.OrderReqDto;
import com.kulushev.Orders.dto.OrderRespDto;
import com.kulushev.Orders.entity.OrderEntity;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface OrderTransformer {

    OrderRespDto entityToDto(OrderEntity orderEntity);
    OrderEntity dtoToEntity(OrderReqDto orderReqDto);
}
