package it.unibz.inf.rulemlrr19.dl.tableaux;

/**
 * An elementary modification of a tableau that can be performed by applications
 * of tableau rules
 * 
 * @author Yevgeny Kazakov
 */
abstract class DLTableauModification {

	/**
	 * identifies when this modification has been created
	 */
	private final int timeStamp_;

	/**
	 * Creates a tableau modification with the given time stamp
	 * 
	 * @param timeStamp
	 */
	public DLTableauModification(int timeStamp) {
		this.timeStamp_ = timeStamp;
	}

	/**
	 * @return an identifier when this modification has been created
	 */
	public int getTimeStamp() {
		return timeStamp_;
	}

	public abstract void accept(Visitor visitor);

	/**
	 * The visitor pattern for subclasses of {@link DLTableauModification}
	 * 
	 * @author Yevgeny Kazakov
	 */
	interface Visitor {

		void visit(DLTableauNodeAddition mod);

		void visit(DLTableauNodeLabelAddition mod);

		void visit(DLTableauEdgeLabelAddition mod);
	}

}
