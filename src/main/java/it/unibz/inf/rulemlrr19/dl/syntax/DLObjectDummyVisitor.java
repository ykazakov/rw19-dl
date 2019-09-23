package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A visitor that does nothing
 * 
 * @author Yevgeny Kazakov
 */
public class DLObjectDummyVisitor implements DLObject.Visitor {

	@Override
	public void visit(DLConceptAssertion axiom) {
	}

	@Override
	public void visit(DLRoleAssertion axiom) {
	}

	@Override
	public void visit(DLConceptInclusion axiom) {
	}

	@Override
	public void visit(DLConceptEquivalence axiom) {
	}

	@Override
	public void visit(DLConceptBottom concept) {
	}

	@Override
	public void visit(DLConceptConjunction concept) {
	}

	@Override
	public void visit(DLConceptDisjunction concept) {
	}

	@Override
	public void visit(DLConceptExistentialRestiction concept) {
	}

	@Override
	public void visit(DLConceptName concept) {
	}

	@Override
	public void visit(DLConceptNegation concept) {
	}

	@Override
	public void visit(DLConceptTop concept) {
	}

	@Override
	public void visit(DLConceptUniversalRestiction concept) {
	}

	@Override
	public void visit(DLRoleName role) {
	}

	@Override
	public void visit(DLIndividual individual) {
	}

}
