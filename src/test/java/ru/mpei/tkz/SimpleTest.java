package ru.mpei.tkz;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexInductance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexResistance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexVoltageSource;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.services.SolverService;

public class SimpleTest {

    @Test
    public void ELRSimpleTest() {
        ComplexNode n0 = new ComplexNode("n0");
        ComplexNode n1 = new ComplexNode("n1");
        ComplexNode n2 = new ComplexNode("n2");
        ComplexVoltageSource e = new ComplexVoltageSource("e", n0, n1, 10, 0);
        ComplexResistance r = new ComplexResistance("r", n1, n2, 5);
        ComplexInductance xl = new ComplexInductance("xl", n2, n0, 0.0159);

        ComplexScheme scheme = new ComplexScheme();
        scheme.addEquipment(e);
        scheme.addEquipment(r);
        scheme.addEquipment(xl);

        SolverService<Complex> solver = new SolverService<>();
        FieldVector<Complex> solution = solver.solveScheme(scheme);
        scheme.updateScheme(solution);

        Complex I = xl.getEquipmentCurrent();
        Complex nearI = new Complex(Math.round(I.getReal()), Math.round(I.getImaginary()));
        Assertions.assertEquals(new Complex(1, -1), nearI);

        I = r.getEquipmentCurrent();
        nearI = new Complex(Math.round(I.getReal()), Math.round(I.getImaginary()));
        Assertions.assertEquals(new Complex(1, -1), nearI);

        I = e.getEquipmentCurrent();
        nearI = new Complex(Math.round(I.getReal()), Math.round(I.getImaginary()));
        Assertions.assertEquals(new Complex(1, -1), nearI);
    }

    @Test
    public void schemeWithTransformer() {
        ComplexNode n1 = new ComplexNode("n1");
        ComplexNode n2 = new ComplexNode("n2");
        ComplexNode n3 = new ComplexNode("n3");
        ComplexNode n4 = new ComplexNode("n4");
        ComplexNode n5 = new ComplexNode("n5");

        ComplexVoltageSource e = new ComplexVoltageSource("e", n1, n2, 100, 0);
        ComplexResistance r1 = new ComplexResistance("r1", n2, n3, 10);
        ComplexResistance r2 = new ComplexResistance("r2", n5, n4, 10);
        ComplexInductance xl1 = new ComplexInductance("xl1", n3, n1, 0.0318);
        ComplexInductance xl2 = new ComplexInductance("xl2", n4, n5, 0.0318);

        ComplexScheme scheme = new ComplexScheme();
        scheme.addEquipment(e);
        scheme.addEquipment(r1);
        scheme.addEquipment(r2);
        scheme.addEquipment(xl1);
        scheme.addEquipment(xl2);
        scheme.bindInductances(xl1, xl2, new Complex(0, 10));

        SolverService<Complex> solver = new SolverService<>();
        FieldVector<Complex> solution = solver.solveScheme(scheme);
        scheme.updateScheme(solution);

        System.out.println(translateComplex(r1.getEquipmentCurrent()));
        System.out.println(translateComplex(r2.getEquipmentCurrent()));
    }

    private void showVector(FieldVector<Complex> vector) {
        int i = 0;
        for (Complex val: vector.toArray()) {
            System.out.printf("%d: %f + j%f; ", i, val.getReal(), val.getImaginary());
            i++;
        }
    }

    private String translateComplex(Complex val) {
        return Math.pow(Math.pow(val.getReal(), 2) + Math.pow(val.getImaginary(), 2), 0.5) + "∠" + Math.toDegrees(val.getArgument());
    }
}
