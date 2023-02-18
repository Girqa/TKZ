package ru.mpei.tkz.models.nodes.three_phase_nodes;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.nodes.Node;

public class ThreePhaseComplexNode extends ThreePhaseNode<Complex> {
    public ThreePhaseComplexNode(String name, Node<Complex> nA, Node<Complex> nB, Node<Complex> nC) {
        super(name, nA, nB, nC);
        this.type = "three phase complex node";
    }
}
