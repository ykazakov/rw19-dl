package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept ∃ R.C representing objects that are connected by R represented by
 * the given role to at least one object of C.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptExistentialRestiction extends DLConcept {

	/**
	 * The relation using which objects of this concept must be connected
	 */
	private final DLRole relation_;

	/**
	 * The concept
	 * 
	 * containing at least one relation successor of all objects represented by
	 * this concept
	 */
	private final DLConcept filler_;

	/**
	 * Creates a new existential restriction using the given role and concept
	 * 
	 * @param relation
	 * @param filler
	 */
	public DLConceptExistentialRestiction(DLRole relation, DLConcept filler) {
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
	 * @return the concept containing at least one relation successor of all
	 *         objects represented by this concept
	 */
	public DLConcept getFiller() {
		return this.filler_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptExistentialRestiction.class,
				relation_.hashCode(), filler_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptExistentialRestiction) {
			DLConceptExistentialRestiction other = (DLConceptExistentialRestiction) obj;
			return Objects.equals(relation_, other.relation_)
					&& Objects.equals(filler_, other.filler_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "(∃ " + getRelation() + ". " + getFiller() + ")";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
