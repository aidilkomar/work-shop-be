package com.asein.workshop.inventory;

import com.asein.workshop.common.exception.ConflictException;
import com.asein.workshop.inventory.dto.PartCreateRequest;
import com.asein.workshop.inventory.dto.PartResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public PartResponse create(PartCreateRequest req) {
        Optional<Part> skuExist = partRepository.findBySku(req.sku());

        if (skuExist.isPresent()) {
            throw new ConflictException("sku already exists");
        }

        return null;
    }
}
