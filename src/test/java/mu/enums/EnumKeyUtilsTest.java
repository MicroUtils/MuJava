package mu.enums;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static mu.enums.EnumKeyUtils.buildKeysMap;
import static org.junit.Assert.assertEquals;

public class EnumKeyUtilsTest
{
	private enum EnumEmptyTest implements IUniqueKeyEnum<Integer>
	{
		;

		@Override
		public Integer getKey()
		{
			return null;
		}
	}

	private enum EnumTest implements IUniqueKeyEnum<Integer>
	{
		A(1),
		B(2);
		
		private Integer m_iKey;
		
		EnumTest(int iKey)
		{
			m_iKey = iKey;
		}
		
		@Override
		public Integer getKey()
		{
			return m_iKey;
		}
	}
	
	@Before
	public void setUp() throws Exception
	{
		EnumKeyChecker.check(EnumTest.class);
	}
	
	@After
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void testBuildKeysMap()
	{
		assertEquals(Collections.emptyMap(), buildKeysMap(EnumEmptyTest.class));
		Map testMap = new HashMap<Integer,EnumTest>(){{put(1,EnumTest.A); put(2, EnumTest.B);  }} ;


		assertEquals(testMap, buildKeysMap(EnumTest.class));
	}
	
}
