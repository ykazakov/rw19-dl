package it.unibz.inf.rulemlrr19.dl.tableaux;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;

/**
 * A tableau modification representing an addition of a role label to a tableau
 * node
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauNodeLabelAddition extends DLTableauModification {

	/**
	 * The node to which the label was added
	 */
	private final int node_;

	/**
	 * The label that is added
	 */
	private final DLConcept label_;

	public DLTableauNodeLabelAddition(int timeStamp, int node,
			DLConcept label) {
		super(timeStamp);
		this.node_ = node;
		this.label_ = label;
	}

	/**
	 * @return node to which the label was added
	 */
	public int getNode() {
		return node_;
	}

	/**
	 * @return the label that is added
	 */
	public DLConcept getLabel() {
		return label_;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
