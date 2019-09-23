package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A restriction that does not involve individuals
 * 
 * @author Yevgeny Kazakov
 */
public abstract class DLTBoxAxiom extends DLAxiom {

	/**
	 * The visitor pattern for TBox axiom types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLConceptInclusion axiom);

		void visit(DLConceptEquivalence axiom);

	}

	public abstract void accept(Visitor visitor);

	@Override
	public void accept(DLAxiom.Visitor visitor) {
		accept((Visitor) visitor);
	}

}
