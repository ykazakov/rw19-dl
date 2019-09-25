package it.unibz.inf.rulemlrr19.dl.tableaux;

import java.util.Set;
import java.util.stream.Stream;

import it.unibz.inf.rulemlrr19.dl.syntax.DLConcept;
import it.unibz.inf.rulemlrr19.dl.syntax.DLConceptExistentialRestiction;

/**
 * An application of the existential rule. See ∃-Rule in Table 2 of
 * <a href="https://doi.org/10.1007/978-3-030-31423-1_1">the paper</a>.
 * 
 * @author Yevgeny Kazakov
 */
class DLTableauExistentialRuleApplication
		extends DLTableauRuleApplication<DLConceptExistentialRestiction> {

	DLTableauExistentialRuleApplication(DLTableau tableau, int node,
			DLConceptExistentialRestiction concept) {
		super(tableau, node, concept);
	}

	@Override
	public Stream<DLTableauModification> getModifications(int time) {
		// optimization: check if the node already has a suitable successor
		Set<Integer> successors = getTableau().getSuccessors(getNode(),
				getConcept().getRelation());
		for (int node : successors) {
			Set<DLConcept> l = getTableau().getNodeLabels(node);
			if (l.contains(getConcept().getFiller())) {
				// found a successor with correct labels, nothing to do
				return Stream.empty();
			}
		}
		// otherwise, create a new successor
		int successorNode = getTableau().reserveFreshNode();
		return Stream.of(new DLTableauNodeAddition(time, getNode() + 1),
				new DLTableauEdgeLabelAddition(time, getNode(),
						getConcept().getRelation(), successorNode),
				new DLTableauNodeLabelAddition(time, successorNode,
						getConcept().getFiller()));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}