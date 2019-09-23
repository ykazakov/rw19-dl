package it.unibz.inf.rulemlrr19.dl.syntax;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A collection of {@link DLEntity} objects
 * 
 * @author Yevgeny Kazakov
 *
 */
public class DLSignature {

	/**
	 * The {@link DLConceptName} objects of this signature
	 */
	private final Set<DLConceptName> conceptNames_ = new HashSet<>();

	/**
	 * The {@link DLRoleName} objects of this signature
	 */
	private final Set<DLRoleName> roleNames_ = new HashSet<>();

	/**
	 * The {@link DLIndividual} objects of this signature
	 */
	private final Set<DLIndividual> individuals_ = new HashSet<>();

	/**
	 * @return the {@link DLConceptName} objects of this signature
	 */
	public Stream<? extends DLConceptName> conceptNames() {
		return conceptNames_.stream();
	}

	/**
	 * @return the {@link DLRoleName} objects of this signature
	 */
	public Stream<? extends DLRoleName> roleNames() {
		return roleNames_.stream();
	}

	/**
	 * @return the {@link DLIndividual} objects of this signature
	 */
	public Stream<? extends DLIndividual> individuals() {
		return individuals_.stream();
	}

	/**
	 * @return the {@link DLEntity} objects of this signature
	 */
	public Stream<? extends DLEntity> entities() {
		return Stream.concat(conceptNames(),
				Stream.concat(roleNames(), individuals()));
	}

	/**
	 * Adds the given {@link DLConceptName} to this signature if it does not
	 * already contain it
	 * 
	 * @param entity
	 */
	public void add(DLConceptName entity) {
		conceptNames_.add(entity);
	}

	/**
	 * Adds the given {@link DLRoleName} to this signature if it does not
	 * already contain it
	 * 
	 * @param entity
	 */
	public void add(DLRoleName entity) {
		roleNames_.add(entity);
	}

	/**
	 * Adds the given {@link DLIndividual} to this signature if it does not
	 * already contain it
	 * 
	 * @param entity
	 */
	public void add(DLIndividual entity) {
		individuals_.add(entity);
	}

	/**
	 * Removes the given {@link DLConceptName} from this signature if it
	 * contains it
	 * 
	 * @param entity
	 */
	public void remove(DLConceptName entity) {
		conceptNames_.remove(entity);
	}

	/**
	 * Removes the given {@link DLRoleName} from this signature if it contains
	 * it
	 * 
	 * @param entity
	 */
	public void remove(DLRoleName entity) {
		roleNames_.remove(entity);
	}

	/**
	 * Removes the given {@link DLIndividual} from this signature if it contains
	 * it
	 * 
	 * @param entity
	 */
	public void remove(DLIndividual entity) {
		individuals_.remove(entity);
	}

	/**
	 * Adds all {@link DLEntity} objects contained in the given {@link DLObject}
	 * to this signature if they already do not appear there
	 * 
	 * @param o
	 * @return the resulting signature after the addition
	 */
	public DLSignature addSymbolsOf(DLObject o) {
		o.accept(new DLSubObjectVisitor() {

			@Override
			public void visit(DLConceptName concept) {
				add(concept);
			}

			@Override
			public void visit(DLRoleName role) {
				add(role);

			}

			@Override
			public void visit(DLIndividual individual) {
				add(individual);
			}
		});
		return this;
	}

	/**
	 * Removes all {@link DLEntity} objects contained in the given
	 * {@link DLObject} from this signature if they appear there
	 * 
	 * @param o
	 * @return the resulting signature after the removal
	 */
	public DLSignature removeSymbolsOf(DLObject o) {
		o.accept(new DLSubObjectVisitor() {

			@Override
			public void visit(DLConceptName concept) {
				remove(concept);
			}

			@Override
			public void visit(DLRoleName role) {
				remove(role);

			}

			@Override
			public void visit(DLIndividual individual) {
				remove(individual);
			}
		});
		return this;
	}

	public static DLSignature getSignatureOf(DLObject o) {
		return new DLSignature().addSymbolsOf(o);
	}

}
