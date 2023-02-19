package ru.mpei.tkz.models.equipments;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.dao.schemes.Scheme;
import ru.mpei.tkz.models.connections.Connections;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexVoltageSource;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.SinglePhaseComplexTransformer;
import ru.mpei.tkz.services.SolverService;

import static ru.mpei.tkz.models.Helpers.phForm;

public class SinglePhaseTransformerTest {

    @Test
    public void transformerSupplyingTest() {
        SinglePhaseComplexTransformer transformer = new SinglePhaseComplexTransformer(
                "transformer",
                3.183, 12.732, 3.8197,
                200,
                1800
        );
        ComplexVoltageSource eds = new ComplexVoltageSource("eds", 283.5, -140);

        Connections<Complex> connections = new Connections<>();
        connections.connectEquipment(eds, Side.END, transformer.getHvSide(), Side.START);
        connections.connectEquipment(transformer.getHvSide(), Side.END, eds, Side.START);
        connections.connectEquipment(transformer.getLvSide(), Side.END, transformer.getLvSide(), Side.START);

        Scheme<Complex> scheme = new ComplexScheme();
        scheme.addEquipments(eds);
        scheme.addCoupledEquipment(transformer);

        SolverService<Complex> solver = new SolverService<>();
        scheme.updateScheme(solver.solveScheme(scheme));

        System.out.println(phForm(transformer.getHvSide().getEquipmentVoltage()));
        System.out.println(phForm(transformer.getHvSide().getEquipmentCurrent()));
        System.out.println(phForm(transformer.getLvSide().getEquipmentCurrent()));
    }
}
