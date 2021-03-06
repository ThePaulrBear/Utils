package paul.wintz.canvas.testing;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import paul.wintz.canvas.MockLayer;

import static org.junit.Assert.*;

public class MockLayerTest {

    MockLayer layer = new MockLayer(100, 100);

    @Test
    public final void testSimple() {
        layer.clear();
        layer.assertEqualToRecorded("clear");
    }

    @Test
    public void testLineCoordinates() {
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "0x0", "100x100");
    }

    @Test
    public void testScaled() {
        int scale = 2;
        layer.setScale(scale); // setters are not recorded
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "0x0", "200x200");
        assertThat(layer.getAverageScale(), is(equalTo((float) scale)));
    }

    @Test
    public void testShifted() {
        layer.setCenter(50, 50); // setters are not recorded
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "50x50", "150x150");
    }

    @Test
    public void testScaledX() {
        int scaleX = 2;
        layer.setScaleX(scaleX);
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "0x0", "200x100");
    }

    @Test
    public void testScaledY() {
        int scaleY = 2;
        layer.setScaleY(scaleY);
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "0x0", "100x200");
    }

    @Test
    public void testScaledAndShifted() {
        int scaleX = 3;
        int scaleY = 2;
        layer.setScaleX(scaleX);
        layer.setScaleY(scaleY);
        layer.setCenter(50, 75);
        layer.line(0, 0, 100, 100, null);
        layer.assertEqualToRecorded("line", "50x75", "350x275");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRegEx_singleInt() {
        layer.assertEqualToRecorded("", "1123");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRegEx_float() {
        layer.assertEqualToRecorded("", "1.2x4.5");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRegEx_extraDimension() {
        layer.assertEqualToRecorded("", "1x2x3");
    }
}