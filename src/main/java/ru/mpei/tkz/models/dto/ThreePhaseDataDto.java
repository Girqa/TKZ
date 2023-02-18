package ru.mpei.tkz.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.FieldElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreePhaseDataDto<T extends FieldElement<T>> {
    private T dataA;
    private T dataB;
    private T dataC;
}
