package code;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteringIterator<E> implements Iterator<E> {
	private Iterator<E> myIterator;
	private IObjectTest<E> myTest;
	private boolean hasNextCalled = false;
	private E nextObject = null;
	private boolean nextObjectSet = false;
	E nextPointer;

	public FilteringIterator(Iterator<E> myIterator, IObjectTest<E> mytest) {
		super();
		this.myIterator = myIterator;
		this.myTest = mytest;
	}

	/**
	 * @param myIterator
	 *            set default filter return true for all.
	 */
	public FilteringIterator(Iterator<E> myIterator) {
		super();
		this.myIterator = myIterator;
		this.myTest = new IObjectTest<E>() {
			@Override
			public boolean test(Object o) {
				return true;
			}
		};
	}

	@Override
	public boolean hasNext() {
		if (hasNextCalled && nextObjectSet){
			return true;
		}
		while (myIterator.hasNext()) {
			nextObject = myIterator.next();
			nextObjectSet = true;
			hasNextCalled = true;
			if (myTest.test(nextObject)) {
				return true;
			} else{
				nextObjectSet = false;
			}
		}
		return false;
	}

	@Override
	public E next() {
		if (hasNextCalled) {
			hasNextCalled = false;
			if (nextObjectSet) {
				return nextObject;
			} else {
				throw new NoSuchElementException();
			}
		} else {
			while (myIterator.hasNext()) {
				nextObject = myIterator.next();
				if (myTest.test(nextObject)) {
					return nextObject;
				}
			}
			throw new NoSuchElementException();
		}

	}

	@Override
	public void remove() {
		if (hasNextCalled) {
			throw new UnsupportedOperationException();
		} else {
			myIterator.remove();
		}

	}
	

}
