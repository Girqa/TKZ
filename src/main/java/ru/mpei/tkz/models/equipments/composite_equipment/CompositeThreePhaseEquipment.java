package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Parent class for all three-phase equipment.
 * MAIN CONTRACT:
 * - Each three-phase equipment must contain Map with three keys:
 * - A, B, C - phases names.
 */

@ToString
public abstract class CompositeThreePhaseEquipment<T extends FieldElement<T>> {
    @Getter
    protected String name;
    @Getter
    protected String type;
    protected Map<String, Equipment<T>> equipmentByPhase;
    @Getter
    protected ThreePhaseNode<T> startNode;
    @Getter
    protected ThreePhaseNode<T> endNode;

    /**
     * Counts current at the start of three-phase equipment
     */
    public ThreePhaseDataDto<T> getCurrent() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentCurrent(),
                equipmentByPhase.get("B").getEquipmentCurrent(),
                equipmentByPhase.get("C").getEquipmentCurrent()
        );
    }

    /**
     * Counts phase voltages for each three-phase equipment phase
     */
    public ThreePhaseDataDto<T> getVoltage() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentVoltage(),
                equipmentByPhase.get("B").getEquipmentVoltage(),
                equipmentByPhase.get("C").getEquipmentVoltage()
        );
    }

    /**
     * Returns all equipment, contained in three-phase equipment
     */
    public Collection<Equipment<T>> getEquipment() {
        return equipmentByPhase.values();
    }

    /**
     * Sets start node of the equipment
     */
    public void setStartNode(ThreePhaseNode<T> startNode) {
        this.startNode = startNode;
        equipmentByPhase.get("A").setStartNode(startNode.getNA());
        equipmentByPhase.get("B").setStartNode(startNode.getNB());
        equipmentByPhase.get("C").setStartNode(startNode.getNC());
    }
    /**
     * Sets end node of the equipment
     */
    public void setEndNode(ThreePhaseNode<T> endNode) {
        this.endNode = endNode;
        equipmentByPhase.get("A").setEndNode(endNode.getNA());
        equipmentByPhase.get("B").setEndNode(endNode.getNB());
        equipmentByPhase.get("C").setEndNode(endNode.getNC());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeThreePhaseEquipment<?> that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(startNode, that.startNode) && Objects.equals(endNode, that.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, startNode, endNode);
    }
}
