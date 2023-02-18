package ru.mpei.tkz.models.equipments.composite_equipment;

import lombok.Data;
import org.apache.commons.math3.FieldElement;
import ru.mpei.tkz.models.dto.ThreePhaseDataDto;
import ru.mpei.tkz.models.nodes.three_phase_nodes.ThreePhaseNode;

@Data
public abstract class CompositeEquipment<T extends FieldElement<T>> {
    protected String name;
    protected String type;
    protected ThreePhaseNode<T> startNode;
    protected ThreePhaseNode<T> endNode;

    public abstract ThreePhaseDataDto<T> getCurrent();
    public abstract ThreePhaseDataDto<T> getVoltage();
}
