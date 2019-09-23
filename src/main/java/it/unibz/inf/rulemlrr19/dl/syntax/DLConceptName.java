package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * An elementary set of objects of the modeled domain uniquely associated with a
 * given name.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptName extends DLConcept implements DLEntity {

	/**
	 * The name of this concept
	 */
	private final String name_;

	/**
	 * Creates a new concept with the given name
	 * 
	 * @param name
	 */
	public DLConceptName(String name) {
		this.name_ = Objects.requireNonNull(name);
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
	public void accept(DLConcept.Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void accept(DLEntity.Visitor visitor) {
		visitor.visit(this);

	}

}
