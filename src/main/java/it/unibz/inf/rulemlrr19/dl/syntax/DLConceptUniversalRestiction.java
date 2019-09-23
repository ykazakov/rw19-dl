package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept ∀ R.C representing the objects that are connected by the relation
 * represented by R to only objects of represented by C.
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLConceptUniversalRestiction extends DLConcept {

	/**
	 * The relation using which objects of this concept must be connected
	 */
	private final DLRole relation_;

	/**
	 * The concept containing all relation successors of all objects represented
	 * by this concept
	 */
	private final DLConcept filler_;

	/**
	 * Creates a new existential restriction using the given role and concept
	 * 
	 * @param relation
	 * @param filler
	 */
	public DLConceptUniversalRestiction(DLRole relation, DLConcept filler) {
		this.relation_ = Objects.requireNonNull(relation);
		this.filler_ = Objects.requireNonNull(filler);
	}

	/**
	 * @return the relation using which objects of this concept must be
	 *         connected
	 */
	public DLRole getRelation() {
		return this.relation_;
	}

	/**
	 * @return the concept containing all relation successors of all objects
	 *         represented by this concept
	 */
	public DLConcept getFiller() {
		return this.filler_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptUniversalRestiction.class,
				relation_.hashCode(), filler_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptUniversalRestiction) {
			DLConceptUniversalRestiction other = (DLConceptUniversalRestiction) obj;
			return Objects.equals(relation_, other.relation_)
					&& Objects.equals(filler_, other.filler_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "(∀ " + getRelation() + ". " + getFiller() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
