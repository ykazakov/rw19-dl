package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept disjunction C ⊔ D representing the union of objects represented
 * by C and D.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptDisjunction extends DLConcept {

	/**
	 * The disjuncts from which this concept is constructed
	 */
	private final DLConcept firstDisjunct_, secondDisjunct_;

	/**
	 * Creates a new disjunction of the two given disjuncts
	 * 
	 * @param firstDisjunct
	 * @param secondDisjunct
	 */
	public DLConceptDisjunction(DLConcept firstDisjunct,
			DLConcept secondDisjunct) {
		this.firstDisjunct_ = Objects.requireNonNull(firstDisjunct);
		this.secondDisjunct_ = Objects.requireNonNull(secondDisjunct);
	}

	/**
	 * @return the first disjunct of this concept disjunction
	 */
	public DLConcept getFirstDisjunct() {
		return this.firstDisjunct_;
	}

	/**
	 * @return the second disjunct of this concept disjunction
	 */
	public DLConcept getSecondDisjunct() {
		return this.secondDisjunct_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptDisjunction.class,
				firstDisjunct_.hashCode(), secondDisjunct_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptDisjunction) {
			DLConceptDisjunction other = (DLConceptDisjunction) obj;
			return Objects.equals(firstDisjunct_, other.firstDisjunct_)
					&& Objects.equals(secondDisjunct_, other.secondDisjunct_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "(" + getFirstDisjunct() + " ⊔ " + getSecondDisjunct() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
