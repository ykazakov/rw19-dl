package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptBottom;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptNegation;

/**
 * An application of the negation rule. See ∀-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 *
 */
class DLTableauClashRuleApplication
		extends DLTableauRuleApplication<DLConceptNegation> {

	DLTableauClashRuleApplication(DLTableau tableau, int node,
			DLConceptNegation concept) {
		super(tableau, node, concept);
	}

	@Override
	public boolean isPotentiallyApplicable() {
		return super.isPotentiallyApplicable() && getTableau().getNodeLabels(getNode())
				.contains(getConcept().getNegated());
	}

	@Override
	public Stream<DLTableauModification> getModifications(int time) {
		return Stream.of(new DLTableauNodeLabelAddition(time, getNode(),
				new DLConceptBottom()));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
