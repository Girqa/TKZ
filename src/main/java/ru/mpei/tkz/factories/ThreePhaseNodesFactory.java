package ru.mpei.tkz.factories;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.nodes.Node;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

public interface ThreePhaseNodesFactory<T extends FieldElement<T>> {
    ThreePhaseNode<T> getNode(String name, Node<T> a, Node<T> b, Node<T> c);
}
