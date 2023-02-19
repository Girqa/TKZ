package ru.mpei.tkz.dao.schemes;

import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldVector;
import ru.mpei.tkz.models.dto.MagneticallyCoupledInductors;
import ru.mpei.tkz.models.dto.SchemeMatrixDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.base_equipment.Inductance;

import java.util.Collection;

public interface Scheme<T extends FieldElement<T>> {
    void addEquipment(Equipment<T> equipment);
    void addEquipments(Equipment<T>... equipments);
    void addEquipments(Collection<Equipment<T>> equipments);
    SchemeMatrixDto<T> getSchemeMatrices();
    void updateScheme(FieldVector<T> potentialsMatrix);
    public void coupleInductances(Inductance<T> l1, Inductance<T> l2, T mutualInduction)  throws IllegalArgumentException;
    public void coupleInductances(MagneticallyCoupledInductors<T> data)  throws IllegalArgumentException;
}
