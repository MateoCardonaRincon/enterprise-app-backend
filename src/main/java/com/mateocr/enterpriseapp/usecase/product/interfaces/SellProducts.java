package com.mateocr.enterpriseapp.usecase.product.interfaces;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface SellProducts {
    Flux<ProductDTO> apply(BillDTO billDTO);
}
