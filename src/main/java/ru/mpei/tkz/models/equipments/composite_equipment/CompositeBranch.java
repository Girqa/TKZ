package ru.mpei.tkz.models.equipments.composite_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.base_equipment.CurrentSource;
import ru.mpei.tkz.models.nodes.Node;

import java.util.List;

/**
 * One-phase composite equipment
 */
public class CompositeBranch<T extends FieldElement<T>> extends Equipment<T> {
    private final List<Equipment<T>> equipment;

    /**
     * Creates new single phase composite equipment (it must contain equipment connected in series!)
     * @param name - name of equipment
     * @param equipment - list of series connected equipment
     */
    public CompositeBranch(String name, List<Equipment<T>> equipment) {
        super(name, equipment.get(0).getStartNode(), equipment.get(equipment.size()-1).getEndNode());
        this.equipment = equipment;
        this.type = "composite equipment";
        this.resistance = equipment.stream()
                .map(Equipment::getEquipmentResistance)
                .reduce(T::add)
                .orElseThrow(() -> new IllegalArgumentException("Can't count resistance of branch"));
    }

    /**
     * THIS TYPE OF CONSTRUCTOR IS NOT SUPPORTED!
     * @throws Exception
     */
    public CompositeBranch(String name, Node<T> startNode, Node<T> endNode) throws Exception {
        super(name, startNode, endNode);
        throw new Exception("This class constructor is not supported");
    }

    @Override
    public Node<T> getStartNode() {
        return equipment.get(0).getStartNode();
    }

    @Override
    public Node<T> getEndNode() {
        return equipment.get(equipment.size()-1).getEndNode();
    }

    @Override
    public void setStartNode(Node<T> startNode) {
        equipment.get(0).setStartNode(startNode);
        this.startNode = startNode;
    }

    @Override
    public void setEndNode(Node<T> endNode) {
        equipment.get(equipment.size()-1).setEndNode(endNode);
        this.endNode = endNode;
    }

    @Override
    public T getEquipmentVoltage() {
        T voltage = equipment.get(0).getEquipmentVoltage();
        for (int i = 1; i < equipment.size(); i++) {
            voltage.add(equipment.get(i).getEquipmentVoltage());
        }
        return voltage;
    }

    @Override
    public T getEquipmentCurrent() {
        int currentSources = 0;
        T current = equipment.get(0).getEquipmentCurrent();
        if (equipment.get(0) instanceof CurrentSource<T>) {
            currentSources++;
        }
        for (int i = 1; i < equipment.size(); i++) {
            if (equipment.get(i) instanceof CurrentSource<T>) {
                currentSources += 1;
            }
        }
        if (currentSources > 1) {
            throw new IllegalArgumentException("Composite branch can't have more than 1 Current Source, but has " + currentSources);
        }
        return current;
    }

    public List<Equipment<T>> getEquipment() {
        return equipment;
    }
}
