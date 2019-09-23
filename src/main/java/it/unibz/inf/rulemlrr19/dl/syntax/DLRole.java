package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * A binary relations on objects from the modeled domain.
 * 
 * @author Yevgeny Kazakov
 */
public abstract class DLRole extends DLObject {

	/**
	 * The visitor pattern for role types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLRoleName role);

	}

	public abstract void accept(Visitor visitor);

	@Override
	public void accept(DLObject.Visitor visitor) {
		accept((Visitor) visitor);
	}

}
