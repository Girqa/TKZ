package ru.mpei.tkz.models.nodes;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.math3.FieldElement;

import java.util.Objects;

@ToString
public abstract class Node<T extends FieldElement<T>> {
    @Getter
    protected String name;
    @Getter@Setter
    protected T potential;

    public Node(String name) {
        this.name = name;
    }

    public abstract int getNodesCount();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?> node)) return false;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
