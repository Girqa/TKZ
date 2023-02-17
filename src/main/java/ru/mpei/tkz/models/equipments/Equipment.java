package ru.mpei.tkz.models.equipments;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.nodes.Node;


/**
 * Base equipment (with two connection nodes)
 * @param <T>
 */
@EqualsAndHashCode
@ToString
public abstract class Equipment<T extends FieldElement<T>> {
    protected static final double TOLERANCE = 10E-10;
    @Getter
    protected String type;
    @Getter
    protected String name;
    @Getter
    protected Node<T> startNode;
    @Getter
    protected Node<T> endNode;

    protected T resistance;
    public Equipment(String name, Node<T> startNode, Node<T> endNode) {
        if (startNode.equals(endNode)) {
            throw new IllegalArgumentException("Short circuit on element!");
        }
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Counts equipment voltage in volts
     * @return voltage between start and end nodes
     */
    public T getEquipmentVoltage() {
        // [(fi+) - (fi-)]
        T startPotential = startNode.getPotential();
        T endPotential = endNode.getPotential();
        return startPotential.subtract(endPotential);
    }

    /**
     * Counts equipment current in amperes
     */
    public T getEquipmentCurrent() {
        // [(fi+) - (fi-)] / z
        return getEquipmentVoltage().divide(resistance);
    }

    /**
     * Counts equipment resistance
     */
    public T getEquipmentResistance() {
        return resistance;
    }
}
