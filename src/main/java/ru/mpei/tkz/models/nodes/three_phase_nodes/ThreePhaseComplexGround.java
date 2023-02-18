package ru.mpei.tkz.models.nodes.three_phase_nodes;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexNodeFactory;
import ru.mpei.tkz.factories.NodesFactory;

public class ThreePhaseComplexGround extends ThreePhaseGround<Complex>{

    public ThreePhaseComplexGround() {
        super(new ComplexNodeFactory());
        this.type = "three phase ground";
    }

    public ThreePhaseComplexGround(NodesFactory<Complex> nodesFactory) throws Exception {
        super(nodesFactory);
        throw new Exception("This constructor is not supported");
    }
}
