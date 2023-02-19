package ru.mpei.tkz.models;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.dao.schemes.Scheme;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.base_equipment.Resistance;
import ru.mpei.tkz.models.equipments.base_equipment.VoltageSource;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexResistance;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexVoltageSource;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.SimpleComplexTransformer;
import ru.mpei.tkz.models.equipments.composite_equipment.SimpleTransformer;
import ru.mpei.tkz.services.SolverService;

import static ru.mpei.tkz.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTransformerTest {

    @Test
    public void transformerWithEdsTest() {
        SimpleTransformer<Complex> transformer = new SimpleComplexTransformer("t", 3.183, 12.732, 3.8197);
        VoltageSource<Complex> eds = new ComplexVoltageSource("eds", 283.5, -140);
        Resistance<Complex> r1 = new ComplexResistance("r1", 200);
        Resistance<Complex> r2 = new ComplexResistance("r2", 1800);

        Connections<Complex> connections = new Connections<>();

        connections.connectEquipment(eds, Side.START, transformer.getXl1(), Side.END);
        connections.connectEquipment(eds, Side.END, r1, Side.START);
        connections.connectEquipment(r1, Side.END, transformer.getXl1(), Side.START);

        connections.connectEquipment(transformer.getXl2(), Side.START, r2, Side.END);
        connections.connectEquipment(transformer.getXl2(), Side.END, r2, Side.START);

        Scheme<Complex> scheme = new ComplexScheme();
        scheme.addEquipments(eds, transformer.getXl1(), transformer.getXl2(), r1, r2);
        scheme.coupleInductances(transformer.getCoupledInductances());

        SolverService<Complex> solver = new SolverService<>();
        scheme.updateScheme(solver.solveScheme(scheme));

        System.out.println(phForm(transformer.getXl1().getEquipmentVoltage()));
        System.out.println(phForm(r1.getEquipmentCurrent()));
        System.out.println(phForm(transformer.getXl1().getEquipmentCurrent()));
        System.out.println(phForm(r2.getEquipmentCurrent()));
        System.out.println(phForm(transformer.getXl2().getEquipmentCurrent()));
        assertEquals(round(r1.getEquipmentCurrent()), round(eds.getEquipmentCurrent()));
        assertEquals(round(eds.getEquipmentVoltage()), round(r1.getEquipmentVoltage().add(transformer.getXl1().getEquipmentVoltage())));
        assertEquals(round(r1.getEquipmentCurrent()), round(transformer.getXl1().getEquipmentCurrent()));
        assertEquals(round(r2.getEquipmentCurrent()), round(transformer.getXl2().getEquipmentCurrent()));


    }
}
