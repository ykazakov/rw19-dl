package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The axiom C ≡ D stating that concepts C and D represent identical sets of
 * objects.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptEquivalence extends DLTBoxAxiom {

	/**
	 * The concepts involved in the construction of this axioms that should have
	 * identical members
	 */
	private final DLConcept firstConcept_, secondConcept_;

	/**
	 * Creates a new axiom stating inclusion of objects of the given concepts
	 * 
	 * @param firstConcept
	 * @param secondConcept
	 */
	public DLConceptEquivalence(DLConcept firstConcept,
			DLConcept secondConcept) {
		this.firstConcept_ = Objects.requireNonNull(firstConcept);
		this.secondConcept_ = Objects.requireNonNull(secondConcept);
	}

	/**
	 * @return the first of the equivalent concepts of this axiom
	 */
	public DLConcept getFirstConcept() {
		return this.firstConcept_;
	}

	/**
	 * @return the second of the equivalent concepts of this axiom
	 */
	public DLConcept getSecondConcept() {
		return this.secondConcept_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptEquivalence.class,
				firstConcept_.hashCode(), secondConcept_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptEquivalence) {
			DLConceptEquivalence other = (DLConceptEquivalence) obj;
			return Objects.equals(firstConcept_, other.secondConcept_)
					&& Objects.equals(secondConcept_, other.secondConcept_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return getFirstConcept() + " ≡ " + getSecondConcept();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
