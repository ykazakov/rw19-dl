package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * States that two given individual (instances) are connected by the relation
 * represented by the given role (relation)
 * 
 * @author Yevgeny Kazakov
 */
public class DLRoleAssertion extends DLABoxAxiom {

	/**
	 * The instances of this role assertion
	 */
	private final DLIndividual firstInstance_, secondInstance_;

	/**
	 * The relation of this role assertion
	 */
	private final DLRole relation_;

	/**
	 * Creates a new axiom stating that the given individuals are connected by
	 * the given role.
	 * 
	 * @param relation
	 * @param firstInstance
	 * @param secondInstance
	 */
	public DLRoleAssertion(DLRole relation, DLIndividual firstInstance,
			DLIndividual secondInstance) {
		this.firstInstance_ = Objects.requireNonNull(firstInstance);
		this.secondInstance_ = Objects.requireNonNull(secondInstance);
		this.relation_ = Objects.requireNonNull(relation);
	}

	/**
	 * @return the type of this concept assertion
	 */
	public DLRole getRelation() {
		return this.relation_;
	}

	/**
	 * @return the first instance of this role assertion
	 */
	public DLIndividual getFirstInstance() {
		return this.firstInstance_;
	}

	/**
	 * @return the first instance of this role assertion
	 */
	public DLIndividual getSecondInstance() {
		return this.secondInstance_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLRoleAssertion.class, firstInstance_.hashCode(),
				secondInstance_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLRoleAssertion) {
			DLRoleAssertion other = (DLRoleAssertion) obj;
			return Objects.equals(firstInstance_, other.firstInstance_)
					&& Objects.equals(secondInstance_, other.secondInstance_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return getRelation() + "(" + getFirstInstance() + ", "
				+ getSecondInstance() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
