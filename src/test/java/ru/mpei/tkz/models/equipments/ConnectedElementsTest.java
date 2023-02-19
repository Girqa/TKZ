package ru.mpei.tkz.models.equipments;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.models.connections.Connection;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexInductance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexResistance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexVoltageSource;
import ru.mpei.tkz.services.SolverService;

public class ConnectedElementsTest {
    ComplexVoltageSource e;
    ComplexResistance r;
    ComplexInductance xl;
    ComplexScheme scheme;

    @BeforeEach
    public void init() {
        e = new ComplexVoltageSource("e", 10, 0);
        r = new ComplexResistance("r", 5);
        xl = new ComplexInductance("xl", 0.0159);
        scheme = new ComplexScheme();
    }

    @AfterEach
    public void test() {
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
    public void ELRSimpleTest() {
        Connection<Complex> con1 = new Connection<>(e.getEndNode());
        con1.connectEquipment(r, r.getStartNode());

        Connection<Complex> con2 = new Connection<>(r.getEndNode());
        con2.connectEquipment(xl, xl.getStartNode());

        Connection<Complex> con3 = new Connection<>(xl.getEndNode());
        con3.connectEquipment(e, e.getStartNode());

        scheme.addEquipments(e, r, xl);

        SolverService<Complex> solver = new SolverService<>();
        FieldVector<Complex> solution = solver.solveScheme(scheme);
        scheme.updateScheme(solution);
    }

    @Test
    public void ELRSimpleWithConnectionsTest() {
        Connections<Complex> connections = new Connections<>();
        connections.connectEquipment(e, Side.END, r, Side.START);
        connections.connectEquipment(r, Side.END, xl, Side.START);
        connections.connectEquipment(xl, Side.END, e, Side.START);

        scheme.addEquipments(e, r, xl);

        SolverService<Complex> solver = new SolverService<>();
        FieldVector<Complex> solution = solver.solveScheme(scheme);
        scheme.updateScheme(solution);
    }
}
