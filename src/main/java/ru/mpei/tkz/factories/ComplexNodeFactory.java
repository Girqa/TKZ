package ru.mpei.tkz.factories;

import org.apache.commons.math3.complex.Complex;
import ru.mpei.tkz.models.nodes.ComplexNode;
import ru.mpei.tkz.models.nodes.Node;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexGround;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseComplexNode;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseGround;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

import java.util.HashMap;
import java.util.Map;


public class ComplexNodeFactory implements NodesFactory<Complex> {
    private static final Node<Complex> ground = new ComplexNode("ground node");
    private static final Map<String, Node<Complex>> pointNodes = new HashMap();
    @Override
    public ThreePhaseNode<Complex> getThreePhaseNode(String name, Node<Complex> a, Node<Complex> b, Node<Complex> c) {
        return new ThreePhaseComplexNode(name, a, b, c);
    }

    @Override
    public Node<Complex> getNode(String name) {
        return new ComplexNode(name);
    }

    @Override
    public Node<Complex> getGround() {
        return ground;
    }

    @Override
    public ThreePhaseGround<Complex> getThreePhaseGround() {
        return new ThreePhaseComplexGround();
    }

    @Override
    public Node<Complex> getPointNode(String name) {
        if (!pointNodes.containsKey(name)) {
            pointNodes.put(name, new ComplexNode(name));
        }
        return pointNodes.get(name);
    }
}
