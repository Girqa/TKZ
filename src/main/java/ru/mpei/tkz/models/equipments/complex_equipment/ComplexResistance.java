package ru.mpei.tkz.models.equipments.complex_equipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexResistance extends Equipment<Complex> {
    public ComplexResistance(String name, double resistance) {
        this(
                name,
                new ComplexNode(name + "_resistance_" + "start node"),
                new ComplexNode(name + "_resistance_" + "end node"),
                resistance);
    }

    public ComplexResistance(String name, Node<Complex> startNode, Node<Complex> endNode, double resistance) {
        super(name, startNode, endNode);
        this.type = "resistance";
        this.resistance = new Complex(resistance, 0);
    }
}
