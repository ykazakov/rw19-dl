package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.Set;
import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;

/**
 * An application of the disjunction rule that chooses the second disjunct to be
 * produced. See ⊔-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauDisjunctionSecondRuleApplication
		extends DLTableauRuleApplication<DLConceptDisjunction> {

	DLTableauDisjunctionSecondRuleApplication(DLTableau tableau, int node,
			DLConceptDisjunction disjunction) {
		super(tableau, node, disjunction);
	}

	@Override
	public boolean isApplicable() {
		if (!super.isApplicable()) {
			return false;
		}
		Set<DLConcept> l = getTableau().getNodeLabels(getNode());
		return !l.contains(getConcept().getFirstDisjunct())
				&& !l.contains(getConcept().getSecondDisjunct());
	}

	@Override
	public Stream<DLTableauModification> apply(int time) {
		if (!isApplicable()) {
			return Stream.empty();
		}
		// else
		return Stream.of(new DLTableauNodeLabelAddition(time, getNode(),
				getConcept().getSecondDisjunct()));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
