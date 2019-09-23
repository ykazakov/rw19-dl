package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The axiom C(a) stating that the individual (instance) corresponds to an
 * element represented by concept C (type).
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptAssertion extends DLABoxAxiom {

	/**
	 * The instance of this concept assertion
	 */
	private final DLIndividual instance_;

	/**
	 * The type of this concept assertion
	 */
	private final DLConcept type_;

	/**
	 * Creates a new axiom stating that the given individual is an instance of a
	 * given concept.
	 * 
	 * @param type
	 * @param instance
	 */
	public DLConceptAssertion(DLConcept type, DLIndividual instance) {
		this.instance_ = Objects.requireNonNull(instance);
		this.type_ = Objects.requireNonNull(type);
	}

	/**
	 * @return the type of this concept assertion
	 */
	public DLConcept getType() {
		return this.type_;
	}

	/**
	 * @return the instance of this concept assertion
	 */
	public DLIndividual getInstance() {
		return this.instance_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptAssertion.class, type_.hashCode(),
				instance_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptAssertion) {
			DLConceptAssertion other = (DLConceptAssertion) obj;
			return Objects.equals(type_, other.type_)
					&& Objects.equals(instance_, other.instance_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return getType() + "(" + getInstance() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
