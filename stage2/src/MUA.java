import java.util.*;

/*main class of MUA*/
public class MUA 
{	
	public static Scanner scan = new Scanner(System.in);
	
	//hashmap to store the global namespace
	public static HashMap <String, Object> globalName = new HashMap<String, Object>();  	
	public static ArrayList<String> state = new ArrayList<String>();
	public static String currentState = "default";
	public static ArrayList<String> exeParam = new ArrayList<String>();
	public static int paraIndex = 0;
	public static int paraNumber = 2; //as to basic operation, at most two parameter is needed, can be changed by function
	public static int squareMatch = 0;  //judge square match in list
	public static int finishList = 0;  //variable to tell the following code the list reading finishs
	public static ArrayList<String> funcName = new ArrayList<String>();
	public static int funcReturnIndex = 0;
	
	
	/*function to initial the namespace with input all basic operation*/
	public static void initial()
	{
		globalName.put("make", null);
		globalName.put("thing", null);
		globalName.put("erase", null);
		globalName.put("isname", null);
		globalName.put("print", null);
		globalName.put("read", null);
		globalName.put("add", null);
		globalName.put("sub", null);
		globalName.put("mul", null);
		globalName.put("div", null);
		globalName.put("mod", null);
		globalName.put("eq", null);
		globalName.put("gt", null);
		globalName.put("lt", null);
		globalName.put("and", null);
		globalName.put("or", null);
		globalName.put("not", null);		
		globalName.put("readlist", null);
		globalName.put("repeat", null);		
		globalName.put("output", null);
		globalName.put("stop", null);
		globalName.put("export", null);		
		globalName.put("isnumber", null);
		globalName.put("isword", null);
		globalName.put("islist", null);
		globalName.put("isbool", null);
		globalName.put("isempty", null);
		globalName.put("random", null);
		globalName.put("sqrt", null);
		globalName.put("int", null);
	}
	
