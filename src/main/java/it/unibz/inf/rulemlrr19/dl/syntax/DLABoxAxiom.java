package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A restriction that involves individuals
 * 
 * @author Yevgeny Kazakov
 */
public abstract class DLABoxAxiom extends DLAxiom {

	/**
	 * The visitor pattern for ABox axiom types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLConceptAssertion axiom);

		void visit(DLRoleAssertion axiom);

	}

	public abstract void accept(Visitor visitor);

	@Override
	public void accept(DLAxiom.Visitor visitor) {
		accept((Visitor) visitor);
	}

}
