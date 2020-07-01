import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('a', 'b'));  // true
        assertTrue(obo.equalChars('r', 'q'));  // true
        assertFalse(obo.equalChars('a', 'e'));  // false
        assertFalse(obo.equalChars('z', 'a'));  // false
        assertFalse(obo.equalChars('a', 'a'));  // false
        assertTrue(obo.equalChars('&', '%')); // true
        assertFalse(obo.equalChars('A', 'b'));
        assertFalse(obo.equalChars('#', 'b'));
        //
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('z', 'y'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('B', 'A'));
        assertTrue(offByOne.equalChars('1', '2'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('@', 'A'));
        assertTrue(offByOne.equalChars('`', 'a'));
        assertTrue(offByOne.equalChars('z', '{'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('A', 'a'));
        assertFalse(offByOne.equalChars('A', 'C'));
        assertFalse(offByOne.equalChars('c', '3'));
        assertFalse(offByOne.equalChars('x', 'z'));
        assertFalse(offByOne.equalChars('Z', 'X'));
        assertFalse(offByOne.equalChars('1', '3'));
        assertFalse(offByOne.equalChars('?', '!'));
        assertFalse(offByOne.equalChars('`', 'A'));
        assertFalse(offByOne.equalChars(' ', '"'));
    }
}
