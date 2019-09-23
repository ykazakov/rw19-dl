package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A restriction imposed on the modeled domain.
 * 
 * @author Yevgeny Kazakov
 */
public abstract class DLAxiom extends DLObject {

	/**
	 * The visitor pattern for axiom types
	 * 
	 * @author Yevgeny Kazakov
	 *
	 */
	public interface Visitor extends DLABoxAxiom.Visitor, DLTBoxAxiom.Visitor {

	}

	public abstract void accept(Visitor visitor);

	@Override
	public void accept(DLObject.Visitor visitor) {
		accept((Visitor) visitor);
	}

}
