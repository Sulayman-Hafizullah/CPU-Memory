import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//This class processes the information received by the memory class and performs actions based on the instructions.
public class CPU {
	private BufferedReader memoryIn;
	private PrintWriter memoryOut;
	private int fromMem;
	private int IR, X, Y, AC;
	private int PC = 0;
	private int SP = 1000;
	private int count = 0;
	private String mode = "user";
	private boolean interruptStatus = false;
	private int timer;
	private String read = "R";
	private String write = "W";
	private String end= "E";

	public CPU(int timer) {
		this.timer = timer;
	}

	public void SetStream(BufferedReader memoryIn2, PrintWriter memOut) {
		this.memoryOut = memOut;
		this.memoryIn = memoryIn2;

	}

	public void mainloop() throws NumberFormatException, IOException {
		// int i = 0;

		int fileVal;
		while (true) {
			if (count > timer && interruptStatus == false) {
				timerInterrupt();
				count = 0;

			}
			if (interruptStatus == false) {
				count++;
			}

			/*
			 * fromMem = readMemory(); System.out.println(fromMem); fileVal =
			 * Integer.valueOf(fromMem);
			 * 
			 * if (fileVal == 44) { writeMemory(4, 10);
			 * System.out.println(readMemoryOperation(4)); endProcess(); // writeMemory(50);
			 * } // writeMemory(fileVal+i++);
			 */
			int address;
			int val;
			fromMem = readMemoryOperation(PC);
			// System.out.println(fromMem);
			IR = fromMem;
			// Load the value into the AC
			if (IR == 1) {
				PC++;
				address = readMemoryOperation(PC);
				AC = address;
				PC++;
			}
			// Load the value at the address into the AC
			else if (IR == 2) {
				PC++;
				address = readMemoryOperation(PC);
				AC = readMemoryOperation(address);
				PC++;
			}
			// Load the value from the address found in the given address into the AC
			else if (IR == 3) {
				PC++;
				address = readMemoryOperation(PC);
				address = readMemoryOperation(address);
				// AC = readMemoryOperation(readMemoryOperation(IR));
				AC = readMemoryOperation(address);
				PC++;
			}
			// Load the value at (address+X) into the AC
			else if (IR == 4) {
				PC++;
				address = readMemoryOperation(PC);
				AC = readMemoryOperation(X + address);
				PC++;

			}
			// Load the value at (address+Y) into the AC
			else if (IR == 5) {
				PC++;
				address = readMemoryOperation(PC);
				AC = readMemoryOperation(address + Y);
				PC++;
			}
			// Load from (Sp+X) into the AC (if SP is 990, and X is 1, load from 991)
			else if (IR == 6) {

				AC = readMemoryOperation(SP + X);
				PC++;
			}
			// Store the value in the AC into the address
			else if (IR == 7) {
				PC++;
				address = readMemoryOperation(PC);
				writeMemory(address, AC);
				PC++;
			}
			// Gets a random int from 1 to 100 into the AC
			else if (IR == 8) {

				AC = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1);
				PC++;
			} else if (IR == 9) {
				PC++;
				val = readMemoryOperation(PC);
				// If port=1, writes AC as an int to the screen
				if (val == 1) {
					System.out.print(AC);
					PC++;
				}
				// If port=2, writes AC as a char to the screen
				else if (val == 2) {
					char asciiVal = (char) AC;
					System.out.print(asciiVal);
					PC++;
				}
			}
			// Add the value in X to the AC
			else if (IR == 10) {

				AC = AC + X;
				PC++;
			}
			// Add the value in Y to the AC
			else if (IR == 11) {

				AC = AC + Y;
				PC++;
			}
			// Subtract the value in X from the AC
			else if (IR == 12) {

				AC = AC - X;
				PC++;
			}
			// Subtract the value in Y from the AC
			else if (IR == 13) {

				AC = AC - Y;
				PC++;
			}
			// Copy the value in the AC to X
			else if (IR == 14) {

				X = AC;
				PC++;
			}
			// Copy the value in X to the AC
			else if (IR == 15) {

				AC = X;
				PC++;
			}
			// Copy the value in the AC to Y
			else if (IR == 16) {

				Y = AC;
				PC++;
			}
			// Copy the value in Y to the AC
			else if (IR == 17) {

				AC = Y;
				PC++;
			}
			// Copy the value in AC to the SP
			else if (IR == 18) {

				SP = AC;
				PC++;
			}
			// Copy the value in SP to the AC
			else if (IR == 19) {

				AC = SP;
				PC++;
			}
			// Jump to the address
			else if (IR == 20) {
				PC++;
				address = readMemoryOperation(PC);
				PC = address;
			}
			// Jump to the address only if the value in the AC is zero
			else if (IR == 21) {
				PC++;
				if (AC == 0) {
					address = readMemoryOperation(PC);
					PC = address;
				} else {
					PC++;
				}
			}
			// Jump to the address only if the value in the AC is not zero
			else if (IR == 22) {
				PC++;
				if (AC != 0) {
					address = readMemoryOperation(PC);
					PC = address;
				} else {
					PC++;
				}
			}
			// Push return address onto stack, jump to the address
			else if (IR == 23) {
				PC++;
				address = readMemoryOperation(PC);
				writeMemory(--SP, PC + 1); // push
				PC = address;
			}
			// Pop return address from the stack, jump to the address
			else if (IR == 24) {
				val = readMemoryOperation(SP++);// pop
				PC = val;
			}
			// Increment the value in X
			else if (IR == 25) {
				X = X + 1;
				PC++;
			}
			// Decrement the value in X
			else if (IR == 26) {
				X = X - 1;
				PC++;
			}
			// Push AC onto stack
			else if (IR == 27) {
				writeMemory(--SP, AC);// push
				PC++;
			}
			// Pop from stack into AC
			else if (IR == 28) {
				AC = readMemoryOperation(SP++);// pop
				PC++;
			} else if (IR == 29) {
				instructionInterrupt();
			} else if (IR == 30) {
				returnFromInterrupt();
			} else if (IR == 50) {
				endProcess();
				System.exit(0);
			} else
				System.exit(0);
			// System.out.println("print AC:"+AC);

		}
	}

	private int readInst() throws IOException {
		if (memoryIn.readLine().equals(null)) {
			System.out.println("\nno more inputs");
			System.exit(1);
		}
		return Integer.valueOf(memoryIn.readLine());

	}

	private int readMemoryOperation(int address) throws NumberFormatException, IOException {
		if (address >= 1000 && mode.equals("user")) {
			System.err.println("Memory violation: accessing system address 1000 in user mode");
			System.exit(-1);
		}
		memoryOut.println(read + address);
		memoryOut.flush();
		return Integer.valueOf(memoryIn.readLine());
	}

	public void writeMemory(int address, int value) {

		memoryOut.println(write + address + "," + value);
		// memoryOut.print(in);
		memoryOut.flush();
		// readMemory();
	}

	public void endProcess() {
		memoryOut.println(end);
		memoryOut.flush();
	}

	public void timerInterrupt() {
		interruptStatus = true;
		int temp;
		temp = SP;
		mode = "kernel";
		SP = 2000; // set sp to top of system stack
		writeMemory(--SP, temp);
		temp = PC;
		setPC("timer"); // PC 1000 for timer interrupt
		writeMemory(--SP, temp);
	}
	public void returnFromInterrupt() throws NumberFormatException, IOException
	{
		PC = readMemoryOperation(SP++);//pop
		SP = readMemoryOperation(SP++);//pop
		mode = "user";
		interruptStatus = false;
		count++;
	}
	public void instructionInterrupt()
	{
		int val;
		interruptStatus = true;
		val = SP;
		mode = "kernel";
		SP = 2000; // set sp to top of system stack
		writeMemory(--SP, val);// push
		val = PC + 1;
		setPC("instruction"); // PC 1500 for instruction interrupt
		writeMemory(--SP, val);// push
	}
	//sets PC values based on type of interrupt
	public void setPC(String type)
	{
		if (type.equals("instruction"))
		{
			PC = 1500;
		}
		else 
		{
			PC= 1000;
		}
				
	}

}
