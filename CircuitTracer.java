import java.awt.List;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 *
 * @author mvail
 */
public class CircuitTracer {

    /**
     * Launch the program.
     *
     * @param args three required arguments: first arg: -s for stack or -q for
     *             queue second arg: -c for console output or -g for GUI output
     *             third arg:
     *             input file name
     */
    public static void main(String[] args) {
        new CircuitTracer(args); // create this with args
    }

    /**
     * Print instructions for running CircuitTracer from the command line.
     */
    private void printUsage() {
        // print out clear usage instructions when there are problems with
        // any command line args
        System.out.println("Three required arguments:");
        System.out.println("1st arg:   -s for stack | -q for queue");
        System.out.println("2nd arg:   -c for console output | -g for GUI output");
        System.out.println("3rd arg:   input file name");
    }

    /**
     * Set up the CircuitBoard and all other components based on command line
     * arguments. Search for shortest paths and report results.
     *
     * @param args command line arguments passed through from main()
     */
    public CircuitTracer(String[] args) {
        boolean stackUse = false;
        boolean queueUse = false;
        boolean consoleUse = false;
        boolean guiUse = false;

        // parse and validate command line args - first validation provided
        if (args.length != 3) {
            printUsage();
            return; // exit the constructor immediately
        }

        // initialize the Storage to use either a stack or queue
        if (args[0].equals("-s")) {
            stackUse = true;
        } else if (args[0].equals("-q")) {
            queueUse = true;
        } else {
            printUsage();
            return;
        }

        // initialize use of console or GUI output
        if (args[1].equals("-c")) {
            consoleUse = true;
        } else if ((args[1].equals("-g"))) {
            guiUse = true;
        } else {
            printUsage();
            return;
        }

        // read in the CircuitBoard from the given file
        String filename = args[2];

        // create circuit board
        CircuitBoard cb;

        try {
            cb = new CircuitBoard(filename);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found.");
            return;
        } catch (InvalidFileFormatException iffe) {
            System.out.println("Invalid file format.");
            return;
        }

        // run the search for best paths
        Storage<TraceState> stateStore = null;
        stateStore = (stackUse) ? Storage.getStackInstance() : Storage.getQueueInstance();

        List<TraceState> bestPaths = new ArrayList<TraceState>();

        Point starter = cb.getStartingPoint();

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        // check north, east, south, west of starting point
        for (int i = 0; i < 4; i++) {
            if (!cb.isOpen(starter.x + dx[i], starter.y + dy[i])) {
                continue;
            }
            stateStore.store(new TraceState(cb, starter.x + dx[i], starter.y + dy[i]));
        }

        while (!stateStore.isEmpty()) {
            TraceState currentState = stateStore.retrieve();

            
        }



        // TODO: output results to console or GUI, according to specified choice
    }

} // class CircuitTracer
