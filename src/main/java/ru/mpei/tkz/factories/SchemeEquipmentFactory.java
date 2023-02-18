package ru.mpei.tkz.factories;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.Equipment;

public interface SchemeEquipmentFactory<T extends FieldElement<T>> {
    Equipment<T> getCapacitor(String name, double capacity);
    Equipment<T> getInductance(String name, double inductance);
    Equipment<T> getResistance(String name, double resistance);
    Equipment<T> getVoltageSource(String name, double voltage, double phase);
    Equipment<T> getCurrentSource(String name, double current, double phase);
}
