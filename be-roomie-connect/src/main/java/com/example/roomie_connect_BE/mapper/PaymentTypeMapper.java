package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.PaymentTypeRequest;
import com.example.roomie_connect_BE.dto.response.PaymentTypeResponse;
import com.example.roomie_connect_BE.entity.PaymentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {
    PaymentTypeResponse toPaymentTypeResponseDTO(PaymentType paymentType);

    PaymentType toPaymentType(PaymentTypeRequest paymentTypeRequestDTO);
}