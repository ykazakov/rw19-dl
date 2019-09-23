package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * An object of the modeled domain uniquely associated with a name
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLIndividual extends DLObject implements DLEntity {

	/**
	 * The name of this individual
	 */
	private final String name_;

	/**
	 * Creates a new individual with the given name
	 * 
	 * @param name
	 */
	public DLIndividual(String name) {
		this.name_ = Objects.requireNonNull(name);
	}

	@Override
	public String getName() {
		return name_;
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

	/**
	 * The visitor pattern for individuals
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLIndividual individual);

	}

	@Override
	public void accept(DLObject.Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void accept(DLEntity.Visitor visitor) {
		visitor.visit(this);
	}

}
