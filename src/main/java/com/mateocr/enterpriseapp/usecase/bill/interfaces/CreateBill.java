package com.mateocr.enterpriseapp.usecase.bill.interfaces;

import com.mateocr.enterpriseapp.dto.BillDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CreateBill {
    Mono<BillDTO> apply(BillDTO billDTO);
}
