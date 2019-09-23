package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A syntactic object used in description logics
 * 
 * @author Yevgeny Kazakov
 *
 */
public abstract class DLObject {

	/**
	 * The visitor pattern for DL object types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor extends DLAxiom.Visitor, DLConcept.Visitor,
			DLRole.Visitor, DLIndividual.Visitor {
	}

	public abstract void accept(Visitor visitor);

}
