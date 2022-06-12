package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.repository.SupplierRepository;
import com.mateocr.enterpriseapp.usecase.supplier.interfaces.DeleteSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteSupplierUseCase implements DeleteSupplier {

    private final SupplierRepository supplierRepository;

    @Override
    public Mono<Void> apply(String supplierId) {
        return supplierRepository.findById(supplierId)
                .flatMap(entity -> supplierRepository.deleteById(entity.getId()))
                .switchIfEmpty(Mono.empty());
    }
}
