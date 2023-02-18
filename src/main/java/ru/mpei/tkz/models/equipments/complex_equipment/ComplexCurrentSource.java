package ru.mpei.tkz.models.equipments.complex_equipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexCurrentSource extends Equipment<Complex> {
    private Complex current;

    public ComplexCurrentSource(String name, double current, double phase) {
        this(
                name,
                new ComplexNode(name + "_current source_" + "start node"),
                new ComplexNode(name + "_current source_" + "end node"),
                current,
                phase);
    }

    /**
     * @param current - amperes
     * @param phase - degrees
     */
    public ComplexCurrentSource(String name,
                                Node<Complex> startNode,
                                Node<Complex> endNode,
                                double current,
                                double phase) {
        super(name, startNode, endNode);
        this.resistance = new Complex(1 / TOLERANCE, 0);
        double real = current * Math.cos(Math.toRadians(phase));
        double imag = current * Math.sin(Math.toRadians(phase));
        this.current = new Complex(real, imag);
        this.type = "current source";
    }

    @Override
    public Complex getEquipmentCurrent() {
        return current;
    }
}
