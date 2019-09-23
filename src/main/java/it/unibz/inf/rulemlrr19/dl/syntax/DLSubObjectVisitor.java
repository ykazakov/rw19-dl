package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A {@link DLObject.Visitor} that is accepted for the sub-objects of the
 * visited {@link DLObject}. Mostly useful as a prototype of recursive methods
 * over {@link DLObject}.
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLSubObjectVisitor implements DLObject.Visitor {

	@Override
	public void visit(DLConceptAssertion axiom) {
		axiom.getInstance().accept(this);
		axiom.getType().accept(this);
	}

	@Override
	public void visit(DLRoleAssertion axiom) {
		axiom.getRelation().accept(this);
		axiom.getFirstInstance().accept(this);
		axiom.getSecondInstance().accept(this);
	}

	@Override
	public void visit(DLConceptInclusion axiom) {
		axiom.getSubConcept().accept(this);
		axiom.getSuperConcept().accept(this);
	}

	@Override
	public void visit(DLConceptEquivalence axiom) {
		axiom.getFirstConcept().accept(this);
		axiom.getSecondConcept().accept(this);
	}

	@Override
	public void visit(DLConceptBottom concept) {
		// no sub-objects
	}

	@Override
	public void visit(DLConceptConjunction concept) {
		concept.getFirstConjunct().accept(this);
		concept.getSecondConjunct().accept(this);
	}

	@Override
	public void visit(DLConceptDisjunction concept) {
		concept.getFirstDisjunct().accept(this);
		concept.getSecondDisjunct().accept(this);
	}

	@Override
	public void visit(DLConceptExistentialRestiction concept) {
		concept.getRelation().accept(this);
		concept.getFiller().accept(this);
	}

	@Override
	public void visit(DLConceptName concept) {
		// no sub-objects
	}

	@Override
	public void visit(DLConceptNegation concept) {
		concept.getNegated().accept(this);
	}

	@Override
	public void visit(DLConceptTop concept) {
		// no sub-objects
	}

	@Override
	public void visit(DLConceptUniversalRestiction concept) {
		concept.getRelation().accept(this);
		concept.getFiller().accept(this);

	}

	@Override
	public void visit(DLRoleName role) {
		// no sub-objects
	}

	@Override
	public void visit(DLIndividual individual) {
		// no sub-objects
	}

}
