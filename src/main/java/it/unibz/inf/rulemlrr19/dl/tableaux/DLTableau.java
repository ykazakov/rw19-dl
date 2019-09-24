package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLRole;

/**
 * A tableau that represent a state of a model construction by the tableau
 * procedure. See Definition 1 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
public class DLTableau {

	/**
	 * The set of nodes of a tableau
	 */
	private final Set<Integer> nodes_ = new HashSet<>();

	/**
	 * The mapping assigning to a node the set of concepts in the label of this
	 * node. If the mapping does not appear in the map for some node this means
	 * that the respective set of concepts is empty.
	 */
	private final Map<Integer, Set<DLConcept>> nodeLabels_ = new HashMap<>();

	/**
	 * The mapping that assigns to a node the map from roles to nodes that are
	 * connected to the original node and has this role in the label of the edge
	 * between these nodes
	 */
	private final Map<Integer, Map<DLRole, Set<Integer>>> successors_ = new HashMap<>();

	/**
	 * The next fresh node that does not appear yet in the tableau
	 */
	private int nextFreshNode_ = 0;

	/**
	 * how many times ⊥ appears in the tableau
	 */
	private int clashCount_ = 0;

	/**
	 * @return the set of nodes of this tableau
	 */
	public Set<Integer> getNodes() {
		return Collections.unmodifiableSet(nodes_);
	}

	/**
	 * @param node
	 * @return the set of concepts in the label of the given node
	 */
	public Set<DLConcept> getNodeLabels(int node) {
		checkValidNode(node);
		Set<DLConcept> result = nodeLabels_.get(node);
		if (result == null) {
			return Collections.emptySet();
		}
		// else
		return Collections.unmodifiableSet(result);
	}

	/**
	 * @param node
	 * @param role
	 * @return the set of nodes that are connected to the given node by an edge
	 *         that contains the given role in the label
	 */
	public Set<Integer> getSuccessors(int node, DLRole role) {
		checkValidNode(node);
		Map<DLRole, Set<Integer>> nodeSuccessors = successors_.get(node);
		if (nodeSuccessors == null) {
			return Collections.emptySet();
		}
		// else
		Set<Integer> result = nodeSuccessors.get(role);
		if (result == null) {
			return Collections.emptySet();
		}
		// else
		return Collections.unmodifiableSet(result);
	}

	/**
	 * @return {@code true} if ⊥ (the clash) appears in the label of some node
	 *         of this tableau and {@code false} if this tableau is clash-free
	 */
	public boolean hasClash() {
		return clashCount_ > 0;
	}

	void apply(DLTableauModification mod) {
		mod.accept(new DLTableauModification.Visitor() {

			@Override
			public void visit(DLTableauEdgeLabelAddition mod) {
				addLabel(mod.getNodeFrom(), mod.getLabel(), mod.getNodeTo());
			}

			@Override
			public void visit(DLTableauNodeLabelAddition mod) {
				addLabel(mod.getNode(), mod.getLabel());
			}

			@Override
			public void visit(DLTableauNodeAddition mod) {
				addNode(mod.getNode());
			}
		});
	}

	void revert(DLTableauModification mod) {
		mod.accept(new DLTableauModification.Visitor() {

			@Override
			public void visit(DLTableauEdgeLabelAddition mod) {
				removeLabel(mod.getNodeFrom(), mod.getLabel(), mod.getNodeTo());
			}

			@Override
			public void visit(DLTableauNodeLabelAddition mod) {
				removeLabel(mod.getNode(), mod.getLabel());
			}

			@Override
			public void visit(DLTableauNodeAddition mod) {
				removeNode(mod.getNode());
			}
		});

	}

	int reserveFreshNode() {
		return nextFreshNode_++;
	}

	private void addNode(int node) {
		if (node >= nextFreshNode_) {
			throw new Error("Node not reserved: " + node);
		}
		boolean added = nodes_.add(node);
		if (!added) {
			throw new Error("Node already exists: " + node);
		}
	}

	private void removeNode(int node) {
		boolean success = nodes_.remove(node);
		if (!success) {
			throw new Error("Node not found: " + node);
		}
	}

	private void addLabel(int node, DLConcept label) {
		checkValidNode(node);
		Set<DLConcept> l = nodeLabels_.get(node);
		if (l == null) {
			l = new HashSet<>();
			nodeLabels_.put(node, l);
		}
		boolean added = l.add(label);
		if (added && label.equals(new DLConceptBottom())) {
			clashCount_++;
		}
	}

	private void removeLabel(int node, DLConcept label) {
		checkValidNode(node);
		Set<DLConcept> l = nodeLabels_.get(node);
		boolean success = (l != null) && l.remove(label);
		if (!success) {
			throw new Error("Label " + label + " not found in node " + node);
		}
		if (label.equals(new DLConceptBottom())) {
			clashCount_--;
		}

	}

	private void addLabel(int nodeFrom, DLRole label, int nodeTo) {
		checkValidNode(nodeFrom);
		checkValidNode(nodeTo);
		Map<DLRole, Set<Integer>> nodeSuccessorMap = successors_.get(nodeFrom);
		if (nodeSuccessorMap == null) {
			nodeSuccessorMap = new HashMap<>();
			successors_.put(nodeFrom, nodeSuccessorMap);
		}
		Set<Integer> nodeSuccessors = nodeSuccessorMap.get(label);
		if (nodeSuccessors == null) {
			nodeSuccessors = new HashSet<>();
			nodeSuccessorMap.put(label, nodeSuccessors);
		}
		nodeSuccessors.add(nodeTo);
	}

	private void removeLabel(int nodeFrom, DLRole label, int nodeTo) {
		checkValidNode(nodeFrom);
		checkValidNode(nodeTo);
		Map<DLRole, Set<Integer>> nodeSuccessorMap = successors_.get(nodeFrom);
		boolean success = false;
		if (nodeSuccessorMap != null) {
			Set<Integer> nodeSuccessors = nodeSuccessorMap.get(label);
			if (nodeSuccessors != null) {
				success = nodeSuccessors.remove(nodeTo);
			}
		}
		if (!success) {
			throw new Error("Label " + label + " not found in the edge <"
					+ nodeFrom + ", " + nodeTo + ">");
		}

	}

	int checkValidNode(int node) {
		if (!nodes_.contains(node)) {
			throw new Error("Node not found: " + node);
		}
		// else
		return node;
	}

}
