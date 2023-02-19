package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.MagneticallyCoupledInductors;
import ru.mpei.tkz.models.equipments.base_equipment.Inductance;
import ru.mpei.tkz.models.nodes.Node;

/**
 * Base inductive transformer element. IMPORTANT: low-voltage network must contain at least 1 node
 * from high-voltage network (for example - ground).
 * More over, mutual inductance != l1 != l2 - otherwise -> problems
 * @param <T>
 */
@Data
public abstract class SimpleTransformer<T extends FieldElement<T>> {
    protected Inductance<T> xl1;
    protected Inductance<T> xl2;
    protected T mutualInduction;

    public SimpleTransformer(String name, double l1, double l2, double m) {
    }

    public MagneticallyCoupledInductors<T> getCoupledInductances() {
        return new MagneticallyCoupledInductors<>(xl1, xl2, mutualInduction);
    }
}
