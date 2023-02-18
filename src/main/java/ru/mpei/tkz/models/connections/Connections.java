package ru.mpei.tkz.models.connections;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for all scheme connections
 */
public class Connections<T extends FieldElement<T>> {
    private final Map<Node<T>, Connection<T>> connectionsByNodes;

    public Connections() {
        this.connectionsByNodes = new HashMap<>();
    }

    /**
     * Connects connectedEq to connectionEq by given sides
     * @param connectionEq - to which equipment wil connect
     * @param toWhichSideConnect - side of connection equipment to which will connect
     * @param connectedEq - equipment to be connected
     * @param whichSideConnect - which side of equipment will be connected
     */
    public void connectEquipment(Equipment<T> connectionEq,
                                 Side toWhichSideConnect,
                                 Equipment<T> connectedEq,
                                 Side whichSideConnect) {
        switch (toWhichSideConnect) {
            case START -> {connectEquipment(connectionEq.getStartNode(), connectedEq, whichSideConnect);}
            case END -> {connectEquipment(connectionEq.getEndNode(), connectedEq, whichSideConnect);}
        }
    }

    /**
     * Connects to connectionNode given equipment according to enum Side
     * @param connectionNode - node to which equipment will be connected
     * @param connectedEquipment - equipment to be connected
     * @param side - which node of equipment is connecting
     */
    public void connectEquipment(Node<T> connectionNode, Equipment<T> connectedEquipment, Side side) {
        Connection<T> connection = connectionsByNodes.getOrDefault(connectionNode, new Connection<>(connectionNode));

        switch (side) {
            case START -> {
                connection.connectEquipment(connectedEquipment, connectedEquipment.getStartNode());
            }
            case END -> {
                    connection.connectEquipment(connectedEquipment, connectedEquipment.getEndNode());
            }
        }
    }

    /**
     * Disconnects given equipment at the disconnectFrom node
     * @param disconnectingEquipment - equipment to be disconnected
     * @param disconnectFrom - which node (start or end) should be disconnected
     */
    public void disconnectEquipment(Equipment<T> disconnectingEquipment, Side disconnectFrom) {
        Node<T> nodeToBeDisconnected = null;
        switch (disconnectFrom) {
            case START -> {
                nodeToBeDisconnected = disconnectingEquipment.getStartNode();
            }
            case END -> {
                nodeToBeDisconnected = disconnectingEquipment.getEndNode();
            }
        }
        Connection<T> connection = connectionsByNodes.get(nodeToBeDisconnected);
        if (connection == null) {
            throw new IllegalArgumentException("There is no connection for this device at " + disconnectFrom + " node");
        }
        connection.disconnectEquipment(disconnectingEquipment);
    }
}
