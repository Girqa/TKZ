package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.CompositeBranch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to describe single phase transformer with coupled coils and, if chosen, active loses
 */
public abstract class SinglePhaseTransformer<T extends FieldElement<T>> extends CoupledCoils<T> {
    @Getter
    protected CompositeBranch<T> hvSide;
    @Getter
    protected CompositeBranch<T> lvSide;

    protected CoupledCoils<T> coupledCoils;

    public SinglePhaseTransformer(String name) {
        super(name);
    }

    @Override
    public Collection<Equipment<T>> getEquipment() {
        List<Equipment<T>> equipment = new ArrayList<>(hvSide.getEquipment());
        equipment.addAll(lvSide.getEquipment());
        return equipment;
    }
}
