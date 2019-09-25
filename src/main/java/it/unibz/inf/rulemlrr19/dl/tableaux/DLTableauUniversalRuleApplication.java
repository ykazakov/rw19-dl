package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptUniversalRestiction;

/**
 * An application of the clash rule. See ∀-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauUniversalRuleApplication
		extends DLTableauRuleApplication<DLConceptUniversalRestiction> {

	DLTableauUniversalRuleApplication(DLTableau tableau, int node,
			DLConceptUniversalRestiction concept) {
		super(tableau, node, concept);
	}

	@Override
	public Stream<DLTableauModification> getModifications(int time) {
		// TODO: compute the tableau modifications
		return null;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}