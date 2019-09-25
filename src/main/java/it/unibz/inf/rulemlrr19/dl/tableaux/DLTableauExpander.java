package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptName;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptTop;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;
import it.unibz.inf.rulemlrr19.dl.syntax.DLObjectDummyVisitor;

/**
 * Performs the non-deterministic expansion of tableau using the tableau rules.
 * If an expansion results in a clash, alternative tableau expansions are tried.
 * See Algorithm 1 of <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the
 * paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauExpander {

	/**
	 * The tableau that is being expanded
	 */
	private final DLTableau tableau_;

	/**
	 * Incremented with every rule application of a tableau expansion
	 */
	private int time_ = 0;

	/**
	 * The rule applications that are not yet performed
	 */
	private final Queue<DLTableauRuleApplication<?>> todoRules_ = new LinkedList<>();

	/**
	 * Records all tableau modifications that are applied on a tableau expansion
	 * so that they can be reverted in case the tableau expansion results in a
	 * clash
	 */
	private final Stack<DLTableauModification> modificationHistory_ = new Stack<>();

	/**
	 * Records all rule applications that are being made during a tableau
	 * expansion
	 */
	private final Stack<DLTableauRuleApplication<?>> ruleHistory_ = new Stack<>();

	DLTableauExpander(DLTableau tableau, DLConcept concept) {
		this.tableau_ = tableau;
		// tableau initialization
		int node = tableau_.reserveFreshNode();
		apply(new DLTableauNodeAddition(time_, node));
		apply(new DLTableauNodeLabelAddition(time_, node, concept));
		time_++;
	}

	/**
	 * Run the tableau expansion
	 * 
	 * @return {@code true} if the tableau can be expanded without obtaining a
	 *         clash and {@code false} otherwise
	 */
	boolean expand() {
		for (;;) {
			DLTableauRuleApplication<?> next = todoRules_.poll();
			if (next == null) {
				return true;
			}
			apply(next);
			while (tableau_.hasClash()) {
				if (!backtrack()) {
					// cannot backtrack
					return false;
				}
			}
		}
	}

	/**
	 * Undo rule applications until the first disjunction rule
	 * 
	 * @return {@code true} if backtracking can be performed and {@code false}
	 *         otherwise
	 */
	private boolean backtrack() {
		while (!ruleHistory_.isEmpty()) {
			DLTableauRuleApplication<?> lastRule = ruleHistory_.pop();
			// undo all modification after this rule application
			time_--;
			while (!modificationHistory_.isEmpty()) {
				DLTableauModification mod = modificationHistory_.peek();
				if (mod.getTimeStamp() >= time_) {
					modificationHistory_.pop();
					tableau_.revert(mod);
				} else {
					break;
				}
			}
			if (lastRule instanceof DLTableauDisjunctionFirstRuleApplication) {
				DLTableauDisjunctionFirstRuleApplication rule = (DLTableauDisjunctionFirstRuleApplication) lastRule;
				// try the alternative rule application
				DLTableauDisjunctionSecondRuleApplication alternative = new DLTableauDisjunctionSecondRuleApplication(
						tableau_, rule.getNode(), rule.getConcept());
				todo(alternative);
				// done backtracking
				return true;
			} else {
				// may still need to re-apply the rule if it is still applicable
				todo(lastRule);
			}
		}
		// cannot backtrack
		return false;

	}

	/**
	 * Triggers an application of a tableau rule, including the resulting
	 * modification of the tableau
	 * 
	 * @param application
	 */
	private void apply(DLTableauRuleApplication<?> application) {
		application.apply(time_++).forEach(mod -> apply(mod));
		ruleHistory_.add(application);
	}

	/**
	 * Applies the given tableau modification if it changes the tableau and
	 * finds new rule applications that can be performed to the extended tableau
	 * 
	 * @param mod
	 */
	private void apply(DLTableauModification mod) {
		if (tableau_.isAlreadyApplied(mod)) {
			return;
		}
		// else
		tableau_.apply(mod);
		modificationHistory_.add(mod);
		// check if any new rules can be triggered
		mod.accept(new DLTableauModification.Visitor() {

			@Override
			public void visit(DLTableauEdgeLabelAddition mod) {
				// a universal rule potentially can be applied to universal
				// restrictions
				for (DLConcept c : tableau_.getNodeLabels(mod.getNodeFrom())) {
					// we are only interested in universal restrictions in the
					// label
					c.accept(new DLObjectDummyVisitor() {
						@Override
						public void visit(
								DLConceptUniversalRestiction concept) {
							if (concept.getRelation().equals(mod.getLabel())) {
								todo(new DLTableauUniversalRuleApplication(
										tableau_, mod.getNodeFrom(), concept));
							}
						}
					});
				}
			}

			@Override
			public void visit(DLTableauNodeLabelAddition mod) {
				// check potential rule applications depending on the type of
				// the label
				mod.getLabel().accept(new DLConcept.Visitor() {

					@Override
					public void visit(DLConceptTop concept) {
						// nothing to apply
					}

					@Override
					public void visit(DLConceptBottom concept) {
						// nothing to apply
					}

					@Override
					public void visit(DLConceptConjunction concept) {
						todo(new DLTableauConjunctionRuleApplication(tableau_,
								mod.getNode(), concept));
					}

					@Override
					public void visit(DLConceptDisjunction concept) {
						// try first expansion
						todo(new DLTableauDisjunctionFirstRuleApplication(
								tableau_, mod.getNode(), concept));
					}

					@Override
					public void visit(DLConceptExistentialRestiction concept) {
						todo(new DLTableauExistentialRuleApplication(tableau_,
								mod.getNode(), concept));
					}

					@Override
					public void visit(DLConceptUniversalRestiction concept) {
						todo(new DLTableauUniversalRuleApplication(tableau_,
								mod.getNode(), concept));
					}

					@Override
					public void visit(DLConceptNegation concept) {
						todo(new DLTableauClashRuleApplication(tableau_,
								mod.getNode(), concept));
					}

					@Override
					public void visit(DLConceptName concept) {
						// potential clash rule application
						todo(new DLTableauClashRuleApplication(tableau_,
								mod.getNode(), new DLConceptNegation(concept)));
					}

				});
			}

			@Override
			public void visit(DLTableauNodeAddition mod) {
				// no rule can be triggered by node addition
			}
		});

	}

	/**
	 * Schedule a given rule application to be performed at a latter time if it
	 * is applicable
	 * 
	 * @param app
	 */
	private void todo(DLTableauRuleApplication<?> app) {
		if (app.isApplicable()) {
			todoRules_.add(app);
		}
	}

}