	public static void explain(String input,int isGlobal)
	{
		String lineInput[] = input.split(" ");	  //split the line into strings
		int i, j;
		int recFlag;
		int isContinue;
		int isOpe = 0;
		String one = "";
		for(i = 0; i < lineInput.length; i++)  //do judgment for each string
		{
			isOpe = 0;
			recFlag = 0;   //set flag of recognize to 0
			
			if(currentState.equals("annotation"))  //if current state is annotation, all strings is to be ignored
				continue;
			
			if(currentState.equals("list"))  //if is reading a list, concat the word judge whether reading finishs
			{
				one = one + " " + lineInput[i];
				if(lineInput[i].equals("["))
					squareMatch++;				
				if(lineInput[i].equals("]"))
					squareMatch--;
				if(squareMatch != 0)
					continue;
				else
				{
					finishList = 1;
					currentState = state.get(state.size() - 1);
				}	
			}
			else  //not read a list, one = the single word
				one = lineInput[i];
			
			//judge if the string is in namespace and is basic operation
			if(globalName.containsKey(one) && globalName.get(one) == null && !one.equals("read") && !one.equals("readlist"))
			{
				currentState = one;  //if so, change current state to the new one				
				state.add(currentState);  //store new state, and allocate two sapce for parameters
				for(j = 0; j < paraNumber; j++)
					exeParam.add(paraIndex++, "");
				isOpe = 1;										
			}		
			else if(one.charAt(0) == ':')  //if : ; do like thing but judge whether it is followed by the parameter
			{
				currentState = "thing";
				state.add(currentState);
				for(j = 0; j < paraNumber; j++)
				{
					exeParam.add(paraIndex++, "");
				}
				
				if(one.contentEquals(":"))
					isOpe = 1;
				else
					one = "\"".concat(one.substring(1));
			}				
			else if(globalName.containsKey(one) && isFunc(one) != -1)
			{
				currentState = one;
				state.add(currentState);
				funcName.add(one);
				
				if(paraIndex != 0)
				{
					for(j = 0; j < paraNumber; j++)
					{
						if(exeParam.get(paraIndex - paraNumber + j).contentEquals(""))
						{
							funcReturnIndex = paraIndex - paraNumber + j;
							break;
						}
					}
				}
								
				if(isFunc(one) > 2)
					paraNumber = isFunc(one);
				
				for(j = 0; j < paraNumber; j++)
				{
					exeParam.add(paraIndex++, "");
				}		
				isOpe = 1;
			}
			
			if(isOpe == 0)
			{
				if(one.length() >= 2 && one.charAt(0) == '/' && one.charAt(1) == '/')  //have two /, enter annotation state
				{
					currentState = "annotation";
					continue;
				}			
				
				if(one.equals("["))
				{
					currentState = "list";
					squareMatch++;
					continue;
				}
				
				if(one.contentEquals("read"))  //there read is seen to be a special input, just read the next line
				{
					System.out.print("==>");
					one = scan.nextLine();
					
					if(one.contains(" "))
					{
						System.out.println("==>can not read more than one string!");
						currentState = "default";
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}		
					recFlag = 1;
				}
				
				if(one.contentEquals("readlist"))  //there readlist is seen to be a special input, read the next line
				{
					System.out.print("==>");
					one = scan.nextLine();
					
					if(judge_list(one) == 0)
					{
						System.out.println("==>illegal list!");
						currentState = "default";
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}
					finishList = 1;
				}
				
				if(one.charAt(0) == '"')  //is a word, change to its value
				{
					recFlag = 1;
					one = one.substring(1);
				}
				
				if((one.charAt(0) >= '0' && one.charAt(0) <= '9') || one.charAt(0) == '-')  //is a number header
				{
					if(judge_number(one) == 0)  //judge if really a number
					{
						System.out.println("==>Syntax Error!(number)");
						currentState = "default";
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}			
					recFlag = 1;
				}
				
				if(one.contentEquals("true") || one.contentEquals("false"))  //is bool
				{
						recFlag = 1;
				}
			
				if(recFlag == 1  || finishList == 1)  //is recognized as basic data type(word number bool)
				{
					if(exeParam.isEmpty())
					{  //no space for the parameter, error
						System.out.println("==>Syntax Error!(parameter number)");
						currentState = "default";
						paraIndex = 0;
						state.clear();
						exeParam.clear();
						break;
					}
					else 
						{
							for(j = 0; j < paraNumber; j++)
							{
								if(exeParam.get(paraIndex - paraNumber + j).contentEquals(""))  //have space and store
									{
										exeParam.set(paraIndex - paraNumber + j, one);
										break;
									}
							}
							if(j == paraNumber)
							{
								//no space for the parameter, error
								System.out.println("==>Syntax Error!");
								currentState = "default";
								paraIndex = 0;
								state.clear();
								exeParam.clear();
								break;
							}
						}
				}
				else  //cannot recognize, error
				{
					System.out.println("==>Syntax Error!(cannot recognize input, check type)");
					currentState = "default";
					paraIndex = 0;
					state.clear();
					exeParam.clear();
					break;
				}
			}
			
/*---------------------------------------------------------------------------------------------------------------------------*/	
			while(true)  //check whether any operation can be done
			{
				isContinue = 0;	
				if(currentState == "default")  //default state, no need to check
					break;
				if(currentState.equals("repeat") && Exe.isRepeat == 1)
					break;
				if(isFunc(currentState) != -1 && Func.isDoFunc == 1)
					break;							
				switch(currentState)  //according to current state, try its function
				{
				case "make": isContinue = Exe.do_make(isGlobal, exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1)); break;
				case "thing": isContinue = Exe.do_thing(isGlobal, exeParam.get(paraIndex - paraNumber)); break;
				case "erase": isContinue = Exe.do_erase(isGlobal, exeParam.get(paraIndex - paraNumber)); break;
				case "isname": isContinue = Exe.do_isname(isGlobal, exeParam.get(paraIndex - paraNumber)); break;
				case "print": isContinue = Exe.do_print(exeParam.get(paraIndex - paraNumber)); break;
				case "add": isContinue = NumOpe.op_two_num(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "add"); break;
				case "sub": isContinue = NumOpe.op_two_num(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "sub"); break;
				case "mul": isContinue = NumOpe.op_two_num(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "mul"); break;
				case "div": isContinue = NumOpe.op_two_num(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "div"); break;
				case "mod": isContinue = NumOpe.op_two_num(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "mod"); break;
				case "eq": isContinue = NumOpe.op_two_judge(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "eq"); break;
				case "gt": isContinue = NumOpe.op_two_judge(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "gt"); break;
				case "lt": isContinue = NumOpe.op_two_judge(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "lt"); break;
				case "and": isContinue = NumOpe.op_two_logic(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "and"); break;
				case "or": isContinue = NumOpe.op_two_logic(exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1), "or"); break;
				case "not": isContinue = NumOpe.op_not(exeParam.get(paraIndex - paraNumber)); break;	
				case "repeat": isContinue = Exe.do_repeat(isGlobal, exeParam.get(paraIndex - paraNumber), exeParam.get(paraIndex - paraNumber + 1)); break;
				case "output": isContinue = Func.do_output(exeParam.get(paraIndex - paraNumber)); break;
				case "stop": isContinue = 1; break;
				case "export": isContinue = Func.do_export(); break;
				case "isnumber": isContinue = Exe.do_isnumber(exeParam.get(paraIndex - paraNumber)); break;
				case "isword": isContinue = Exe.do_isword(exeParam.get(paraIndex - paraNumber)); break;
				case "islist": isContinue = Exe.do_islist(exeParam.get(paraIndex - paraNumber)); break;
				case "isbool": isContinue = Exe.do_isbool(exeParam.get(paraIndex - paraNumber)); break;
				case "isempty": isContinue = Exe.do_isempty(exeParam.get(paraIndex - paraNumber)); break;
				case "random": isContinue = Exe.do_random(exeParam.get(paraIndex - paraNumber)); break;
				case "sqrt": isContinue = Exe.do_sqrt(exeParam.get(paraIndex - paraNumber)); break;
				case "int": isContinue = Exe.do_int(exeParam.get(paraIndex - paraNumber)); break;
				default: isContinue = Func.func(); break;
				}
				
				if(isContinue != 1)  //cannot continue, quit the loop
					break;
									
				for(j = 1; j <= paraNumber; j++)
					exeParam.remove(paraIndex - j); //recovery the space by the completed state					
				paraIndex = paraIndex - paraNumber;		
				
				if(currentState.equals("stop"))
					break;
			
				if(isFunc(currentState) != -1)  //reset paraNumber, remove the function completed
				{
					funcName.remove(funcName.size() - 1);
					if(funcName.isEmpty())
						paraNumber = 2;
					else
					{
						if(isFunc(funcName.get(funcName.size() - 1)) > 2)
							paraNumber = isFunc(funcName.get(funcName.size() - 1));
						else
							paraNumber = 2;
					}
				}
				
				if(state.size() == 1)  //it is the last state, change state to def
				{
					currentState = "default";
					state.remove(0);
					break;
				}
				currentState = state.get(state.size() - 2);
				state.remove(state.size() - 1);
			}
			
