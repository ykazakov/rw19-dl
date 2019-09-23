package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A set of objects of the modeled domain.
 * 
 * @author Yevgeny Kazakov
 */
public abstract class DLConcept extends DLObject {

	/**
	 * The visitor pattern for concept types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLConceptBottom concept);

		void visit(DLConceptConjunction concept);

		void visit(DLConceptDisjunction concept);

		void visit(DLConceptExistentialRestiction concept);

		void visit(DLConceptName concept);

		void visit(DLConceptNegation concept);

		void visit(DLConceptTop concept);

		void visit(DLConceptUniversalRestiction concept);

	}

	public abstract void accept(Visitor visitor);

	@Override
	public void accept(DLObject.Visitor visitor) {
		accept((Visitor) visitor);
	}

}
