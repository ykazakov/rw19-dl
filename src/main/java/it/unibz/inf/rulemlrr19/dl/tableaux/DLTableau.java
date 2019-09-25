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

	/**
	 * A visitor checking if a the visited tableau modification can change this
	 * tableau when applied
	 * 
	 * @author Yevgeny Kazakov
	 */
	private class DLTableauModificationApplicationChecker
			implements DLTableauModification.Visitor {

		private boolean isAlreadyApplied = false;

		@Override
		public void visit(DLTableauEdgeLabelAddition mod) {
			isAlreadyApplied = getNodes().contains(mod.getNodeFrom())
					&& getSuccessors(mod.getNodeFrom(), mod.getLabel())
							.contains(mod.getNodeTo());
		}

		@Override
		public void visit(DLTableauNodeLabelAddition mod) {
			isAlreadyApplied = getNodes().contains(mod.getNode())
					&& getNodeLabels(mod.getNode()).contains(mod.getLabel());
		}

		@Override
		public void visit(DLTableauNodeAddition mod) {
			isAlreadyApplied = getNodes().contains(mod.getNode());
		}
	}

	/**
	 * Checks if the given tableau modification is already applied for this
	 * tableau, i.e., if the tableau would change after applying this
	 * modification
	 * 
	 * @param mod
	 * @return {@code true} the given tableau modification is does not need to
	 *         be applied and {@code false} otherwise
	 */
	boolean isAlreadyApplied(DLTableauModification mod) {
		DLTableauModificationApplicationChecker checker = new DLTableauModificationApplicationChecker();
		mod.accept(checker);
		return checker.isAlreadyApplied;
	}

	/**
	 * Applies the given tableau modification to this tableau
	 * 
	 * @param mod
	 *                the modification to be applied
	 */
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

	/**
	 * Reverts the application of the given tableau modificaiton. If a tableau
	 * modification was not applicable, it is applied and then reverted, the
	 * original state of the tableau will be restored.
	 * 
	 * @param mod
	 */
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

	/**
	 * Reserves a fresh node that does not appear in this tableau and was not
	 * reserved before. Before a node can be added to this tableau, it must be
	 * first be reserved.
	 * 
	 * @return a node that does not appear in this tableau
	 */
	int reserveFreshNode() {
		return nextFreshNode_++;
	}

	/**
	 * Add the given node to the set of the nodes of this tableau. The added
	 * node will have the empty label and will not be connected to any other
	 * node. The added node should have been previously returned by
	 * {@link #reserveFreshNode()}
	 * 
	 * @param node
	 *                 the node that should be added to this tableau
	 */
	void addNode(int node) {
		if (node >= nextFreshNode_) {
			throw new Error("Node not reserved: " + node);
		}
		boolean added = nodes_.add(node);
		if (!added) {
			throw new Error("Node already exists: " + node);
		}
	}

	/**
	 * Remove the given node from this tableau. The given node should belong to
	 * the set of nodes of this tableau and not connected to any other nodes.
	 * 
	 * @param node
	 *                 the node that should be removed from this tableau
	 */
	void removeNode(int node) {
		boolean success = nodes_.remove(node);
		if (!success) {
			throw new Error("Node not found: " + node);
		}
	}

	/**
	 * Add a label to the given node of the tableau. The node should already
	 * appear in this tableau, i.e., belong to the set returned by
	 * {@link #getNodes()}.
	 * 
	 * @param node
	 *                  the node to which the label should be added
	 * @param label
	 *                  the label that should be added to the given node; not
	 *                  necessarily a new label
	 */
	void addLabel(int node, DLConcept label) {
		checkValidNode(node);
		Set<DLConcept> l = nodeLabels_.get(node);
		if (l == null) {
			l = new HashSet<>();
			nodeLabels_.put(node, l);
		}
		boolean added = l.add(label);
		if (added && label instanceof DLConceptBottom) {
			clashCount_++;
		}
	}

	void removeLabel(int node, DLConcept label) {
		checkValidNode(node);
		Set<DLConcept> l = nodeLabels_.get(node);
		boolean success = (l != null) && l.remove(label);
		if (!success) {
			throw new Error("Label " + label + " not found in node " + node);
		}
		if (label instanceof DLConceptBottom) {
			clashCount_--;
		}

	}

	void addLabel(int nodeFrom, DLRole label, int nodeTo) {
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

	void removeLabel(int nodeFrom, DLRole label, int nodeTo) {
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
