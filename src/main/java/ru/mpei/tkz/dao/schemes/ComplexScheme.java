package ru.mpei.tkz.dao.schemes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.*;
import ru.mpei.tkz.models.dto.SchemeMatrixDto;
import ru.mpei.tkz.models.equipments.Equipment;
import ru.mpei.tkz.models.equipments.complex_equipment.ComplexInductance;
import ru.mpei.tkz.models.nodes.Node;

import java.util.*;

@ToString
public class ComplexScheme implements Scheme<Complex> {
    private final Map<Equipment<Complex>, Integer> indexesByEquipments;
    private final Map<Node<Complex>, Integer> indexesByNodes;

    private final List<MutualInductanceConnection> mutualInductanceConnections;

    public ComplexScheme() {
        indexesByEquipments = new HashMap<>();
        indexesByNodes = new HashMap<>();
        mutualInductanceConnections = new ArrayList<>();
    }

    public ComplexScheme(Iterable<Equipment<Complex>> equipments,
                         Iterable<Node<Complex>> nodes) {
        this();
        // saves indexes of equipments from 0 to n - rows
        for (Equipment<Complex> equipment: equipments) {
            indexesByEquipments.put(equipment, indexesByEquipments.size());
        }
        // saves indexes of equipments from 0 to m - columns
        for (Node<Complex> node: nodes) {
            indexesByNodes.put(node, indexesByNodes.size());
        }
    }

    @Override
    public void addEquipment(Equipment<Complex> equipment) {
        if (!indexesByEquipments.containsKey(equipment)) {
            indexesByEquipments.put(equipment, indexesByEquipments.size());
            Node<Complex> startNode = equipment.getStartNode();
            Node<Complex> endNode = equipment.getEndNode();
            if (!indexesByNodes.containsKey(startNode)) {
                indexesByNodes.put(equipment.getStartNode(), indexesByNodes.size());
            }
            if (!indexesByNodes.containsKey(endNode)) {
                indexesByNodes.put(equipment.getEndNode(), indexesByNodes.size());
            }
        }
    }

    @Override
    public void addEquipments(Equipment<Complex>... equipments) {
        for (Equipment<Complex> equipment: equipments) {
            addEquipment(equipment);
        }
    }

    @Override
    public void addEquipments(Collection<Equipment<Complex>> equipments) {
        equipments.forEach(this::addEquipment);
    }

    @Override
    public void updateScheme(FieldVector<Complex> potentialsMatrix) {
        for (Node<Complex> node: indexesByNodes.keySet()) {
            int i = indexesByNodes.get(node);
            if (i == indexesByNodes.size() - 1) continue;
            node.setPotential(potentialsMatrix.getEntry(
                    indexesByNodes.get(node)
            ));
        }
    }

    @Override
    public SchemeMatrixDto<Complex> getSchemeMatrices() {
        cleanNodesPotentials();
        int nodes = indexesByNodes.size();
        int branches = indexesByEquipments.size();
        FieldMatrix<Complex> A = new Array2DRowFieldMatrix<>(Complex.ZERO.getField(), nodes-1, branches);
        FieldMatrix<Complex> Z = new Array2DRowFieldMatrix<>(Complex.ZERO.getField(), branches, branches);
        FieldVector<Complex> J = new ArrayFieldVector<>(Complex.ZERO.getField(), branches);
        FieldVector<Complex> E = new ArrayFieldVector<>(Complex.ZERO.getField(), branches);

        for (Equipment<Complex> equipment: indexesByEquipments.keySet()) {
            int i = indexesByEquipments.get(equipment);  // selected matrix's row
            Z.setEntry(i, i, equipment.getEquipmentResistance());
            J.setEntry(i, equipment.getEquipmentCurrent());
            E.setEntry(i, equipment.getEquipmentVoltage());
            // Fill connections matrix
            for (Node<Complex> node: indexesByNodes.keySet()) {
                int j = indexesByNodes.get(node);  // selected matrix's column
                if (j == nodes - 1) continue;  // TODO: passes last node - it will have zero potential
                if (equipment.getStartNode().equals(node)) {
                    // if current flows from node -> -1
                    A.setEntry(j, i, Complex.ONE.negate());
                } else if (equipment.getEndNode().equals(node)) {
                    // if current flows to node -> 1
                    A.setEntry(j, i, Complex.ONE);
                } else {
                    A.setEntry(j, i, Complex.ZERO);
                }
            }
        }
        // fills non-diagonal resistances between inductively connected inductances
        for (MutualInductanceConnection mic: mutualInductanceConnections) {
            Z.setEntry(mic.i, mic.j, mic.mutualInductance);
            Z.setEntry(mic.j, mic.i, mic.mutualInductance);
        }
        // Gets reversed matrix from Z -> Y
        FieldMatrix<Complex> Y = new FieldLUDecomposition<>(Z)
                .getSolver()
                .getInverse();
        return new SchemeMatrixDto<>(A, Y, J, E);
    }

    /**
     * Magnetically binds two inductances
     * @param l1 - hv inductance, * will be at start node
     * @param l2 - lv inductance, * will be at end node
     */
    public void bindInductances(ComplexInductance l1, ComplexInductance l2, Complex mutualInduction)  throws IllegalArgumentException {
        if (!indexesByEquipments.containsKey(l1)) {
            throw new IllegalArgumentException("Scheme doesn't contain l1 equipment");
        }
        if (!indexesByEquipments.containsKey(l2)) {
            throw new IllegalArgumentException("Scheme doesn't contain l2 equipment");
        }
        mutualInductanceConnections.add(
                new MutualInductanceConnection(
                        indexesByEquipments.get(l1),
                        indexesByEquipments.get(l2),
                        mutualInduction)
        );
    }

    private void cleanNodesPotentials() {
        for (Node<Complex> node: indexesByNodes.keySet()) {
            node.setPotential(Complex.ZERO);
        }
    }

    private void showMatrix(FieldMatrix<Complex> matrix) {
        int r = matrix.getRowDimension();
        int c = matrix.getColumnDimension();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.printf("%f + j %f |", matrix.getEntry(i, j).getReal(), matrix.getEntry(i, j).getImaginary());
            }
            System.out.println();
        }
    }

    @Data
    @AllArgsConstructor
    private class MutualInductanceConnection {
        private int i;
        private int j;
        private Complex mutualInductance;
    }
}
