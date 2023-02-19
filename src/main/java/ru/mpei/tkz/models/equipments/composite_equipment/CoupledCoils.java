package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.MagneticallyCoupledInductors;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.base_equipment.Inductance;

import java.util.Collection;

/**
 * Base inductive transformer element. IMPORTANT: low-voltage network must contain at least 1 node
 * from high-voltage network (for example - ground).
 * More over, mutual inductance != l1 != l2 - otherwise -> problems
 * @param <T>
 */
@Data
public abstract class CoupledCoils<T extends FieldElement<T>> {
    protected Inductance<T> xl1;
    protected Inductance<T> xl2;
    protected String name;
    protected T mutualInduction;

    public CoupledCoils(String name) {
        this.name = name;
    }

    /**
     * Returns necessary for modeling data about coupled inductances (which coils
     * are coupled and their mutual inductance)
     */
    public MagneticallyCoupledInductors<T> getCoupledInductances() {
        return new MagneticallyCoupledInductors<>(xl1, xl2, mutualInduction);
    }

    /**
     * Returns equipment contained in transformer
     */
    public abstract Collection<Equipment<T>> getEquipment();
}
