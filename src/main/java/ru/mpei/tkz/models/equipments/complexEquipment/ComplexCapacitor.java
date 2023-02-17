package ru.mpei.tkz.models.equipments.complexEquipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexCapacitor extends Equipment<Complex> {
    public ComplexCapacitor(String name,
                            Node<Complex> startNode,
                            Node<Complex> endNode,
                            double capacity) {
        this(name, startNode, endNode, capacity, 50);
        this.type = "capacitor";
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
