package ru.mpei.tkz.models.equipments.composite_equipment.voltage_sources;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexEquipmentFactory;
import ru.mpei.tkz.factories.ComplexNodeFactory;

public class ThreePhaseComplexVoltageSource extends ThreePhaseVoltageSource<Complex> {
    // TODO: придумать, как не созавать каждый раз фабрику
    public ThreePhaseComplexVoltageSource(String name, double voltage, double phase) {
        super(name, voltage, phase, new ComplexEquipmentFactory(), new ComplexNodeFactory());
    }
}
