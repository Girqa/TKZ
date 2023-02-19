package ru.mpei.tkz.models.nodes.three_phase_nodes;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.NodesFactory;
import ru.mpei.tkz.models.nodes.Node;

public abstract class ThreePhaseGround<T extends FieldElement<T>> extends ThreePhaseNode<T> {

    /**
     * Creates ground three-phase node
     */
    public ThreePhaseGround(NodesFactory<T> nodesFactory) {
        super("ground node",
                nodesFactory.getGround(),
                nodesFactory.getGround(),
                nodesFactory.getGround());
    }

    public ThreePhaseGround(String name, Node<T> nA, Node<T> nB, Node<T> nC) throws Exception {
        super(name, nA, nB, nC);
        throw new Exception("This constructor is not supported!");
    }
}
