package ru.mpei.tkz.factories;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.nodes.Node;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexNode;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

public class ComplexThreePhaseNodeFactory implements ThreePhaseNodesFactory<Complex> {
    @Override
    public ThreePhaseNode<Complex> getNode(String name, Node<Complex> a, Node<Complex> b, Node<Complex> c) {
        return new ThreePhaseComplexNode(name, a, b, c);
    }
}
