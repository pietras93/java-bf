// brainfuck interpreter
import java.util.Scanner;

class bf_int {

	final int arr_size = 3000;
	Scanner sc = new Scanner(System.in);
	
	public char[] arr = new char[arr_size];
	int pos = 0;
	char curr = 0;
	public int code_pos = 0;
	String code = "";
	
	int at_loop = -1;
	int curr_loop = 0;
	int loop_start[] = new int[arr_size];
	int loop_end[] = new int[arr_size];
	
	//Constructor
	public bf_int(String code_str) {
		int i = 0;
		
		for(i = 0; i < arr_size; i++) {
			arr[i] = 0;
			loop_start[i] = 0;
			loop_end[i] = 0;
		}
		
		code = code_str;
	}
	
	//Increment position
	void inc_pos() {
		if(pos == arr_size - 1)
			pos = 0;
		else
			pos++;
	}
	
	//Decrement position
	void dec_pos() {
		if(pos == 0)
			pos = arr_size - 1;
		else
			pos--;
	}
	
	//Increment value
	void inc_val() {
		arr[pos]++;
	}
	
	//Decrement position
	void dec_val() {
		arr[pos]--;
	}
	
	//Write value to output
	char write_val() {
		return arr[pos];
	}
	
	//Read value from input
	void read_val() {
		System.out.print("Enter a character: ");
		char ch = sc.findInLine(".").charAt(0);
		System.out.print("\n");
		arr[pos] = ch;
	}
	
	//Start loop
	void start_loop() {
		if((int)arr[pos] == 0) {
			code_pos = loop_end[at_loop] - 1;
			at_loop--;
		} else {
			at_loop++;
		}
	}
	
	//End loop
	void end_loop() {
		if((int)arr[pos] == 0) {
			at_loop--;
		} else {
			code_pos = loop_start[at_loop];
		}
	}
	
	//Open loop
	void open_loop(int char_pos) {
		at_loop++;
		loop_start[at_loop] = char_pos;
	}
	
	//Close loop
	void close_loop(int char_pos) {
		loop_end[at_loop] = char_pos;
		at_loop--;
	}
	
	//Start code execution
	public void start_exec() {
		
		//Find loops
		for(code_pos = 0; code_pos < code.length(); code_pos++) {
			if(code.charAt(code_pos) == '[')
				open_loop(code_pos);
			if(code.charAt(code_pos) == ']')
				close_loop(code_pos);
		}
		
		if(at_loop != -1) {
			System.out.println("\nExecution ended due to syntax error.\nLoop brackets don't match.");
		} else {
			//Execute code
			for(code_pos = 0; code_pos < code.length(); code_pos++) {
				System.out.println(code.charAt(code_pos) + "\t" + code_pos + "\t" + pos + "\t" + (int)arr[pos]);
				if(code.charAt(code_pos) == '+')
					inc_val();
				if(code.charAt(code_pos) == '-')
					dec_val();
				if(code.charAt(code_pos) == '>')
					inc_pos();
				if(code.charAt(code_pos) == '<')
					dec_pos();
				if(code.charAt(code_pos) == '.')
					System.out.print(write_val());
				if(code.charAt(code_pos) == ',')
					read_val();
				if(code.charAt(code_pos) == '[')
					start_loop();
				if(code.charAt(code_pos) == ']')
					end_loop();
			}
		}
	}
}

public class bf
{
	public static void main(String[] args) {
	
		System.out.println("Java Brainfuck Interpreter\nPiotr Frankowski\n\nEnter your brainfuck code:");
		Scanner sc = new Scanner(System.in);
		String code = sc.nextLine();
		
		bf_int interp = new bf_int(code);
		
		System.out.println("\nStarting interpreter!\n");
		
		interp.start_exec();
		
		System.out.println("\n\nFinished!");
	}
}