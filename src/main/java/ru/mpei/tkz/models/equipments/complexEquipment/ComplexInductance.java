package ru.mpei.tkz.models.equipments.complexEquipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexInductance extends Equipment<Complex> {

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
