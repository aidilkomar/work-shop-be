package com.asein.workshop.workorder;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public final class WorkOrderWorkflow {
    private static final Map<WorkOrderStatus, EnumSet<WorkOrderStatus>> DIRECT_FLOW =
            new EnumMap<>(WorkOrderStatus.class);

    private static final Map<WorkOrderStatus, EnumSet<WorkOrderStatus>> INSPECTION_FLOW =
            new EnumMap<>(WorkOrderStatus.class);

    static {
        // DIRECT_REQUEST
        DIRECT_FLOW.put(
                WorkOrderStatus.CREATED,
                EnumSet.of(
                        WorkOrderStatus.WAITING_APPROVAL,
                        WorkOrderStatus.CANCELLED
                )
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.WAITING_APPROVAL,
                EnumSet.of(
                        WorkOrderStatus.APPROVED,
                        WorkOrderStatus.CANCELLED
                )
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.APPROVED,
                EnumSet.of(
                        WorkOrderStatus.IN_PROGRESS,
                        WorkOrderStatus.CANCELLED
                )
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.IN_PROGRESS,
                EnumSet.of(
                        WorkOrderStatus.COMPLETED
                )
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.COMPLETED,
                EnumSet.of(
                        WorkOrderStatus.PAID
                )
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.PAID,
                EnumSet.noneOf(WorkOrderStatus.class)
        );

        DIRECT_FLOW.put(
                WorkOrderStatus.CANCELLED,
                EnumSet.noneOf(WorkOrderStatus.class)
        );

        // INSPECTION_BASED
        INSPECTION_FLOW.put(
                WorkOrderStatus.CREATED,
                EnumSet.of(
                        WorkOrderStatus.INSPECTING,
                        WorkOrderStatus.CANCELLED
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.INSPECTING,
                EnumSet.of(
                        WorkOrderStatus.WAITING_APPROVAL,
                        WorkOrderStatus.CANCELLED
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.WAITING_APPROVAL,
                EnumSet.of(
                        WorkOrderStatus.APPROVED,
                        WorkOrderStatus.CANCELLED
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.APPROVED,
                EnumSet.of(
                        WorkOrderStatus.IN_PROGRESS,
                        WorkOrderStatus.CANCELLED
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.IN_PROGRESS,
                EnumSet.of(
                        WorkOrderStatus.COMPLETED
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.COMPLETED,
                EnumSet.of(
                        WorkOrderStatus.PAID
                )
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.PAID,
                EnumSet.noneOf(WorkOrderStatus.class)
        );

        INSPECTION_FLOW.put(
                WorkOrderStatus.CANCELLED,
                EnumSet.noneOf(WorkOrderStatus.class)
        );

    }

    private WorkOrderWorkflow() {}

    public static boolean canTransition(
            WorkOrderType type,
            WorkOrderStatus current,
            WorkOrderStatus next
    ) {

        Map<WorkOrderStatus, EnumSet<WorkOrderStatus>> flow =
                type == WorkOrderType.DIRECT_REQUEST
                        ? DIRECT_FLOW
                        : INSPECTION_FLOW;

        return flow
                .getOrDefault(current, EnumSet.noneOf(WorkOrderStatus.class))
                .contains(next);
    }
}
