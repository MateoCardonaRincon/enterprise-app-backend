package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.mapper.SupplierMapper;
import com.mateocr.enterpriseapp.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetAllSuppliersUseCase implements Supplier<Flux<SupplierDTO>> {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public Flux<SupplierDTO> get() {
        return supplierRepository.findAll()
                .map(supplier -> supplierMapper.convertCollectionToSupplierDTO().apply(supplier));
    }
}
