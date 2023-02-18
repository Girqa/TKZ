package ru.mpei.tkz.models.equipments.complex_equipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.base_equipment.Capacitor;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexCapacitor extends Capacitor<Complex> {
    protected final String type = "capacitor";
    public ComplexCapacitor(String name, double capacity) {
        this(
                name,
                new ComplexNode(name + "_capacitor_" + "start node"),
                new ComplexNode(name + "_capacitor_" + "end node"),
                capacity);
    }

    public ComplexCapacitor(String name,
                            Node<Complex> startNode,
                            Node<Complex> endNode,
                            double capacity) {
        this(name, startNode, endNode, capacity, 50);
    }

    public ComplexCapacitor(String name,
                            Node<Complex> startNode,
                            Node<Complex> endNode,
                            double capacity,
                            double frequency) {
        super(name, startNode, endNode);
        this.resistance = new Complex(0, -1 / (2 * Math.PI * frequency));
    }
}
