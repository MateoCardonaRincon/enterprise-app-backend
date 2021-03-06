package com.mateocr.enterpriseapp.usecase.bill;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.mapper.BillMapper;
import com.mateocr.enterpriseapp.repository.BillRepository;
import com.mateocr.enterpriseapp.usecase.bill.interfaces.CreateBill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
@AllArgsConstructor
public class CreateBillUseCase implements CreateBill {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Mono<BillDTO> apply(BillDTO billDTO) {
        return billRepository.save(billMapper.convertBillDTOToCollection().apply(billDTO))
                .map(bill -> billMapper.convertCollectionToBillDTO().apply(bill));
    }
}
