package ToyRobotSimulator;

import ToyRobotSimulator.exception.ToyRobotException;
import ToyRobotSimulator.simulator.*;

public class GameIntegration {


    TableTop squareTableTop;
    ToyRobot robot;

    public GameIntegration(TableTop squareTableTop, ToyRobot robot) {
        this.squareTableTop = squareTableTop;
        this.robot = robot;
    }

    /**
     * Places the robot on the squareTableTop  in position X,Y and facing NORTH, SOUTH, EAST or WEST
     *
     * @param position Robot position
     * @return true if placed successfully
     * @throws ToyRobotException
     */
    public boolean placeToyRobot(Position position) throws ToyRobotException {

        if (squareTableTop == null)
            throw new ToyRobotException("Invalid squareTableTop object");

        if (position == null)
            throw new ToyRobotException("Invalid position object");

        if (position.getDirection() == null)
            throw new ToyRobotException("Invalid direction value");

        // validate the position
        if (!squareTableTop.isValidPosition(position))
            return false;

        // if position is valid then assign values to fields
        robot.setPosition(position);
        return true;
    }

    /**
     * Evals and executes a string command.
     *
     * @param inputString command string
     * @return string value of the executed command
     * @throws ToyRobotSimulator.exception.ToyRobotException
     *
     */
    public String eval(String inputString) throws ToyRobotException {
        String[] args = inputString.split(" ");

        // validate command
        RobotCommand command;
        try {
            command = RobotCommand.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            throw new ToyRobotException("Invalid command");
        }
        if (command == RobotCommand.PLACE && args.length < 2) {
            throw new ToyRobotException("Invalid command");
        }

        // validate PLACE params
        String[] params;
        int x = 0;
        int y = 0;
        Direction commandDirection = null;
        if (command == RobotCommand.PLACE) {
            params = args[1].split(",");
            try {
                x = Integer.parseInt(params[0]);
                y = Integer.parseInt(params[1]);
                commandDirection = Direction.valueOf(params[2]);
            } catch (Exception e) {
                throw new ToyRobotException("Invalid command");
            }
        }

        String output;

        switch (command) {
            case PLACE:
                output = String.valueOf(placeToyRobot(new Position(x, y, commandDirection)));
                break;
            case MOVE:
                Position newPosition = robot.getPosition().getNextPosition();
                if (!squareTableTop.isValidPosition(newPosition))
                    output = String.valueOf(false);
                else
                    output = String.valueOf(robot.move(newPosition));
                break;
            case LEFT:
                output = String.valueOf(robot.rotateLeft());
                break;
            case RIGHT:
                output = String.valueOf(robot.rotateRight());
                break;
            case REPORT:
                output = report();
                break;
            default:
                throw new ToyRobotException("Invalid command");
        }

        return output;
    }

    /**
     * Returns the X,Y and Direction of the robot
     */
    public String report() {
        if (robot.getPosition() == null)
            return null;

        return robot.getPosition().getX() + "," + robot.getPosition().getY() + "," + robot.getPosition().getDirection().toString();
    }
}
