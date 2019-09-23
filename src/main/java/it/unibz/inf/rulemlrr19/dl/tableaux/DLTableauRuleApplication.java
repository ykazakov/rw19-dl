package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.Objects;
import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;

/**
 * An application of a tableau rule to a concept of a node. This is an instance
 * of the tableau rule in which the node and the concept to which the rule is
 * applied is fixed. See Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 *
 * @param <C>
 *                the type of the concept to which this rule application is
 *                performed
 */
abstract class DLTableauRuleApplication<C extends DLConcept> {

	/**
	 * The tableau in which this rule is applied
	 */
	private final DLTableau tableau_;

	/**
	 * The node to which this rule is applied
	 */
	private final int node_;

	/**
	 * The concept for which this rule is applied
	 */
	private final C concept_;

	/**
	 * Creates a new rule application of the given type for the given tableau,
	 * node, and concept
	 * 
	 * @param tableau
	 * @param node
	 * @param concept
	 */
	DLTableauRuleApplication(DLTableau tableau, int node, C concept) {
		this.tableau_ = Objects.requireNonNull(tableau);
		this.node_ = Objects.requireNonNull(node);
		this.concept_ = Objects.requireNonNull(concept);
	}

	/**
	 * @return the tableau in which this rule is applied
	 */
	public DLTableau getTableau() {
		return tableau_;
	}

	/**
	 * @return the node to which this rule is applied
	 */
	public int getNode() {
		return node_;
	}

	/**
	 * @return the concept for which this rule is applied
	 */
	public C getConcept() {
		return concept_;
	}

	/**
	 * @return {@code true} if this rule application is applicable to the
	 *         current state of the tableau
	 */
	boolean isApplicable() {
		// check the common applicability conditions
		return tableau_.getNodeLabels(node_).contains(concept_);
	}

	/**
	 * Applies this rule to the tableau if it is applicable. The tableau is not
	 * modified after calling this method.
	 * 
	 * 
	 * @param time
	 * @return the resulting changes in the tableau that this rule application
	 *         performs; if the rule is not applicable, the empty stream is
	 *         returned
	 */
	public abstract Stream<DLTableauModification> apply(int time);

	public abstract void accept(Visitor visitor);

	/**
	 * The visitor pattern for sub-classes of
	 * {@link DLTableauClashRuleApplication }
	 * 
	 * @author Yevgeny Kazakov
	 */
	static interface Visitor {

		void visit(DLTableauClashRuleApplication app);

		void visit(DLTableauConjunctionRuleApplication app);

		void visit(DLTableauDisjunctionFirstRuleApplication app);

		void visit(DLTableauDisjunctionSecondRuleApplication app);

		void visit(DLTableauExistentialRuleApplication app);

		void visit(DLTableauUniversalRuleApplication app);
	}

}
