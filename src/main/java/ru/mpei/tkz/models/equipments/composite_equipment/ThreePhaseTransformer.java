package ru.mpei.tkz.models.equipments.composite_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class ThreePhaseTransformer<T extends FieldElement<T>> extends CompositeThreePhaseEquipment<T> {
    protected Map<String, SinglePhaseTransformer<T>> transformersByPhase;

    /**
     * Counts currents at hv side if transformer
     */
    public ThreePhaseDataDto<T> getHvCurrent() {
        return new ThreePhaseDataDto<>(
                transformersByPhase.get("A").getHvSide().getEquipmentCurrent(),
                transformersByPhase.get("B").getHvSide().getEquipmentCurrent(),
                transformersByPhase.get("C").getHvSide().getEquipmentCurrent()
        );
    }

    /**
     * Counts currents at lv side if transformer
     */
    public ThreePhaseDataDto<T> getLvCurrent() {
        return new ThreePhaseDataDto<>(
                transformersByPhase.get("A").getLvSide().getEquipmentCurrent(),
                transformersByPhase.get("B").getLvSide().getEquipmentCurrent(),
                transformersByPhase.get("C").getLvSide().getEquipmentCurrent()
        );
    }

    /**
     * Counts currents at hv side if transformer
     */
    @Override
    public ThreePhaseDataDto<T> getCurrent() {
        return getHvCurrent();
    }

    /**
     * Counts phase voltages at hv side if transformer
     */
    public ThreePhaseDataDto<T> getHvVoltage() {
        return new ThreePhaseDataDto<>(
                transformersByPhase.get("A").getHvSide().getEquipmentVoltage(),
                transformersByPhase.get("B").getHvSide().getEquipmentVoltage(),
                transformersByPhase.get("C").getHvSide().getEquipmentVoltage()
        );
    }

    /**
     * Counts phase voltages at lv side if transformer
     */
    public ThreePhaseDataDto<T> getLvVoltage() {
        return new ThreePhaseDataDto<>(
                transformersByPhase.get("A").getLvSide().getEquipmentVoltage(),
                transformersByPhase.get("B").getLvSide().getEquipmentVoltage(),
                transformersByPhase.get("C").getLvSide().getEquipmentVoltage()
        );
    }

    /**
     * Counts phase voltages at hv side if transformer
     */
    @Override
    public ThreePhaseDataDto<T> getVoltage() {
        return getHvVoltage();
    }

    /**
     * Returns all equipment, contained in three-phase transformer
     */
    @Override
    public Collection<Equipment<T>> getEquipment() {
        List<Equipment<T>> equipment = new ArrayList<>();
        for (SinglePhaseTransformer<T> transformer: transformersByPhase.values()) {
            equipment.addAll(transformer.getEquipment());
        }
        return equipment;
    }

    /**
     * Sets start node of the transformer
     */
    @Override
    public void setStartNode(ThreePhaseNode<T> startNode) {
        this.startNode = startNode;
        transformersByPhase.get("A").getHvSide().setStartNode(startNode.getNA());
        transformersByPhase.get("B").getHvSide().setStartNode(startNode.getNB());
        transformersByPhase.get("C").getHvSide().setStartNode(startNode.getNC());
    }

    /**
     * Sets end node of the transformer
     */
    @Override
    public void setEndNode(ThreePhaseNode<T> endNode) {
        this.endNode = endNode;
        transformersByPhase.get("A").getLvSide().setEndNode(endNode.getNA());
        transformersByPhase.get("B").getLvSide().setEndNode(endNode.getNB());
        transformersByPhase.get("C").getLvSide().setEndNode(endNode.getNC());
    }

    /**
     * Returns all single-phase transformers, contained in three-phase transformer
     */
    public Collection<SinglePhaseTransformer<T>> getSinglePhaseTransformers() {
        return transformersByPhase.values();
    }
}
