package lab3;

import balloon4.Balloon;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class BalloonTests {
	private Balloon balloon;
	private static final double EPSILON = 10e-07;

	@Before
	public void setup() {
		balloon = new Balloon(10);
	}

	@Test
	public void testIntialRadius() {
		String msg = "A newly constructed Balloon should have radius zero.";
		assertEquals(msg, 0.0, balloon.getRadius(), EPSILON);
	}

	@Test
	public void testInitialPopped() {
		String msg = "A newly constructed Balloon should not be popped.";
		assertEquals(msg, false, balloon.isPopped());
	}

	@Test
	public void testBlow() {
		String msg = "After calling blow(5) on a Balloon with maximum radius 10, the radius should be 5.";
		balloon.blow(5);
		assertEquals(msg, 5, balloon.getRadius(), EPSILON);
	}

	@Test
	public void testDeflate() {
		String msg = "After calling deflate() on a Balloon, the radius should be 0.";
		balloon.blow(5);
		balloon.deflate();
		assertEquals(msg, 0, balloon.getRadius(), EPSILON);
	}

	@Test
	public void testPop() {
		String msg = "After calling pop() on a Balloon, isPopped() should be true.";
		balloon.pop();
		assertEquals(msg, true, balloon.isPopped());
	}
	
	@Test
	public void testExceededBlowPopped() {
		String msg = "After calling blow(15) on a Balloon with maximum radius 10, the balloon should be popped.";
		balloon.blow(15);
		assertEquals(msg, true, balloon.isPopped());
	}
	
	@Test
	public void testPoppedRadius() {
		String msg = "After calling pop() on a Balloon, the radius should be zero.";
		balloon.blow(5);
		balloon.pop();
		assertEquals(msg, 0, balloon.getRadius(), EPSILON);
	}
	
	@Test
	public void testPoppedAfterBlow() {
		String msg = "After calling blow(10) on a Balloon with maximum radius 10, the balloon should not be popped.";
		balloon.blow(10);
		assertEquals(msg, false, balloon.isPopped());
	}
	
	@Test 
	public void testPoppedAfterDeflate() {
		String msg = "After calling deflate(), the balloon should not be popped.";
		balloon.deflate();
		assertEquals(msg, false, balloon.isPopped());
	}
	
	@Test
	public void testBlowAfterPopped() {
		String msg = "The method blow(5)should have no effect after the balloon is popped.";
		balloon.pop();
		balloon.blow(5);
		assertEquals(msg, 0, balloon.getRadius(), EPSILON);
	}
	
	@Test
	public void testNegativeBlow() {
		String msg = "The method blow(-5)should have no effect";
		balloon.blow(10);
		balloon.blow(-5);
		assertEquals(msg, 10, balloon.getRadius(), EPSILON);
	}
	
	@Test
	public void testExceededBlowRadius() {
		String msg = "After calling blow(15) on a Balloon with maximum radius 10, the balloon should be popped.";
		balloon.blow(15);
		assertEquals(msg, 0, balloon.getRadius(), EPSILON);
	}
	
	@Test
	public void testBlowAfterDeflate() {
		String msg = "After calling deflate() and then blow(5), the balloon radius should be 5.";
		balloon.deflate();
		balloon.blow(5);
		assertEquals(msg, 5, balloon.getRadius(), EPSILON);
	}
	
	@Test 
	public void testBlowAfterBlow() {
		String msg = "After calling blow(5) twice the balloons radius should be 10.";
		balloon.blow(5);
		balloon.blow(5);
		assertEquals(msg, 10, balloon.getRadius(), EPSILON);
	}
}
