package com.mateocr.enterpriseapp.mapper;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.dto.BillDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.function.Function;

@EnableWebFlux
@Configuration
@RequiredArgsConstructor
public class BillMapper {

    private final ModelMapper modelMapper;

    public Function<BillDTO, Bill> convertBillDTOToCollection() {
        return billDTO -> modelMapper.map(billDTO, Bill.class);
    }

    public Function<Bill, BillDTO> convertCollectionToBillDTO() {
        return bill -> modelMapper.map(bill, BillDTO.class);
    }


}
