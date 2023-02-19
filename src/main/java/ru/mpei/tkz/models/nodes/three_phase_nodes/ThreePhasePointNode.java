package ru.mpei.tkz.models.nodes.three_phase_nodes;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.factories.NodesFactory;
import ru.mpei.tkz.models.nodes.Node;

public class ThreePhasePointNode<T extends FieldElement<T>> extends ThreePhaseNode<T>{

    /**
     * Creates point three-phase node
     */
    public ThreePhasePointNode(String name, NodesFactory<T> nodesFactory) {
        super(name,
                nodesFactory.getPointNode(name),
                nodesFactory.getPointNode(name),
                nodesFactory.getPointNode(name));
    }

    public ThreePhasePointNode(String name, Node<T> nA, Node<T> nB, Node<T> nC) throws Exception {
        super(name, nA, nB, nC);
        throw new Exception("This constructor is not supported!");
    }
}
