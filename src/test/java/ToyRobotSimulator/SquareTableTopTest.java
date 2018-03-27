package ToyRobotSimulator;

import ToyRobotSimulator.simulator.Position;
import ToyRobotSimulator.simulator.SquareTableTop;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SquareTableTopTest {

    @Test
    public void testIsValidPosition() throws Exception {
        Position mockPosition = mock(Position.class);
        when(mockPosition.getX()).thenReturn(6);
        when(mockPosition.getY()).thenReturn(7);

        SquareTableTop board = new SquareTableTop(4, 5);
        Assert.assertFalse(board.isValidPosition(mockPosition));


        when(mockPosition.getX()).thenReturn(1);
        when(mockPosition.getY()).thenReturn(1);
        Assert.assertTrue(board.isValidPosition(mockPosition));


        when(mockPosition.getX()).thenReturn(-1);
        when(mockPosition.getY()).thenReturn(-1);
        Assert.assertFalse(board.isValidPosition(mockPosition));
    }
}
