package ru.mpei.tkz.models.connections;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple connection class between given node and others
 * @param <T>
 */
public class Connection<T extends FieldElement<T>> {
    private final Node<T> thisNode;
    private Map<Equipment<T>, Node<T>> connectedNodesByEquipment;

    public Connection(Node<T> thisNode) {
        this.thisNode = thisNode;
        this.connectedNodesByEquipment = new HashMap<>();
    }

    /**
     * Connects given equipment by given node to this node
     * @param equipment to be connected
     * @param connectedNode to be replaced to this node
     * @throws IllegalArgumentException if given connected node is not a nod of given equipment
     */
    public void connectEquipment(Equipment<T> equipment, Node<T> connectedNode) throws IllegalArgumentException {
        if (!equipment.getStartNode().equals(connectedNode) && !equipment.getEndNode().equals(connectedNode)) {
            throw new IllegalArgumentException("Given equipment doesn't contain 'connectedNode'");
        }
        connectedNodesByEquipment.put(equipment, connectedNode);
        if (equipment.getStartNode().equals(connectedNode)) {
            equipment.setStartNode(thisNode);
        } else {
            equipment.setEndNode(thisNode);
        }
    }

    /**
     * Disconnects given equipment from this node
     * @param equipment ti be disconnected
     * @throws IllegalArgumentException if given equipment is not connected to this node
     */
    public void disconnectEquipment(Equipment<T> equipment) throws IllegalArgumentException {
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
