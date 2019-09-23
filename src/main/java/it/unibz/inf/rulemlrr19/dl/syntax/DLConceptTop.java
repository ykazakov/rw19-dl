package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * The concept ⊤ representing the all objects of the modeled domain
 * 
 * @author Yevgeny Kazakov
 */
public class DLConceptTop extends DLConcept {

	@Override
	public int hashCode() {
		return Objects.hash(DLConceptTop.class);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// else
		if (obj instanceof DLConceptTop) {
			return true;
		}
		// else
		return false;
	}

	@Override
	public String toString() {
		return "⊤";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
