package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptDisjunction;

/**
 * An application of the disjunction rule that chooses the first disjunct to be
 * produced. See ⊔-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 * 
 */
class DLTableauDisjunctionFirstRuleApplication
		extends DLTableauRuleApplication<DLConceptDisjunction> {

	DLTableauDisjunctionFirstRuleApplication(DLTableau tableau, int node,
			DLConceptDisjunction concept) {
		super(tableau, node, concept);
	}

	@Override
	public Stream<DLTableauModification> getModifications(int time) {
		return Stream.of(new DLTableauNodeLabelAddition(time, getNode(),
				getConcept().getFirstDisjunct()));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
