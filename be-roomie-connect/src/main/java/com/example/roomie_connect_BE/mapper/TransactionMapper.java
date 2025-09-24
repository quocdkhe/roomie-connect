package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.TransactionRequest;
import com.example.roomie_connect_BE.dto.response.TransactionResponse;
import com.example.roomie_connect_BE.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toTransactionResponseDTO(Transaction transaction);
    Transaction toTransaction(TransactionRequest transactionRequestDTO);
}
