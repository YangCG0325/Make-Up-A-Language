import java.io.*;
import java.util.*;

public class compile 
{	
	public final static String [] Basic = new String[]{"annotation", "make", "thing", "erase",
			"isname", "print", "add", "sub", "mul", "div", "mod","eq", "gt", 
			"lt", "and", "or", "not"};
	final static int def = -1;
	final static int annotation = 0;//define basic operation to integers
	final static int make = 1;
	final static int thing = 2;
	final static int erase = 3;
	final static int isname = 4;
	final static int print = 5;
	final static int add = 6;
	final static int sub = 7;
	final static int mul = 8;
	final static int div = 9;
	final static int mod = 10;
	final static int eq = 11;
	final static int gt = 12;
	final static int lt = 13;
	final static int and = 14;
	final static int or = 15;
	final static int not = 16;
	
	public static ArrayList<String> name = new ArrayList<String>();//arraylist to store name and values
	public static ArrayList<String> value = new ArrayList<String>();
	public static ArrayList<Integer> state = new ArrayList<Integer>();//arraylist to store state
	public static ArrayList<String> exeParam = new ArrayList<String>();//arraylist to store parameters in programs execution
	public static int currentState = def;  //store current state
	public static int paraIndex = 0;  //store current parameter array' s index
	
	/*main function*/
	public static void execute() throws IOException
	{
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String [] lineInput = new String[100];
		String str;
		int i;
		int recFlag = 0;  //flag to judge if the string can be the basic data type
		int isContinue = 0;  //flag to judge if the state change need to continue
		int isRead = 0;  //flag to judge if the string is from read
		
		System.out.print("==>"); //read first line
		str = buffer.readLine();
		while(!str.contentEquals("exit"))  //if get "exit", quit the program
		{
			lineInput = str.split(" ");	  //split the line into strings
			for(i = 0; i < lineInput.length; i++)  //do judgment for each string
			{						
				if(currentState == annotation)  //if current state is annotation, all strings is to be ignored
					continue;
				if(isContain(Basic, lineInput[i]) != -1)  //judge if the string is a name of basic operation
				{
					currentState = isContain(Basic, lineInput[i]);  //if so, change current state to the new one
					
					if(currentState == make)  //if is make, judge if it is the first state
					{
						if(!state.isEmpty())  //if make is not the first state, find error
						{
							System.out.println("==>Syntax Error! Check make");
							state.clear();
							exeParam.clear();
							currentState = def;
							paraIndex = 0;
							break;
						}
						else  //make is the first one, clear parameters
						{
							exeParam.clear();
							paraIndex = 0;
						}						
					}
					state.add(currentState);  //store new state, and allocate two sapce for parameters
					exeParam.add(paraIndex++, "");
					exeParam.add(paraIndex++, "");
					continue;
				}			
				if(lineInput[i].charAt(0) == ':')  //if : ; do like thing but judge whether it is followed by the parameter
				{
					currentState = thing;
					state.add(currentState);
					exeParam.add(paraIndex++, "");
					exeParam.add(paraIndex++, "");
					
					if(lineInput[i].contentEquals(":"))
						continue;
					else
						lineInput[i] = "\"".concat(lineInput[i].substring(1));
				}				
				if(lineInput[i].length() >= 2 && lineInput[i].charAt(0) == '/' && lineInput[i].charAt(1) == '/')  //have two /, enter annotation state
				{
					currentState = annotation;
					continue;
				}
				
				recFlag = 0;   //set flag of recognize to 0		
				
				if(lineInput[i].contentEquals("read"))  //there read is seen to be a special input, just read the next line
				{
					isRead =1;
					System.out.print("==>");
					lineInput[i] = buffer.readLine();
				}
				
				if(lineInput[i].charAt(0) == '"')  //is a word
				{
					recFlag = 1;
				}
				
				if((lineInput[i].charAt(0) >= '0' && lineInput[i].charAt(0) <= '9') || lineInput[i].charAt(0) == '-')  //is a number header
				{
					if(judge_number(lineInput[i]) == 0)  //judge if really a number
					{
						System.out.println("==>Syntax Error!(number)");
						currentState = def;
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}			
					recFlag = 1;
				}
				
				if(lineInput[i].contentEquals("true") || lineInput[i].contentEquals("false"))  //is bool
				{
					if(isRead == 0)
					recFlag = 1;
					else
						recFlag = 0;
				}
			
				if(recFlag == 1)  //is recognized as basic data type
				{
					if(exeParam.isEmpty() || ( !exeParam.get(paraIndex - 2) .contentEquals("") && !exeParam.get(paraIndex - 1).contentEquals("")))
					{  //no space for the parameter, error
						System.out.println("==>Syntax Error!(parameter number)");
						currentState = def;
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}
					else if(exeParam.get(paraIndex - 2).contentEquals(""))  //have space and store
						exeParam.set(paraIndex - 2, lineInput[i]);
					else
						exeParam.set(paraIndex - 1, lineInput[i]);
					isRead =0;
				}
				else  //cannot recognize, error
				{
					isRead = 0;
					System.out.println("==>Syntax Error!(invalid input)");
					currentState = def;
					paraIndex = 0;
					state.clear();
					exeParam.clear();
					break;
				}
				
				while(true)  //check whether any operation can be done
				{
					isContinue = 0;
					if(currentState == def)  //default state, no need to check
						break;
					switch(currentState)  //according to current state, try its function
					{
					case make: isContinue = exe.do_make(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1)); break;
					case thing: isContinue = exe.do_thing(exeParam.get(paraIndex - 2)); break;
					case erase: isContinue = exe.do_erase(exeParam.get(paraIndex - 2)); break;
					case isname: isContinue = exe.do_isname(exeParam.get(paraIndex - 2)); break;
					case print: isContinue = exe.do_print(exeParam.get(paraIndex - 2)); break;
					case add: isContinue = NumberOpe.op_two_num(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "add"); break;
					case sub: isContinue = NumberOpe.op_two_num(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "sub"); break;
					case mul: isContinue = NumberOpe.op_two_num(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "mul"); break;
					case div: isContinue = NumberOpe.op_two_num(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "div"); break;
					case mod: isContinue = NumberOpe.op_two_num(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "mod"); break;
					case eq: isContinue = NumberOpe.op_two_judge(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "eq"); break;
					case gt: isContinue = NumberOpe.op_two_judge(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "gt"); break;
					case lt: isContinue = NumberOpe.op_two_judge(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "lt"); break;
					case and: isContinue = NumberOpe.op_two_logic(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "and"); break;
					case or: isContinue = NumberOpe.op_two_logic(exeParam.get(paraIndex - 2), exeParam.get(paraIndex - 1), "or"); break;
					case not: isContinue = NumberOpe.op_not(exeParam.get(paraIndex - 2)); break;		
					default:currentState = def;break;
					}
					if(isContinue != 1)  //cannnot continue, quit the loop
						break;						
					exeParam.remove(paraIndex - 1);  //recovery the space by the completed state
					exeParam.remove(paraIndex - 2);
					paraIndex = paraIndex - 2;
				
					if(state.size() == 1)  //it is the last state, change state to def
					{
						currentState = def;
						state.remove(0);
						break;
					}
					else  //not the last, change to its last one
					{
						currentState = state.get(state.size() - 2);
						state.remove(state.size() - 1);
					}				
				}
				
				if(isContinue == 0)  //number 0 means parameter is not enough
					continue;
				if(isContinue == -1)  // -1 means error
				{
					currentState = def;
					paraIndex = 0;
					state.clear();
					exeParam.clear();
					break;
				}
			}
			if(currentState ==annotation)  //one line is completed, if annotation, change to def
				currentState = def;
			System.out.print("==>");
			str = buffer.readLine();  //read next line
		}
		buffer.close();//program quit, close the stream
	}
	
	/*function to judge if the string is in basic operation*/
	public static int isContain(String [] arr, String key) 
	{
		int i;
		for(i = 0; i < arr.length; i++)
		{
			if(arr[i].contentEquals(key.toString()))
				return i;
		}
		return -1;
	}
	
	/*function to judge if a string with header of number is really a number*/
	public static int judge_number(String number)
	{		
		if(number.contentEquals("-") || number.charAt(number.length() - 1) == '.' || number.lastIndexOf('.') != number.indexOf('.'))
		{
			return 0;
		}
		
		int i;		
		for(i = 1; i < number.length(); i++)
			if(!Character.isDigit(number.charAt(i)) && number.charAt(i) != '.')
				break;
		if (i < number.length())
		{
			return 0;
		}
		return 1;
	}
	
	/*main function*/
/*------------------------------------------------------------------------------------------------------------------*/
	public static void main(String [] args) throws IOException
	{
		System.out.println("Input your MUA instruction:");
		execute();
	}
}