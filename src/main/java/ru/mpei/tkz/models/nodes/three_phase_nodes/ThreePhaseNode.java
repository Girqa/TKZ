package ru.mpei.tkz.models.nodes.three_phase_nodes;

import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.nodes.Node;

@Data
public abstract class ThreePhaseNode <T extends FieldElement<T>> {
    protected String name;
    protected String type;
    protected Node<T> nA;
    protected Node<T> nB;
    protected Node<T> nC;

    public ThreePhaseNode(String name, Node<T> nA, Node<T> nB, Node<T> nC) {
        this.nA = nA;
        this.nB = nB;
        this.nC = nC;
        this.name = name;
    }
}
