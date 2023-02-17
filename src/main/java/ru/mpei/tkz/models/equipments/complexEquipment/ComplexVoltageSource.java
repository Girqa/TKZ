package ru.mpei.tkz.models.equipments.complexEquipment;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

public class ComplexVoltageSource extends Equipment<Complex> {
    private Complex voltage;

    /**
     * @param voltage - volts
     * @param phase - degrees
     */
    public ComplexVoltageSource(String name,
                                Node<Complex> startNode,
                                Node<Complex> endNode,
                                double voltage,
                                double phase) {
        super(name, startNode, endNode);
        this.resistance = new Complex(TOLERANCE, 0);
        double real = voltage * Math.cos(Math.toRadians(phase));
        double imag = voltage * Math.sin(Math.toRadians(phase));
        this.voltage = new Complex(real, imag);
        this.type = "voltage source";
    }

    @Override
    public Complex getEquipmentVoltage() {
        return voltage;
    }

    @Override
    public Complex getEquipmentCurrent() {
        // --[  eds ]--[fiction resistance]--
        // --(<- U v)--(  fiction U ->    )--
        // -- counts current by fiction U and fiction resistance
        if (!startNode.getPotential().equals(endNode.getPotential())) {
            Complex fullVoltage = startNode.getPotential().subtract(endNode.getPotential());
            Complex fictionVoltage = fullVoltage.add(voltage);
            return fictionVoltage.divide(resistance);
        }
        return Complex.ZERO;
    }
}
