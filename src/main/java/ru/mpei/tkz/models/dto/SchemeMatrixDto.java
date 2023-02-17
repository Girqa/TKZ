package ru.mpei.tkz.models.dto;

import lombok.Getter;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;

public class SchemeMatrixDto<T extends FieldElement<T>> {
    @Getter
    private FieldMatrix<T> connectionsMatrix;
    @Getter
    private FieldMatrix<T> conductivityMatrix;
    @Getter
    private FieldVector<T> currentSourcesVector;
    @Getter
    private FieldVector<T> voltageSourcesVector;

    public SchemeMatrixDto(FieldMatrix<T> connectionsMatrix,
                           FieldMatrix<T> conductanceMatrix,
                           FieldVector<T> currentSourcesVector,
                           FieldVector<T> voltageSourcesVector) throws IllegalArgumentException {
        setConnectionsMatrix(connectionsMatrix);
        setConductivityMatrix(conductanceMatrix);
        setCurrentSourcesVector(currentSourcesVector);
        setVoltageSourcesVector(voltageSourcesVector);
    }

    public void setConnectionsMatrix(FieldMatrix<T> connectionsMatrix) throws IllegalArgumentException {
        if (connectionsMatrix.getRowDimension() == 1 || connectionsMatrix.getColumnDimension() == 1){
            throw new IllegalArgumentException("Connection matrix must be 2d matrix");
        }
        this.connectionsMatrix = connectionsMatrix;
    }

    public void setConductivityMatrix(FieldMatrix<T> conductivityMatrix) throws IllegalArgumentException {
        if (conductivityMatrix.getRowDimension() == 1 || conductivityMatrix.getColumnDimension() == 1){
            throw new IllegalArgumentException("Conductance matrix must be 2d matrix");
        }
        this.conductivityMatrix = conductivityMatrix;
    }

    public void setCurrentSourcesVector(FieldVector<T> currentSourcesVector) throws IllegalArgumentException {
        this.currentSourcesVector = currentSourcesVector;
    }

    public void setVoltageSourcesVector(FieldVector<T> voltageSourcesVector) throws IllegalArgumentException {
        this.voltageSourcesVector = voltageSourcesVector;
    }
}
