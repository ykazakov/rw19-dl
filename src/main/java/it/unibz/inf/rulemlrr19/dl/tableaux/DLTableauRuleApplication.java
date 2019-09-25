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
	 * Checks necessary conditions for application of this rule. If the rule can
	 * be applied to the tableau, and as a result change it, this method must
	 * return {@code true} (but the method may also return {@code true} in other
	 * cases). It is also required that if this method returns {@code true},
	 * then it should also return {@code true} in all further expansions of the
	 * tableau, i.e., after applying tableau modifications using
	 * {@link DLTableau#apply(DLTableauModification)}
	 * 
	 * @return {@code true} if this rule can be potentially applied to the
	 *         current state of the tableau and {@code false} if this rule
	 *         cannot be applied
	 */
	boolean isPotentiallyApplicable() {
		// check the necessary applicability conditions
		return tableau_.getNodeLabels(node_).contains(concept_);
	}

	/**
	 * @param time
	 * @return the tableau modifications that this rule should perform, whenever
	 *         applicable
	 */
	public abstract Stream<DLTableauModification> getModifications(int time);

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
