package ru.mpei.tkz.models.equipments.complex_equipment.composite;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.base_equipment.Inductance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexInductance;
import ru.mpei.tkz.models.equipments.composite_equipment.SimpleTransformer;

public class SimpleComplexTransformer extends SimpleTransformer<Complex> {
    public SimpleComplexTransformer(String name, double l1, double l2, double m) {
        super(name, l1, l2, m);
        this.xl1 = new CoupledComplexInductance(name + "_l1", l1);
        this.xl2 = new CoupledComplexInductance(name + "_l2", l2);
        ((CoupledComplexInductance) xl1).setCouple(xl2);
        ((CoupledComplexInductance) xl2).setCouple(xl1);
        this.mutualInduction = new Complex(0, m * 2 * Math.PI * 50);
    }

    private class CoupledComplexInductance extends ComplexInductance {
        private Inductance<Complex> couple;

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

        public void setCouple(Inductance<Complex> couple) {
            this.couple = couple;
        }
    }
}
