package ru.mpei.tkz.models.equipments.complex_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.NodesFactory;
import ru.mpei.tkz.factories.SchemeEquipmentFactory;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.composite_equipment.SinglePhaseTransformer;
import ru.mpei.tkz.models.equipments.composite_equipment.ThreePhaseTransformer;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseGround;

import java.util.HashMap;

public abstract class ThreePhaseYgDTransformer<T extends FieldElement<T>> extends ThreePhaseTransformer<T> {

    public ThreePhaseYgDTransformer(String name, double lHv, double lLv, double m, double rHv, double rLv,
                                    SchemeEquipmentFactory<T> equipmentFactory, NodesFactory<T> nodesFactory) {
        this.type = "three-phase Y/D transformer";
        this.name = name;
        this.transformersByPhase = new HashMap<>();
        String namePrefix = name + "_" + type;
        for (String phase: new String[]{"A", "B", "C"}) {
            SinglePhaseTransformer<T> transformer = equipmentFactory.getTransformer(
                    namePrefix + "_" + phase, lHv, lLv, m, rHv, rLv
            );
            transformersByPhase.put(phase, transformer);
        }
        // Ends of hv coils connected to ground -> connection type is Y
        ThreePhaseGround<T> ground = nodesFactory.getThreePhaseGround();
        transformersByPhase.get("A").getHvSide().setEndNode(ground.getNA());
        transformersByPhase.get("B").getHvSide().setEndNode(ground.getNB());
        transformersByPhase.get("C").getHvSide().setEndNode(ground.getNC());
        // Lv coils connected at D
        Connections<T> connections = new Connections<>();
        connections.connectEquipment(
                transformersByPhase.get("A").getLvSide(), Side.END,
                transformersByPhase.get("B").getLvSide(), Side.START
        );
        connections.connectEquipment(
                transformersByPhase.get("B").getLvSide(), Side.END,
                transformersByPhase.get("C").getLvSide(), Side.START
        );
        connections.connectEquipment(
                transformersByPhase.get("C").getLvSide(), Side.END,
                transformersByPhase.get("A").getLvSide(), Side.START
        );

        this.startNode = nodesFactory.getThreePhaseNode(
                namePrefix + "_HV node",
                transformersByPhase.get("A").getHvSide().getStartNode(),
                transformersByPhase.get("B").getHvSide().getStartNode(),
                transformersByPhase.get("C").getHvSide().getStartNode());
        this.endNode = nodesFactory.getThreePhaseNode(
                namePrefix + "_LV node",
                transformersByPhase.get("A").getLvSide().getEndNode(),
                transformersByPhase.get("B").getLvSide().getEndNode(),
                transformersByPhase.get("C").getLvSide().getEndNode());
    }
}
