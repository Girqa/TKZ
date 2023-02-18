package ru.mpei.tkz.models.equipments.composite_equipment.load;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexEquipmentFactory;
import ru.mpei.tkz.factories.ComplexNodeFactory;
import ru.mpei.tkz.models.enums.LoadType;

public class ThreePhaseComplexLoad extends ThreePhaseLoad<Complex> {
    public ThreePhaseComplexLoad(String name, double resistance, double capacity, double inductance, LoadType type) {
        super(name, resistance, capacity, inductance, type, new ComplexEquipmentFactory(), new ComplexNodeFactory());
    }
}
