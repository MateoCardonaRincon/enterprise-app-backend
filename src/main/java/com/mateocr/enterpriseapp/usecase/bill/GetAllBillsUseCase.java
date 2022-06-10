package com.mateocr.enterpriseapp.usecase.bill;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.mapper.BillMapper;
import com.mateocr.enterpriseapp.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetAllBillsUseCase implements Supplier<Flux<BillDTO>> {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Flux<BillDTO> get() {
        return billRepository.findAll()
                .map(bill -> billMapper.convertCollectionToBillDTO().apply(bill));
    }
}
