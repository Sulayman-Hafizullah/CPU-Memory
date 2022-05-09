
import java.io.*;
import java.lang.Runtime;
import java.util.Scanner;
//This class begins the processes 
public class Project1 {
	public static void main(String args[]) {
		try {
			int timer = 20;
			//start the memory process with the test file as an input
			Runtime rt = Runtime.getRuntime();
			Process memoryproc = rt.exec("java Memory " + args[0]);


			InputStream is = memoryproc.getInputStream();
			OutputStream os = memoryproc.getOutputStream();
			// Scanner inputMemory = new Scanner(is);
			// PrintWriter outputMemory = new PrintWriter(os);
			BufferedReader memoryIn = new BufferedReader(new InputStreamReader(is));
			PrintWriter memoryOut = new PrintWriter(new OutputStreamWriter(os));
			//create CPU object with timer as the parameter
			
			if (args.length == 2)
			{
				timer = Integer.valueOf(args[1]);
			}
			else {
				System.out.println("using default timer value: "+timer);
			}
			CPU cpu = new CPU(timer);
			

			cpu.SetStream(memoryIn, memoryOut);
			cpu.mainloop();

			memoryproc.waitFor();

			int exitVal = memoryproc.exitValue();

			//System.out.println("Process exited: " + exitVal);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
