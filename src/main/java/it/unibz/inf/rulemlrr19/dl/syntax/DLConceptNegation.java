package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * A concept ¬ C representing the complement of the set of objects represented
 * by C.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptNegation extends DLConcept {

	/**
	 * The negated concept from which this concept is constructed
	 */
	private final DLConcept negated_;

	/**
	 * Creates a new negation the given concept
	 * 
	 * @param negated
	 */
	public DLConceptNegation(DLConcept negated) {
		this.negated_ = Objects.requireNonNull(negated);
	}

	/**
	 * @return the negated concept from which this concept is constructed
	 */
	public DLConcept getNegated() {
		return this.negated_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptNegation.class, negated_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptNegation) {
			DLConceptNegation other = (DLConceptNegation) obj;
			return Objects.equals(negated_, other.negated_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "(¬ " + getNegated() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
