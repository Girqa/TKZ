package ru.mpei.tkz.models.equipments.complex_equipment.composite;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexEquipmentFactory;
import ru.mpei.tkz.factories.ComplexNodeFactory;
import ru.mpei.tkz.models.equipments.complex_equipment.ThreePhaseYgDTransformer;

public class ThreePhaseComplexYgDTransformer extends ThreePhaseYgDTransformer<Complex> {

    public ThreePhaseComplexYgDTransformer(String name, double lHv, double lLv, double m, double rHv, double rLv) {
        super(name, lHv, lLv, m, rHv, rLv, new ComplexEquipmentFactory(), new ComplexNodeFactory());
    }
}
