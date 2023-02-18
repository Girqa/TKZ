package ru.mpei.tkz.factories;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexNode;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.HashMap;
import java.util.Map;


public class ComplexNodeFactory implements NodesFactory<Complex> {
    private static final Map<String, Node<Complex>> nodesPull = new HashMap<>();
    @Override
    public ThreePhaseNode<Complex> getThreePhaseNode(String name, Node<Complex> a, Node<Complex> b, Node<Complex> c) {
        return new ThreePhaseComplexNode(name, a, b, c);
    }

    @Override
    public Node<Complex> getNode(String name) {
        if (!nodesPull.containsKey(name)) {
            nodesPull.put(name, new ComplexNode(name));
        }
        return nodesPull.get(name);
    }
}
