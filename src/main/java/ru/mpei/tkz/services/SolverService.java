package ru.mpei.tkz.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.FieldDecompositionSolver;
import org.apache.commons.math3.linear.FieldLUDecomposition;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import ru.mpei.tkz.dao.schemes.Scheme;
import ru.mpei.tkz.models.dto.SchemeMatrixDto;

@Slf4j
public class SolverService<T extends FieldElement<T>> {
    public FieldVector<T> solveScheme(Scheme<T> scheme) {
        SchemeMatrixDto<T> data = scheme.getSchemeMatrices();
        FieldMatrix<T> A = data.getConnectionsMatrix();
        FieldMatrix<T> Y = data.getConductivityMatrix();
        FieldVector<T> J = data.getCurrentSourcesVector();
        FieldVector<T> E = data.getVoltageSourcesVector();

        // node's conductivity matrix
        FieldMatrix<T> nodeConductivity = A.multiply(Y).multiply(A.transpose());
        // node's current matrix
        FieldVector<T> nodeCurrent = A.operate(J.add(Y.operate(E)));
        // solve the equation
        FieldDecompositionSolver<T> solver = new FieldLUDecomposition<>(nodeConductivity).getSolver();
        if (!solver.isNonSingular()) {
            log.warn("Matrix is singular!");
        }
        return solver.solve(nodeCurrent);
    }
}
