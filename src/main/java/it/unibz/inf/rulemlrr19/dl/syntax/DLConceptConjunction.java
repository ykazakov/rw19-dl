package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept conjunction C ⊓ D representing the common objects represented by
 * C and D.
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLConceptConjunction extends DLConcept {

	/**
	 * The conjuncts from which this concept is constructed
	 */
	private final DLConcept firstConjunct_, secondConjunct_;

	/**
	 * Creates a new conjunction of the two given conjuncts
	 * 
	 * @param firstConjunct
	 * @param secondConjunct
	 */
	public DLConceptConjunction(DLConcept firstConjunct,
			DLConcept secondConjunct) {
		this.firstConjunct_ = Objects.requireNonNull(firstConjunct);
		this.secondConjunct_ = Objects.requireNonNull(secondConjunct);
	}

	/**
	 * @return the first conjunct of this concept conjunction
	 */
	public DLConcept getFirstConjunct() {
		return this.firstConjunct_;
	}

	/**
	 * @return the second conjunct of this concept conjunction
	 */
	public DLConcept getSecondConjunct() {
		return this.secondConjunct_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptConjunction.class,
				firstConjunct_.hashCode(), secondConjunct_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptConjunction) {
			DLConceptConjunction other = (DLConceptConjunction) obj;
			return Objects.equals(firstConjunct_, other.firstConjunct_)
					&& Objects.equals(secondConjunct_, other.secondConjunct_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "(" + getFirstConjunct() + " ⊓ " + getSecondConjunct() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
