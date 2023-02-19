package ru.mpei.tkz.models.equipments.complex_equipment.composite;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexInductance;
import ru.mpei.tkz.models.equipments.composite_equipment.CoupledCoils;

import java.util.Collection;
import java.util.List;

public class ComplexCoupledCoils extends CoupledCoils<Complex> {
    public ComplexCoupledCoils(String name, double l1, double l2, double m) {
        super(name);
        this.xl1 = new CoupledComplexInductance(name + "_hv", l1);
        this.xl2 = new CoupledComplexInductance(name + "_lv", l2);
        ((CoupledComplexInductance) xl1).setCouple(xl2);
        ((CoupledComplexInductance) xl2).setCouple(xl1);
        this.mutualInduction = new Complex(0, m * 2 * Math.PI * 50);
    }

    @Override
    public Collection<Equipment<Complex>> getEquipment() {
        return List.of(xl1, xl2);
    }


    private class CoupledComplexInductance extends ComplexInductance {
        private Equipment<Complex> couple;

        public CoupledComplexInductance(String name, double inductance) {
            super(name, inductance);
        }

        @Override
        public Complex getEquipmentCurrent() {
            Complex d1 = getEquipmentVoltage().divide(getEquipmentResistance());
            Complex d2 = couple.getEquipmentVoltage().multiply(getMutualInduction())
                    .divide(getEquipmentResistance().multiply(couple.getEquipmentResistance()));
            Complex d3 = Complex.ONE
                    .subtract(getMutualInduction().multiply(getMutualInduction())
                            .divide(getEquipmentResistance().multiply(couple.getEquipmentResistance())));
            Complex current = d1.subtract(d2).divide(d3);
            return current;
        }

        public void setCouple(Equipment<Complex> couple) {
            this.couple = couple;
        }
    }
}
