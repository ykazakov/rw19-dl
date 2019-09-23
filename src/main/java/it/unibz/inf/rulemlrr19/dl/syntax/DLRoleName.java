package it.unibz.inf.rulemlrr19.dl.syntax;

/**
 * An elementary binary relation o objects of the modeled domain uniquely
 * associated with a given name.
 * 
 * @author Yevgeny Kazakov
 */
public class DLRoleName extends DLRole implements DLEntity {

	/**
	 * The name of this role
	 */
	private final String name_;

	/**
	 * Creates a new role with the given name
	 * 
	 * @param name
	 */
	public DLRoleName(String name) {
		this.name_ = name;
	}

	@Override
	public String getName() {
		return this.name_;
	}

	@Override
	public int hashCode() {
		return DLEntity.hashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return DLEntity.equals(this, obj);
	}

	@Override
	public String toString() {
		return DLEntity.toString(this);
	}

	@Override
	public void accept(DLRole.Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void accept(DLEntity.Visitor visitor) {
		visitor.visit(this);

	}

}
