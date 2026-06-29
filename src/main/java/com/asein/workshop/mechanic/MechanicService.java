package com.asein.workshop.mechanic;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.mechanic.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface MechanicService {
    List<MechanicListResponse> getMechanics(@Valid MechanicListRequest req);

    MechanicResponse getMechanic(@Valid ApiRequest req);

    MechanicResponse create(@Valid MechanicCreateRequest req);

    MechanicResponse update(@Valid MechanicUpdateRequest req);

    void updateStatus(@Valid long id, String status);

    void delete(@Valid ApiRequest req);
}
