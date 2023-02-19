package ru.mpei.tkz.models.equipments.complex_equipment.composite;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexEquipmentFactory;
import ru.mpei.tkz.factories.ComplexNodeFactory;
import ru.mpei.tkz.models.enums.LoadType;
import ru.mpei.tkz.models.equipments.composite_equipment.ThreePhaseLoad;

public class ThreePhaseComplexLoad extends ThreePhaseLoad<Complex> {
    /**
     * Creates three-phase load
     * @param name - name of load (must be unique)
     * @param resistance - Ohm
     * @param capacity - Farad
     * @param inductance - Genre
     * @param type - type of load
     */
    public ThreePhaseComplexLoad(String name, double resistance, double capacity, double inductance, LoadType type) {
        super(name, resistance, capacity, inductance, type, new ComplexEquipmentFactory(), new ComplexNodeFactory());
    }
}
