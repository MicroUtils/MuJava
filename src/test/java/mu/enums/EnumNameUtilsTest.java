package mu.enums;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

import static mu.enums.EnumNameUtils.fromName;
import static mu.enums.EnumNameUtils.fromNameIgnoreCase;
import static org.junit.Assert.assertEquals;


public class EnumNameUtilsTest 
{
	private enum EnumTest implements INamedEnum
	{
		A("A"),
		ANOTHER_A("A"),
		B("B"),
		b("b");
		
		private String m_sName;
		EnumTest(String sName)
		{

					m_sName = sName;
		}
		@Override
		public String getName() 
		{
			return m_sName;
		}
	}
	@Before
	public void setUp() throws Exception 
	{
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testFromName() 
	{
		
		assertEquals(null, fromName("b", EnumSet.of(EnumTest.B,EnumTest.A)));
		assertEquals(null, fromName("B", EnumSet.of(EnumTest.b,EnumTest.A)));
		assertEquals(EnumTest.b, fromName("b", EnumSet.of(EnumTest.b,EnumTest.B,EnumTest.A)));
		assertEquals(EnumTest.B, fromName("B", EnumSet.of(EnumTest.B,EnumTest.A,EnumTest.b)));
		assertEquals(null, fromNameIgnoreCase("C", EnumSet.of(EnumTest.b,EnumTest.A)));
		assertEquals(null, fromNameIgnoreCase("C", EnumSet.noneOf(EnumTest.class)));
		assertEquals(null, fromNameIgnoreCase("b", EnumSet.noneOf(EnumTest.class)));
	}

	@Test
	public void testFromNameIgnoreCase()
{
		assertEquals(EnumTest.B, fromNameIgnoreCase("B", EnumSet.of(EnumTest.B,EnumTest.A)));
		assertEquals(EnumTest.B, fromNameIgnoreCase("b", EnumSet.of(EnumTest.B,EnumTest.A)));
		assertEquals(EnumTest.b, fromNameIgnoreCase("b", EnumSet.of(EnumTest.b,EnumTest.A)));
		assertEquals(EnumTest.b, fromNameIgnoreCase("B", EnumSet.of(EnumTest.b,EnumTest.A)));
		assertEquals(null, fromNameIgnoreCase("C", EnumSet.of(EnumTest.b,EnumTest.A)));
		assertEquals(null, fromNameIgnoreCase("C", EnumSet.noneOf(EnumTest.class)));
		assertEquals(null, fromNameIgnoreCase("b", EnumSet.noneOf(EnumTest.class)));
	}

	@Test
	public void testCheck() 
	{
		EnumNameUtils.check(EnumSet.of(EnumTest.B,EnumTest.A));
		EnumNameUtils.check(EnumSet.of(EnumTest.B,EnumTest.A,EnumTest.b));
		EnumNameUtils.check(EnumSet.noneOf(EnumTest.class));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckError1() {
		EnumNameUtils.check(EnumSet.of(EnumTest.ANOTHER_A, EnumTest.A));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCheckError2() {
		EnumNameUtils.check(EnumSet.allOf(EnumTest.class));
	}
}
