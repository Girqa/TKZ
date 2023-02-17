package ru.mpei.tkz.models.nodes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.math3.FieldElement;

@ToString
@EqualsAndHashCode
public abstract class Node<T extends FieldElement<T>> {
    @Getter
    protected String name;
    @Getter@Setter
    protected T potential;

    public Node(String name) {
        this.name = name;
    }

    public abstract int getNodesCount();
}
