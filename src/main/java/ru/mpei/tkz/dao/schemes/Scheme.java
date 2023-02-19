package ru.mpei.tkz.dao.schemes;

import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldVector;
import ru.mpei.tkz.models.dto.MagneticallyCoupledInductors;
import ru.mpei.tkz.models.dto.SchemeMatrixDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.composite_equipment.CoupledCoils;

import java.util.Collection;

public interface Scheme<T extends FieldElement<T>> {
    /**
     * Adds complex equipment to scheme (it must be connected before adding)
     */
    void addEquipment(Equipment<T> equipment);
    /**
     * Adds array of equipment to scheme (it must be connected before adding)
     */
    void addEquipments(Equipment<T>... equipments);
    /**
     * Adds collection of equipment to scheme (it must be connected before adding)
     */
    void addEquipments(Collection<Equipment<T>> equipments);
    /**
     * Adds coupled equipment to scheme (single phase transformer, for example)
     * (it must be connected before adding)
     */
    void addCoupledEquipment(CoupledCoils<T> coupledCoils);
    void addCoupledEquipments(CoupledCoils<T>... coupledCoils);
    void addCoupledEquipments(Collection<CoupledCoils<T>> coupledCoils);
    /**
     * Returns unnecessary for scheme solving information
     */
    SchemeMatrixDto<T> getSchemeMatrices();
    /**
     * Updates potentials of all scheme nodes (need to be done before result currents
     * and voltages counting)
     * @param potentialsMatrix - result of solving scheme
     */
    void updateScheme(FieldVector<T> potentialsMatrix);
    /**
     * Magnetically couples two inductances
     * @param l1 - hv inductance, * will be at start node
     * @param l2 - lv inductance, * will be at end node
     */
    public void coupleInductances(Equipment<T> l1, Equipment<T> l2, T mutualInduction)  throws IllegalArgumentException;
    /**
     * Magnetically couples two inductances (mutual inductance can not be equal to any
     * inductance, otherwise -> singular matrix exception will be thrown during solving
     * @param data - data about coupled inductances
     * @throws IllegalArgumentException - inductances must be added at scheme before coupling
     */
    public void coupleInductances(MagneticallyCoupledInductors<T> data)  throws IllegalArgumentException;
}
