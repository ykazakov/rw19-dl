package it.unibz.inf.rulemlrr19.dl.tableaux;

import it.unibz.inf.rulemlrr19.dl.syntax.DLRole;

/**
 * A tableau modification representing an addition of a role label to a tableau
 * edge
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauEdgeLabelAddition extends DLTableauModification {

	/**
	 * The nodes of the edge to which the label was added
	 */
	private final int nodeFrom_, nodeTo_;

	/**
	 * The label that is added
	 */
	private final DLRole label_;

	public DLTableauEdgeLabelAddition(int timeStamp, int nodeFrom, DLRole label,
			int nodeTo) {
		super(timeStamp);
		this.nodeFrom_ = nodeFrom;
		this.label_ = label;
		this.nodeTo_ = nodeTo;
	}

	/**
	 * @return the beginning node of the edge to which the label is added
	 */
	public int getNodeFrom() {
		return nodeFrom_;
	}

	/**
	 * @return the end node of the edge to which the label is added
	 */
	public int getNodeTo() {
		return nodeTo_;
	}

	/**
	 * @return the label that is added to the edge
	 */
	public DLRole getLabel() {
		return label_;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
