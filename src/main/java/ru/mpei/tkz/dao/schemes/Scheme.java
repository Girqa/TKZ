package ru.mpei.tkz.dao.schemes;

import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldVector;
import ru.mpei.tkz.models.dto.SchemeMatrixDto;
import ru.mpei.tkz.models.equipments.Equipment;

public interface Scheme<T extends FieldElement<T>> {
    void addEquipment(Equipment<T> equipment);
    void addEquipments(Equipment<T>... equipments);
    SchemeMatrixDto<T> getSchemeMatrices();
    void updateScheme(FieldVector<T> potentialsMatrix);
}
