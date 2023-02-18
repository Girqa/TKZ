package ru.mpei.tkz.models.equipments.complex_equipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexInductance extends Equipment<Complex> {

    public ComplexInductance(String name, double inductance) {
        this(
                name,
                new ComplexNode(name + "_inductance_" + "start node"),
                new ComplexNode(name + "_inductance_" + "end node"),
                inductance);
    }

    public ComplexInductance(String name,
                             Node<Complex> startNode,
                             Node<Complex> endNode,
                             double inductance) {
        this(name, startNode, endNode, inductance, 50);
    }

    public ComplexInductance(String name,
                             Node<Complex> startNode,
                             Node<Complex> endNode,
                             double inductance,
                             double frequency) {
        super(name, startNode, endNode);
        this.resistance = new Complex(0, inductance * 2 * Math.PI * frequency);
        this.type = "inductance";
    }
}
