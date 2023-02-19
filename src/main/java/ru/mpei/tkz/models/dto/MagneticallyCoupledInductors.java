package ru.mpei.tkz.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.Equipment;

@Data
@AllArgsConstructor
public class MagneticallyCoupledInductors<T extends FieldElement<T>> {
    private Equipment<T> l1;
    private Equipment<T> l2;
    private T mutualInduction;
}
