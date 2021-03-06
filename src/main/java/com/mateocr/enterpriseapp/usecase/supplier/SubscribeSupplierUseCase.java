package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.mapper.SupplierMapper;
import com.mateocr.enterpriseapp.repository.SupplierRepository;
import com.mateocr.enterpriseapp.usecase.supplier.interfaces.CreateSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;


@Service
@Validated
@RequiredArgsConstructor
public class SubscribeSupplierUseCase implements CreateSupplier {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public Mono<SupplierDTO> apply(SupplierDTO supplierDTO) {
        return supplierRepository.save(supplierMapper.convertSupplierDTOToCollection().apply(supplierDTO))
                .map(supplier -> supplierMapper.convertCollectionToSupplierDTO().apply(supplier));
    }
}
