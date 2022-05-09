import java.io.*;

//This class simply reads in a file and stores the values into an array. 
//This class has a read and write function to display and change data
public class Memory {

	static int[] memory = new int[2000];
	static String instruc;
	static char operation;
	static int address;
	static int data;
	static String[] instrucs;
	static char read = 'R';
	static char write ='W';
	static char end = 'E';

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println("in memory process");
		String name = args[0];
		File file = new File(name);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));

		int index = 0;
		//String content;
		//reads through the file and stores the values in the memory array
		//while ((content = br.readLine()) != null) {
		for (String content; (content= br.readLine()) != null;)
		{
			
			//empty line
			if (!(content.length() >= 1)) {
				continue;
			}
			//address value
			if (content.charAt(0) == '.') {
				index = whitespace(content);
			}
			//not a number
			if (!(content.charAt(0) >= '0' && content.charAt(0) <= '9')) {
				continue;
			}
			String[] separation = content.split(" ");
			if (!(separation.length >= 1)) {// An empty line
				continue;
			} else {
				memory[index] = Integer.valueOf(separation[0]);
				// System.out.println(memory[init]);
				index++;
			}
			//printAll();
			
			
		}
		//checks for read, write, and end commands from the CPU and processes them accordingly
		BufferedReader other = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			instruc = other.readLine();
			operation = instruc.charAt(0);
			if (operation == read) // read operation performed if there is an R
			{
				address = getFirstNum(instruc);
				System.out.println(memory[address]);

			} else if (operation == write) // write operation performed id there is a W
			{
				String[] params = separate(instruc);
				address = Integer.valueOf(params[0]);
				data = Integer.valueOf(params[1]);
				write(address, data);
			} else if (operation == end) { //end operation performed if there is an E
				System.exit(0);
			}
		}

		// System.out.println("in memory process ended");
	}
	//reads the value at the address
	public static int read(int address) {
		return memory[address];
	}
	//writes a value to the address
	public static void write(int address, int data) {
		memory[address] = data;
	}
	//gets the first number on the line
	public static int getFirstNum(String input) {
		int firstNum;
		firstNum = Integer.valueOf(input.substring(1));
		return firstNum;
	}
	public static int whitespace(String input)
	{
		return Integer.valueOf(input.substring(1).split(" ")[0]);
	}
	//separates the numbers
	public static String[] separate(String input) {
		return input.substring(1).split(",");
	}
	//test class to see values for important variables
	public static void printAll()
	{
		System.out.println(address);
		System.out.println(data);
		int i;
		for (i=0;i<memory.length;i++)
		{
			System.out.println(memory[i]);
		}
		
	}

}
