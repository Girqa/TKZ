package ru.mpei.tkz.models.connections;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.composite_equipment.CompositeThreePhaseEquipment;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for all scheme three-phase connections
 */
public class ThreePhaseConnections<T extends FieldElement<T>> {
    private final Map<ThreePhaseNode<T>, ThreePhaseConnection<T>> connectionsByNodes;

    public ThreePhaseConnections() {
        this.connectionsByNodes = new HashMap<>();
    }

    /**
     * Connects to connectionNode given equipment according to enum Side
     * @param connectionNode - node to which equipment will be connected
     * @param connectedEquipment - equipment to be connected
     * @param side - which node of equipment is connecting
     */
    public void connectEquipment(ThreePhaseNode<T> connectionNode, CompositeThreePhaseEquipment<T> connectedEquipment, Side side) {
        ThreePhaseConnection<T> connection = connectionsByNodes.getOrDefault(
                connectionNode,
                new ThreePhaseConnection<>(connectionNode)
        );
        connection.connectEquipment(connectedEquipment, side);
        connectionsByNodes.put(connectionNode, connection);
    }

    /**
     * Connects connectedEq to connectionEq by given sides
     * @param connectionEq - to which equipment wil connect
     * @param toWhichSideConnect - side of connection equipment to which will connect
     * @param connectedEq - equipment to be connected
     * @param whichSideConnect - which side of equipment will be connected
     */
    public void connectEquipment(CompositeThreePhaseEquipment<T> connectionEq,
                                 Side toWhichSideConnect,
                                 CompositeThreePhaseEquipment<T> connectedEq,
                                 Side whichSideConnect) {
        switch (toWhichSideConnect) {
            case START -> connectEquipment(connectionEq.getStartNode(), connectedEq, whichSideConnect);
            case END -> connectEquipment(connectionEq.getEndNode(), connectedEq, whichSideConnect);
        }
    }

    /**
     * Disconnects given equipment at the disconnectFrom node
     * @param disconnectingEquipment - equipment to be disconnected
     * @param disconnectFrom - which node (start or end) should be disconnected
     */
    public void disconnectEquipment(CompositeThreePhaseEquipment<T> disconnectingEquipment, Side disconnectFrom) {
        ThreePhaseNode<T> nodeToBeDisconnected = null;
        switch (disconnectFrom) {
            case START -> nodeToBeDisconnected = disconnectingEquipment.getStartNode();
            case END -> nodeToBeDisconnected = disconnectingEquipment.getEndNode();
        }
        ThreePhaseConnection<T> connection = connectionsByNodes.get(nodeToBeDisconnected);
        if (connection == null) {
            throw new IllegalArgumentException("There is no connection for this device at " + disconnectFrom + " node");
        }
        connection.disconnectEquipment(disconnectingEquipment);
        connectionsByNodes.remove(nodeToBeDisconnected);
    }
}
