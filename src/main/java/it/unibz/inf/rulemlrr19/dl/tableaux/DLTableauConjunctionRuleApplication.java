package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptConjunction;

/**
 * An application of the conjunction rule. See ⊓-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauConjunctionRuleApplication
		extends DLTableauRuleApplication<DLConceptConjunction> {

	/**
	 * Creates a new application of a conjunction rule within the given tableau
	 * to a given node and a conjunction concept
	 * 
	 * @param tableau
	 * @param node
	 * @param concept
	 */
	DLTableauConjunctionRuleApplication(DLTableau tableau, int node,
			DLConceptConjunction concept) {
		super(tableau, node, concept);
	}

	@Override
	public Stream<DLTableauModification> getModifications(int time) {
		return Stream.of(
				new DLTableauNodeLabelAddition(time, getNode(),
						getConcept().getFirstConjunct()),
				new DLTableauNodeLabelAddition(time, getNode(),
						getConcept().getSecondConjunct()));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
