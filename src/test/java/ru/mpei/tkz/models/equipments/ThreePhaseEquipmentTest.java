package ru.mpei.tkz.models.equipments;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.junit.jupiter.api.Test;
import ru.mpei.tkz.dao.schemes.ComplexScheme;
import ru.mpei.tkz.dao.schemes.Scheme;
import ru.mpei.tkz.models.connections.ThreePhaseConnections;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.enums.LoadType;
import ru.mpei.tkz.models.enums.Side;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.ThreePhaseComplexLoad;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.ThreePhaseComplexVoltageSource;
import ru.mpei.tkz.models.equipments.complex_equipment.composite.ThreePhaseComplexYgDTransformer;
import ru.mpei.tkz.models.equipments.composite_equipment.CoupledCoils;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexGround;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexPointNode;
import ru.mpei.tkz.services.SolverService;

import static org.junit.jupiter.api.Assertions.*;
public class ThreePhaseEquipmentTest {

    @Test
    public void simpleThreePhaseEDSWithLoad() {
        ThreePhaseComplexVoltageSource e = new ThreePhaseComplexVoltageSource("e", 220, 0);
        ThreePhaseComplexLoad load1 = new ThreePhaseComplexLoad("load1", 10, 0.0003183, 0, LoadType.RC);
        ThreePhaseComplexGround ground = new ThreePhaseComplexGround();
        ThreePhaseComplexPointNode pointNode = new ThreePhaseComplexPointNode("p1");

        ThreePhaseConnections<Complex> connections = new ThreePhaseConnections<>();
        connections.connectEquipment(ground, e, Side.START);
        connections.connectEquipment(e, Side.END, load1, Side.START);
        connections.connectEquipment(pointNode, load1, Side.END);

        Scheme<Complex> scheme = new ComplexScheme();

        scheme.addEquipments(e.getEquipment());
        scheme.addEquipments(load1.getEquipment());

        SolverService<Complex> solverService = new SolverService<>();
        FieldVector<Complex> solution = solverService.solveScheme(scheme);
        scheme.updateScheme(solution);

        // Almost equals currents
        ThreePhaseDataDto<Complex> eCurrents = e.getCurrent();
        ThreePhaseDataDto<Complex> loadCurrents = load1.getCurrent();
        assertEquals(round(eCurrents.getDataA()), round(loadCurrents.getDataA()));
        assertEquals(round(eCurrents.getDataB()), round(loadCurrents.getDataB()));
        assertEquals(round(eCurrents.getDataC()), round(loadCurrents.getDataC()));
        // Almost equals voltages
        ThreePhaseDataDto<Complex> eVoltages = e.getVoltage();
        ThreePhaseDataDto<Complex> loadVoltages = load1.getVoltage();
        assertEquals(round(eVoltages.getDataA()), round(loadVoltages.getDataA()));
        assertEquals(round(eVoltages.getDataB()), round(loadVoltages.getDataB()));
        assertEquals(round(eVoltages.getDataC()), round(loadVoltages.getDataC()));
        // Check hands counted values
        assertEquals(new Complex(11, 11), round(loadCurrents.getDataA()));
        assertEquals(new Complex(4.026, -15.026), round(loadCurrents.getDataB()));
        assertEquals(new Complex(-15.026, 4.026), round(loadCurrents.getDataC()));
    }

    @Test
    public void schemeWithThreePhaseTransformer() {
        ThreePhaseComplexVoltageSource e = new ThreePhaseComplexVoltageSource("e", 2200, 0);
        ThreePhaseComplexLoad load = new ThreePhaseComplexLoad("load", 100, 0, 0, LoadType.R);
        ThreePhaseComplexYgDTransformer transformer = new ThreePhaseComplexYgDTransformer(
                "transformer",
                3.183, 12.732, 3.8197,
                200,
                1800
        );
        ThreePhaseComplexGround ground = new ThreePhaseComplexGround();

        ThreePhaseConnections<Complex> connections = new ThreePhaseConnections<>();
        connections.connectEquipment(ground, e, Side.START);
        connections.connectEquipment(e, Side.END, transformer, Side.START);
        connections.connectEquipment(transformer, Side.END, load, Side.START);
        connections.connectEquipment(ground, load, Side.END);

        Scheme<Complex> scheme = new ComplexScheme();

        scheme.addEquipments(e.getEquipment());
        scheme.addEquipments(load.getEquipment());
        scheme.addCoupledEquipments(transformer.getSinglePhaseTransformers().toArray(new CoupledCoils[0]));


        long start = System.currentTimeMillis();
        SolverService<Complex> solverService = new SolverService<>();
        FieldVector<Complex> solution = solverService.solveScheme(scheme);
        scheme.updateScheme(solution);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(transformer.getHvVoltage());
        System.out.println(transformer.getLvVoltage());
    }

    private Complex round(Complex val) {
        double meaningSing = 1000.;
        double real = Math.round(val.getReal() * meaningSing) / meaningSing;
        double imag = Math.round(val.getImaginary() * meaningSing) / meaningSing;
        return new Complex(real, imag);
    }
}
