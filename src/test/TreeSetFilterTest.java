package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import code.FilteringIterator;
import code.IObjectTest;

public class TreeSetFilterTest {
	IObjectTest<Integer> myTester = new IObjectTest<Integer>() {
		@Override
		public boolean test(Integer o) {
			return o.compareTo(10)>0;
		}
	};

	@Test
	public final void testHasNext_BaseCase()
			throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(20);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertTrue(itr.hasNext());
	}
	
	@Test
	public final void testHasNext_Empty() throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertFalse(itr.hasNext());
	}
	
	@Test
	public final void testHasNext_Filter() throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(10);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertFalse(itr.hasNext());
	}

	@Test
	public final void testHasNext_consistency() throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(20);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertTrue(itr.hasNext());
	}
	
	@Test
	public final void testNext_BaseCase()
			throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(20);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals(20, itr.next().longValue());
	}

	@Test(expected = NoSuchElementException.class)
	public final void testNext_End() throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.next();
	}

	
	@Test(expected = NoSuchElementException.class)
	public final void testNext_FilterElement() throws ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(10);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public final void testNext_ConcurrentModificationException() {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(1);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		t.add(20);
		itr.hasNext();
	}
	
	@Test
	public final void testRemove_BaseCase()
			throws UnsupportedOperationException, IllegalStateException,
			ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(20);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals(20, itr.next().longValue());
		itr.remove();
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testRemove_Filter() throws IllegalStateException,
			ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(10);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.remove();
	}

	@Test(expected = IllegalStateException.class)
	public final void testRemove_IllegalStateException() throws UnsupportedOperationException,
			ConcurrentModificationException {
		Set<Integer> t = new TreeSet<Integer>();
		t.add(30);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals(30, itr.next().longValue());
		itr.remove();
		itr.remove();
	}

	@Test
	public final void tes_PrintAll() throws UnsupportedOperationException,
			IllegalStateException, ConcurrentModificationException {
		List<Integer> s = Arrays.asList(1,20,15,5,9,30);
		Set<Integer> t = new TreeSet<Integer>();
		t.addAll(s);
		Iterator<Integer> itr = new FilteringIterator<Integer>(t.iterator(), myTester);
		String output="";
		String result = "15 20 30 ";
		while(itr.hasNext()){
			output = output +itr.next()+" ";
		}
		assertFalse(itr.hasNext());
		assertEquals(output, result);
	}

}