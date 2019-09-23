package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.Objects;

/**
 * 
 * An object uniquely associated with a string name
 * 
 * @author Yevgeny Kazakov
 */
public interface DLEntity {

	/**
	 * @return the name of this concept
	 */
	String getName();

	/**
	 * Computes the hash code of the given entity. If two entities are equal
	 * then they must have the same hash code
	 * 
	 * @param e
	 *              the entity
	 * @return the hash code of the given entity
	 * @see #equals(DLEntity, Object)
	 */
	public static int hashCode(DLEntity e) {
		if (e == null) {
			return 0;
		}
		// else
		return Objects.hash(e.getClass(), e.getName());
	}

	/**
	 * Checks equality of a given entity to a given object. The equality holds
	 * if both objects are entities of the same type (e.g., they are both
	 * concept names or both role names, or both individuals) and have the same
	 * names.
	 * 
	 * @param entity
	 * @param other
	 * @return {@code true} if the two given entities are equal and
	 *         {@code false} otherwise
	 */
	public static boolean equals(DLEntity entity, Object other) {
		if (entity == null) {
			return other == null;
		}
		// else
		return Objects.equals(entity.getClass(), other.getClass()) && Objects
				.equals(entity.getName(), ((DLEntity) other).getName());
	}

	/**
	 * @param entity
	 * @return the string representation of the given entity
	 */
	public static String toString(DLEntity entity) {
		return entity.getName();
	}

	/**
	 * The visitor pattern for entity types
	 * 
	 * @author Yevgeny Kazakov
	 */
	public interface Visitor {

		void visit(DLConceptName concept);

		void visit(DLRoleName role);

		void visit(DLIndividual individual);

	}

	void accept(Visitor visitor);

}
