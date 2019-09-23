package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept ⊥ representing the empty set set of objects
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptBottom extends DLConcept {

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptBottom.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptBottom) {
			return true;
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "⊥";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
