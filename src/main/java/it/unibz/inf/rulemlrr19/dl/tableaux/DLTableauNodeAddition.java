package it.unibz.inf.rulemlrr19.dl.tableaux;

/**
 * A tableau modification representing an addition of a node a tableau
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauNodeAddition extends DLTableauModification {

	/**
	 * The node that should be added
	 */
	private final int node_;

	public DLTableauNodeAddition(int timeStamp, int node) {
		super(timeStamp);
		this.node_ = node;
	}

	/**
	 * @return the node that should be added
	 */
	public int getNode() {
		return node_;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
