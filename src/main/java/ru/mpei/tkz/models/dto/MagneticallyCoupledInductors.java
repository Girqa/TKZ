package ru.mpei.tkz.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.base_equipment.Inductance;

@Data
@AllArgsConstructor
public class MagneticallyCoupledInductors<T extends FieldElement<T>> {
    private Inductance<T> l1;
    private Inductance<T> l2;
    private T mutualInduction;
}
