package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import code.FilteringIterator;
import code.IObjectTest;

public class ArrayListFilterTest {
	IObjectTest<String> myTester = new IObjectTest<String>() {
		@Override
		public boolean test(String o) {
			return !"two".equalsIgnoreCase(o);
		}
	};

	@Test
	public final void testHasNext_BaseCase()
			throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("one");
		s.add("two");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
	}
	
	@Test
	public final void testHasNext_Empty() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertFalse(itr.hasNext());
	}
	
	@Test
	public final void testHasNext_Filter() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("two");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertFalse(itr.hasNext());
	}

	@Test
	public final void testHasNext_consistency() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("one");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertTrue(itr.hasNext());
	}
	
	@Test
	public final void testNext_BaseCase()
			throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("one");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals("one", itr.next());
	}

	@Test(expected = NoSuchElementException.class)
	public final void testNext_End() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.next();
	}


	@Test
	public final void testNext_NullElement() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add(null);
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertNull(itr.next());
	}
	
	@Test(expected = NoSuchElementException.class)
	public final void testNext_FilterElement() throws ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("two");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public final void testNext_ConcurrentModificationException() {
		List<String> s = new ArrayList<String>();
		s.add("one");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		s.add("two");
		itr.hasNext();
	}
	
	@Test
	public final void testRemove_BaseCase()
			throws UnsupportedOperationException, IllegalStateException,
			ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("one");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals("one", itr.next());
		itr.remove();
	}

	@Test
	public final void testRemove_Null() throws UnsupportedOperationException,
			IllegalStateException, ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add(null);
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertNull(itr.next());
		itr.remove();
	}


	@Test(expected = UnsupportedOperationException.class)
	public final void testRemove_Filter() throws IllegalStateException,
			ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("two");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertFalse(itr.hasNext());
		itr.remove();
	}

	@Test(expected = IllegalStateException.class)
	public final void testRemove_IllegalStateException() throws UnsupportedOperationException,
			ConcurrentModificationException {
		List<String> s = new ArrayList<String>();
		s.add("one");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		assertTrue(itr.hasNext());
		assertEquals("one", itr.next());
		itr.remove();
		itr.remove();
	}

	@Test
	public final void tes_PrintAll() throws UnsupportedOperationException,
			IllegalStateException, ConcurrentModificationException {
		List<String> s = Arrays.asList("one", "two","three","one","two");
		Iterator<String> itr = new FilteringIterator<String>(s.iterator(), myTester);
		String output="";
		String result = "one three one ";
		while(itr.hasNext()){
			output = output +itr.next()+" ";
		}
		assertFalse(itr.hasNext());
		assertEquals(output, result);
	}

}