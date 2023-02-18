package ru.mpei.tkz.models.equipments.base_equipment;

import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.nodes.Node;

public abstract class VoltageSource<T extends FieldElement<T>> extends Equipment<T> {
    public VoltageSource(String name, Node<T> startNode, Node<T> endNode) {
        super(name, startNode, endNode);
    }
}
