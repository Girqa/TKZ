package ru.mpei.tkz.models.nodes;

import org.apache.commons.math3.complex.Complex;

public class ComplexNode extends Node<Complex> {
    private static int nodesCount = 0;
    public ComplexNode(String name) {
        super(name);
        this.potential = Complex.ZERO;
        nodesCount++;
    }

    @Override
    public int getNodesCount() {
        return nodesCount;
    }
}
