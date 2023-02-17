package ru.mpei.tkz.models.equipments.complexEquipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexCurrentSource extends Equipment<Complex> {
    private Complex current;
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
