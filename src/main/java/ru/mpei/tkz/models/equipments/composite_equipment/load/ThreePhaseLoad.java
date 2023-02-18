package ru.mpei.tkz.models.equipments.composite_equipment.load;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.SchemeEquipmentFactory;
import ru.mpei.tkz.factories.NodesFactory;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.LoadType;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.composite_equipment.CompositeBranch;
import ru.mpei.tkz.models.equipments.composite_equipment.CompositeThreePhaseEquipment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class ThreePhaseLoad <T extends FieldElement<T>> extends CompositeThreePhaseEquipment<T> {
    public ThreePhaseLoad(String name, double resistance, double capacity, double inductance, LoadType type,
                          SchemeEquipmentFactory<T> equipmentFactory, NodesFactory<T> nodesFactory) {
        Connections<T> connections = new Connections<>();
        this.equipmentByPhase = new HashMap<>();
        this.name = name;
        this.type = "three-phase " + type + " load";
        String namePrefix = name + "_" + this.type + "_";
        switch (type) {
            case R -> {
                equipmentByPhase.put("A", equipmentFactory.getResistance(namePrefix + "A", resistance));
                equipmentByPhase.put("B", equipmentFactory.getResistance(namePrefix + "B", resistance));
                equipmentByPhase.put("C", equipmentFactory.getResistance(namePrefix + "C", resistance));
            }
            case L -> {
                equipmentByPhase.put("A", equipmentFactory.getInductance(namePrefix + "A", inductance));
                equipmentByPhase.put("B", equipmentFactory.getInductance(namePrefix + "B", inductance));
                equipmentByPhase.put("C", equipmentFactory.getInductance(namePrefix + "C", inductance));
            }
            case C -> {
                equipmentByPhase.put("A", equipmentFactory.getCapacitor(namePrefix + "A", capacity));
                equipmentByPhase.put("B", equipmentFactory.getCapacitor(namePrefix + "B", capacity));
                equipmentByPhase.put("C", equipmentFactory.getCapacitor(namePrefix + "C", capacity));
            }
            case RL -> {
                for (String phase: new String[]{"A", "B", "C"}) {
                    Equipment<T> r = equipmentFactory.getResistance(namePrefix + phase, resistance);
                    Equipment<T> l = equipmentFactory.getInductance(namePrefix + phase, inductance);
                    connections.connectEquipment(r, Side.END, l, Side.START);
                    CompositeBranch<T> rlBranch = new CompositeBranch<>(namePrefix + phase, List.of(r, l));
                    equipmentByPhase.put(phase, rlBranch);
                }
            }
            case RC -> {
                for (String phase: new String[]{"A", "B", "C"}) {
                    Equipment<T> r = equipmentFactory.getResistance(namePrefix + phase, resistance);
                    Equipment<T> c = equipmentFactory.getCapacitor(namePrefix + phase, capacity);
                    connections.connectEquipment(r, Side.END, c, Side.START);
                    CompositeBranch<T> rcBranch = new CompositeBranch<>(namePrefix + phase, List.of(r, c));
                    equipmentByPhase.put(phase, rcBranch);
                }
            }
            case LC -> {
                for (String phase: new String[]{"A", "B", "C"}) {
                    Equipment<T> l = equipmentFactory.getInductance(namePrefix + phase, inductance);
                    Equipment<T> c = equipmentFactory.getCapacitor(namePrefix + phase, capacity);
                    connections.connectEquipment(l, Side.END, c, Side.START);
                    CompositeBranch<T> lcBranch = new CompositeBranch<>(namePrefix + phase, List.of(l, c));
                    equipmentByPhase.put(phase, lcBranch);
                }
            }
            case RLC -> {
                for (String phase: new String[]{"A", "B", "C"}) {
                    Equipment<T> r = equipmentFactory.getResistance(namePrefix + phase, resistance);
                    Equipment<T> l = equipmentFactory.getInductance(namePrefix + phase, inductance);
                    Equipment<T> c = equipmentFactory.getCapacitor(namePrefix + phase, capacity);
                    connections.connectEquipment(r, Side.END, l, Side.START);
                    connections.connectEquipment(l, Side.END, c, Side.START);
                    CompositeBranch<T> rlcBranch = new CompositeBranch<>(namePrefix + phase, List.of(r, l, c));
                    equipmentByPhase.put(phase, rlcBranch);
                }
            }
        }
        this.startNode = nodesFactory.getThreePhaseNode(namePrefix + "start node",
                equipmentByPhase.get("A").getStartNode(),
                equipmentByPhase.get("B").getStartNode(),
                equipmentByPhase.get("C").getStartNode());
        this.endNode = nodesFactory.getThreePhaseNode(namePrefix + "end node",
                equipmentByPhase.get("A").getEndNode(),
                equipmentByPhase.get("B").getEndNode(),
                equipmentByPhase.get("C").getEndNode());
    }

    @Override
    public Collection<Equipment<T>> getEquipment() {
        List<Equipment<T>> equipments = new ArrayList<>();
        for (Equipment<T> branch: equipmentByPhase.values()) {
            if (branch instanceof CompositeBranch<T>) {
                equipments.addAll(((CompositeBranch<T>) branch).getEquipment());
            } else {
                equipments.add(branch);
            }
        }
        return equipments;
    }
}
