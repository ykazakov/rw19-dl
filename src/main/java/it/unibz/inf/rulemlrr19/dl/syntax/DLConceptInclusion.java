package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The axiom C ⊑ D stating that every object represented by concept C is also an
 * object represented by concept D.
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptInclusion extends DLTBoxAxiom {

	/**
	 * The concepts involved in the construction of this axioms; all members of
	 * {@link #subConcept_} must be included in {@link #superConcept_}
	 */
	private final DLConcept subConcept_, superConcept_;

	/**
	 * Creates a new axiom stating inclusion of objects of the given concepts
	 * 
	 * @param subConcept
	 * @param superConcept
	 */
	public DLConceptInclusion(DLConcept subConcept, DLConcept superConcept) {
		this.subConcept_ = Objects.requireNonNull(subConcept);
		this.superConcept_ = Objects.requireNonNull(superConcept);
	}

	/**
	 * @return the sub-concept of this axiom
	 */
	public DLConcept getSubConcept() {
		return this.subConcept_;
	}

	/**
	 * @return the super-concept of this axiom
	 */
	public DLConcept getSuperConcept() {
		return this.superConcept_;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptInclusion.class, subConcept_.hashCode(),
				superConcept_.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptInclusion) {
			DLConceptInclusion other = (DLConceptInclusion) obj;
			return Objects.equals(subConcept_, other.subConcept_)
					&& Objects.equals(superConcept_, other.superConcept_);
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return getSubConcept() + " ⊑ " + getSuperConcept();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
