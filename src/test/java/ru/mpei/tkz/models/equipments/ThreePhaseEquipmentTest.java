package ru.mpei.tkz.models.equipments;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.dao.schemes.Scheme;
import ru.mpei.tkz.models.connections.ThreePhaseConnections;
import ru.mpei.tkz.models.enums.LoadType;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.composite_equipment.load.ThreePhaseComplexLoad;
import ru.mpei.tkz.models.equipments.composite_equipment.voltage_sources.ThreePhaseComplexVoltageSource;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexGround;
import ru.mpei.tkz.services.SolverService;

public class ThreePhaseEquipmentTest {

    @Test
    public void simpleThreePhaseEDSWithLoad() {
        ThreePhaseComplexVoltageSource e = new ThreePhaseComplexVoltageSource("e", 220, 0);
        ThreePhaseComplexLoad load = new ThreePhaseComplexLoad("load", 10, 0, 0.0318, LoadType.RL);
        ThreePhaseComplexGround ground = new ThreePhaseComplexGround();

        ThreePhaseConnections<Complex> connections = new ThreePhaseConnections<>();
        connections.connectEquipment(ground, e, Side.START);
        connections.connectEquipment(e, Side.END, load, Side.START);
        connections.connectEquipment(ground, load, Side.END);

        Scheme<Complex> scheme = new ComplexScheme();

        scheme.addEquipments(e.getEquipment());
        scheme.addEquipments(load.getEquipment());

        SolverService<Complex> solverService = new SolverService<>();
        long start = System.currentTimeMillis();
        FieldVector<Complex> solution = solverService.solveScheme(scheme);
        scheme.updateScheme(solution);
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
        System.out.println(load.getCurrent());
    }
}
