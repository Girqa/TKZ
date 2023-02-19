package ru.mpei.tkz.models.nodes.three_phase_nodes;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.factories.ComplexNodeFactory;
import ru.mpei.tkz.factories.NodesFactory;

public class ThreePhaseComplexPointNode extends ThreePhasePointNode<Complex> {

    public ThreePhaseComplexPointNode(String name) {
        super(name, new ComplexNodeFactory());
    }

    public ThreePhaseComplexPointNode(String name, NodesFactory<Complex> nodesFactory) throws Exception {
        super(name, nodesFactory);
        throw new Exception("This constructor is not supported");
    }
}
