package ru.mpei.tkz.models.connections;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.composite_equipment.CompositeEquipment;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides connection between ThreePhaseNodes
 */
public class ThreePhaseConnection<T extends FieldElement<T>> {
    private final Map<CompositeEquipment<T>, ThreePhaseNode<T>> connectedNodesByEquipment;
    private final ThreePhaseNode<T> thisNode;

    public ThreePhaseConnection(ThreePhaseNode<T> node) {
        this.connectedNodesByEquipment = new HashMap<>();
        this.thisNode = node;
    }

    /**
     * Connects given equipment by given side to this node
     * @param equipment to be connected
     * @param side - which side (start, end) of equipment should be connected
     */
    public void connectEquipment(CompositeEquipment<T> equipment, Side side) {
        switch (side) {
            case START -> {
                connectedNodesByEquipment.put(equipment, equipment.getStartNode());
                equipment.setStartNode(thisNode);
            }
            case END -> {
                connectedNodesByEquipment.put(equipment, equipment.getEndNode());
                equipment.setEndNode(thisNode);
            }
        }
    }

    /**
     * Disconnects given equipment from this node
     * @param equipment ti be disconnected
     * @throws IllegalArgumentException if given equipment is not connected to this node
     */
    public void disconnectEquipment(CompositeEquipment<T> equipment) throws IllegalArgumentException {
        if (!connectedNodesByEquipment.containsKey(equipment)) {
            throw new IllegalArgumentException("Given equipment is not connected to this node");
        }
        if (equipment.getStartNode().equals(thisNode)) {
            equipment.setStartNode(connectedNodesByEquipment.get(equipment));
        } else {
            equipment.setEndNode(connectedNodesByEquipment.get(equipment));
        }
        connectedNodesByEquipment.remove(equipment);
    }
}
