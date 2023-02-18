package ru.mpei.tkz.models.equipments.composite_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.SchemeElementFactory;
import ru.mpei.tkz.factories.ThreePhaseNodesFactory;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.equipments.Equipment;

import java.util.HashMap;
import java.util.Map;

public class ThreePhaseVoltageSource<T extends FieldElement<T>> extends CompositeEquipment<T> {
    private SchemeElementFactory<T> elementFactory;
    private Map<String, Equipment<T>> equipmentByPhase;

    public ThreePhaseVoltageSource(String name, double voltage, double phase,
                                   SchemeElementFactory<T> elementFactory, ThreePhaseNodesFactory<T> nodesFactory) {
        Equipment<T> eA = elementFactory.getVoltageSource(name + "_phaseA", voltage, phase);
        Equipment<T> eB = elementFactory.getVoltageSource(name + "_phaseB", voltage, phase - 120);
        Equipment<T> eC = elementFactory.getVoltageSource(name + "_phaseC", voltage, phase + 120);

        this.startNode = nodesFactory.getNode(name, eA.getStartNode(), eB.getStartNode(), eC.getStartNode());
        this.endNode = nodesFactory.getNode(name, eA.getEndNode(), eB.getEndNode(), eC.getEndNode());

        equipmentByPhase = new HashMap<>();
        equipmentByPhase.put("A", eA);
        equipmentByPhase.put("B", eB);
        equipmentByPhase.put("C", eC);
    }

    @Override
    public ThreePhaseDataDto<T> getCurrent() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentCurrent(),
                equipmentByPhase.get("B").getEquipmentCurrent(),
                equipmentByPhase.get("C").getEquipmentCurrent()
        );
    }

    @Override
    public ThreePhaseDataDto<T> getVoltage() {
        return new ThreePhaseDataDto<>(
                equipmentByPhase.get("A").getEquipmentVoltage(),
                equipmentByPhase.get("B").getEquipmentVoltage(),
                equipmentByPhase.get("C").getEquipmentVoltage()
        );
    }
}
