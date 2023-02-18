package ru.mpei.tkz.factories;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.complex_equipment.*;

public class ComplexEquipmentFactory implements SchemeEquipmentFactory<Complex> {
    @Override
    public Equipment<Complex> getCapacitor(String name, double capacity) {
        return new ComplexCapacitor(name, capacity);
    }

    @Override
    public Equipment<Complex> getInductance(String name, double inductance) {
        return new ComplexInductance(name, inductance);
    }

    @Override
    public Equipment<Complex> getResistance(String name, double resistance) {
        return new ComplexResistance(name, resistance);
    }

    @Override
    public Equipment<Complex> getVoltageSource(String name, double voltage, double phase) {
        return new ComplexVoltageSource(name, voltage, phase);
    }

    @Override
    public Equipment<Complex> getCurrentSource(String name, double current, double phase) {
        return new ComplexCurrentSource(name, current, phase);
    }
}
