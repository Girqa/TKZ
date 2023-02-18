package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.Collection;
import java.util.Map;

@Data
public abstract class CompositeThreePhaseEquipment<T extends FieldElement<T>> {
    protected String name;
    protected String type;
    protected Map<String, Equipment<T>> equipmentByPhase;
    protected ThreePhaseNode<T> startNode;
    protected ThreePhaseNode<T> endNode;

    public ThreePhaseDataDto<T> getCurrent() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentCurrent(),
                equipmentByPhase.get("B").getEquipmentCurrent(),
                equipmentByPhase.get("C").getEquipmentCurrent()
        );
    }

    public ThreePhaseDataDto<T> getVoltage() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentVoltage(),
                equipmentByPhase.get("B").getEquipmentVoltage(),
                equipmentByPhase.get("C").getEquipmentVoltage()
        );
    }

    public Collection<Equipment<T>> getEquipment() {
        return equipmentByPhase.values();
    }

    public void setStartNode(ThreePhaseNode<T> startNode) {
        this.startNode = startNode;
        equipmentByPhase.get("A").setStartNode(startNode.getNA());
        equipmentByPhase.get("B").setStartNode(startNode.getNB());
        equipmentByPhase.get("C").setStartNode(startNode.getNC());
    }

    public void setEndNode(ThreePhaseNode<T> endNode) {
        this.endNode = endNode;
        equipmentByPhase.get("A").setEndNode(endNode.getNA());
        equipmentByPhase.get("B").setEndNode(endNode.getNB());
        equipmentByPhase.get("C").setEndNode(endNode.getNC());
    }
}
