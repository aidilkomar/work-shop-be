package com.asein.workshop.inventory;

import com.asein.workshop.inventory.dto.PartCreateRequest;
import com.asein.workshop.inventory.dto.PartResponse;
import jakarta.validation.Valid;

public interface PartService {
    PartResponse create(@Valid PartCreateRequest req);
}