			if(currentState.equals("stop"))
			{					
				currentState = state.get(state.size() - 2);
				state.remove(state.size() - 1);	
				while(currentState != funcName.get(funcName.size() - 1))
				{
					for(j = 1; j <= paraNumber; j++)
						exeParam.remove(paraIndex - j); //recovery the space by the completed state					
					paraIndex = paraIndex - paraNumber;
					currentState = state.get(state.size() - 2);
					state.remove(state.size() - 1);	
				}
				break;
			}
			
			if(isContinue == 0)  //number 0 means parameter is not enough
				continue;
			if(isContinue == -1)  // -1 means error
			{
				currentState = "default";
				paraIndex = 0;
				state.clear();
				funcName.clear();
				exeParam.clear();
				break;
			}		
		}
		if(currentState == "annotation")  //one line is completed, if annotation, change to def
			currentState = "default";
	}
	
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

	public static int judge_list(String list)
	{
		String [] split = list.split(" ");
		int i;
		int square = 0;
		for(i = 0; i < split.length; i++)
		{
			if(split[i].equals("["))
				square++;
			if(split[i].equals("]"))
				square--;		
		}
		if(square != 0)
			return 0;
		return 1;
	}
	
	public static int isFunc(String name)
	{		
		if(globalName.get(name) == null)
			return -1;
		
		String funcList = globalName.get(name).toString();
		if(judge_list(funcList) != 1)
			return -1;
		int index = funcList.indexOf(']');
		if(judge_list(funcList.substring(2, index + 1)) != 1)
			return -1;
		if(judge_list(funcList.substring(index + 2, funcList.length() - 2)) != 1)
			return -1;
		String [] sp = funcList.substring(2, index + 1).split(" ");
		return sp.length - 2;
	}
	
	public static void main(String[] args) 
	{
		initial();
		String input = "";
		System.out.print("==>");
		input = scan.nextLine();
		while(true)
		{
			if(input.equals("exit"))
				break;
			explain(input, 1);
			System.out.print("==>");
			input = scan.nextLine();
		}
		System.out.println("==>Exit successfully!");
		scan.close();
	}
}
