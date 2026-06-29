package com.asein.workshop.mechanic;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.exception.BadRequestException;
import com.asein.workshop.common.exception.ConflictException;
import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.mechanic.dto.*;
import com.asein.workshop.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final UserRepository userRepository;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, UserRepository userRepository) {
        this.mechanicRepository = mechanicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MechanicListResponse> getMechanics(MechanicListRequest req) {
        int page = Math.max(req.pageReq().page(), 1);
        int limit = req.pageReq().size();

        int offset = (page - 1) * limit;
        return mechanicRepository.findAll(offset, limit, req.name());
    }

    @Override
    public MechanicResponse getMechanic(ApiRequest req) {
        var mechanic = mechanicRepository.findById(req.id());
        if (mechanic.isEmpty()) throw new NotFoundException("data not found");
        var m = mechanic.get();

        return new MechanicResponse(
                m.getId(),
                m.getUserId(),
                m.getStatus(),
                m.getSpecialization(),
                m.getHireDate(),
                m.getSalary(),
                m.getSkillLevel()
        );
    }

    @Override
    public MechanicResponse create(MechanicCreateRequest req) {
        userRepository.findById(req.userId()).ifPresent(u -> {
            throw new ConflictException("user already taken");
        });
        var mechanic = Mechanic.builder()
                .userId(req.userId())
                .status(MechanicStatus.BUSY.toString())
                .specialization(req.specialization())
                .hireDate(Timestamp.from(Instant.parse(req.hireDate())))
                .salary(new BigDecimal(req.salary()))
                .skillLevel(req.skillLevel())
                .build();
        return mechanicRepository.save(mechanic);
    }

    @Override
    public MechanicResponse update(MechanicUpdateRequest req) {
        var mechanic = mechanicRepository.findById(req.id());
        return mechanic.map(m -> {
            if (m.getUserId() != req.mechanic().userId()) {
                userRepository.findById(req.mechanic().userId()).ifPresent(u -> {
                    throw new ConflictException("user id already taken");
                });
            }

            m.setSpecialization(req.mechanic().specialization());
            m.setSalary(new BigDecimal(req.mechanic().salary()));
            m.setSkillLevel(req.mechanic().skillLevel());

            return mechanicRepository.save(m);
        }).orElseThrow(() -> {
            log.info("data mechanic with id {} not found", req.id());
            return new NotFoundException("data not found");
        });
    }

    @Override
    public void updateStatus(long id, String status) {
        mechanicRepository.findById(id)
            .map(m -> {

                String currentStatus = m.getStatus();

                boolean validTransition = switch (currentStatus) {
                    case "CREATED" -> status.equals("INSPECTING")
                            || status.equals("CANCELLED");

                    case "INSPECTING" -> status.equals("WAITING_APPROVAL")
                            || status.equals("CANCELLED");

                    case "WAITING_APPROVAL" -> status.equals("APPROVED")
                            || status.equals("CANCELLED");

                    case "APPROVED" -> status.equals("IN_PROGRESS");

                    case "IN_PROGRESS" -> status.equals("COMPLETED");

                    case "COMPLETED" -> status.equals("PAID");

                    case "PAID", "CANCELLED" -> false;

                    default -> false;
                };

                if (!validTransition) {
                    throw new BadRequestException(
                            String.format(
                                    "invalid status transition from %s to %s",
                                    currentStatus,
                                    status
                            )
                    );
                }

                m.setStatus(status);
                return mechanicRepository.save(m);
            })
            .orElseThrow(() -> {
                log.info("data mechanic with id {} not found", id);
                return new NotFoundException("data not found");
            });
    }

    @Override
    public void delete(ApiRequest req) {
        var isDeleted = mechanicRepository.delete(req.id());
        if (!isDeleted) {
            log.info("data mechanic with id {} not found", req.id());
            throw new NotFoundException("data not found");
        }
    }
}
