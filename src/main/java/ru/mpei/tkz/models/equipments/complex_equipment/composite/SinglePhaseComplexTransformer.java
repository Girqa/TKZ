package ru.mpei.tkz.models.equipments.complex_equipment.composite;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexResistance;
import ru.mpei.tkz.models.equipments.composite_equipment.SinglePhaseTransformer;

import java.util.List;

/**
 * Single phase transformer for complex network counting
 */
public class SinglePhaseComplexTransformer extends SinglePhaseTransformer<Complex> {

    /**
     * Creates single phase transformer with active loses
     * @param name - unique name of equipment
     * @param lHv - hv coil inductance
     * @param lLv - lv coil inductance
     * @param m - mutual induction between hv and lv coils
     * @param rHv - active resistance of hv branch
     * @param rLv - active resistance of lv branch
     */
    public SinglePhaseComplexTransformer(String name, double lHv, double lLv, double m, double rHv, double rLv) {
        super(name);
        this.coupledCoils = new ComplexCoupledCoils(name+"_coupled coils", lHv, lLv, m);
        this.xl1 = coupledCoils.getXl1();
        this.xl2 = coupledCoils.getXl2();
        this.mutualInduction = coupledCoils.getMutualInduction();
        ComplexResistance r1 = new ComplexResistance(name + "_hv", rHv);
        ComplexResistance r2 = new ComplexResistance(name + "_lv", rLv);
        Connections<Complex> connections = new Connections<>();
        connections.connectEquipment(r1, Side.END, xl1, Side.START);
        connections.connectEquipment(xl2, Side.END, r2, Side.START);
        this.hvSide = new CompositeBranch<>(name + "_hv", List.of(
                r1,
                this.xl1
        ));
        this.lvSide = new CompositeBranch<>(name + "_lv", List.of(
                this.xl2,
                r2
        ));

    }

    /**
     * Creates single phase transformer without active loses
     * @param name - unique name of equipment
     * @param l1 - hv coil inductance
     * @param l2 - lv coil inductance
     * @param m - mutual induction between hv and lv coils
     */
    public SinglePhaseComplexTransformer(String name, double l1, double l2, double m) {
        super(name);
        this.coupledCoils = new ComplexCoupledCoils(name+"_coupled coils", l1, l2, m);
        this.xl1 = coupledCoils.getXl1();
        this.xl2 = coupledCoils.getXl2();
        this.mutualInduction = coupledCoils.getMutualInduction();
        this.hvSide = new CompositeBranch<>(name + "_hv", List.of(
                this.xl1
        ));
        this.lvSide = new CompositeBranch<>(name + "_lv", List.of(
                this.xl2
        ));
    }
}
