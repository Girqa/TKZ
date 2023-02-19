package ru.mpei.tkz.models.equipments.composite_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.SchemeEquipmentFactory;
import ru.mpei.tkz.factories.NodesFactory;
import ru.mpei.tkz.models.equipments.Equipment;

import java.util.*;

/**
 * Creates three-phase symmetrical voltage source
 */
public abstract class ThreePhaseVoltageSource<T extends FieldElement<T>> extends CompositeThreePhaseEquipment<T> {
    public ThreePhaseVoltageSource(String name, double voltage, double phase,
                                   SchemeEquipmentFactory<T> equipmentFactory, NodesFactory<T> nodesFactory) {
        this.type = "three-phase voltage source";
        this.name = name;
        String namePrefix = name + "_" + type;
        Equipment<T> eA = equipmentFactory.getVoltageSource(namePrefix + "_phaseA", voltage, phase);
        Equipment<T> eB = equipmentFactory.getVoltageSource(namePrefix + "_phaseB", voltage, phase - 120);
        Equipment<T> eC = equipmentFactory.getVoltageSource(namePrefix + "_phaseC", voltage, phase + 120);

        this.startNode = nodesFactory.getThreePhaseNode(namePrefix + "_start node",
                eA.getStartNode(), eB.getStartNode(), eC.getStartNode());
        this.endNode = nodesFactory.getThreePhaseNode(namePrefix + "_end node",
                eA.getEndNode(), eB.getEndNode(), eC.getEndNode());

        equipmentByPhase = new HashMap<>();
        equipmentByPhase.put("A", eA);
        equipmentByPhase.put("B", eB);
        equipmentByPhase.put("C", eC);
    }
}
