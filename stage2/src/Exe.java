import java.util.*;

public class Exe
{
	public static int isRepeat = 0;
	/*function for make*/
/*--------------------------------------------------------------------------------------------------------------------*/	
	public static int do_make(int isGlobal, String word, String value)
	{
		if(word.contentEquals("") || value.contentEquals(""))  //if parameters is not enough, continue to read input
			return 0;

		if(isLetter(word.charAt(0)) != 1) //if the word is not start with letter, error
		{
			System.out.println("==>illegal word!");
			return -1;
		}
		
		if(isGlobal == 0)
			Func.localName.put(word, value);
		else
			MUA.globalName.put(word, value);
		return 1;  //successful with 1 return
	}
	
	/*function for thing*/
/*---------------------------------------------------------------------------------------------------------------------*/	
	public static int do_thing(int isGlobal, String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue to read
			return 0;
				
		if(isGlobal == 0)
		{
			if(Func.localName.containsKey(name))
			{
				if(setValue(Func.localName.get(name).toString()) == -1)
					return -1;
			}
			else
			{
				if(!MUA.globalName.containsKey(name) || MUA.globalName.get(name) == null)
				{
					System.out.println("==>can not find the name or it is a basic operation");
					return -1;
				}
				if(setValue(MUA.globalName.get(name).toString()) == -1)
					return -1;
			}
		}
		else
		{
			if(!MUA.globalName.containsKey(name) || MUA.globalName.get(name) == null)
			{
				System.out.println("==>can not find the name or it is a basic operation!");
				return -1;
			}
			if(setValue(MUA.globalName.get(name).toString()) == -1)
				return -1;
		}		
		return 1;
	}
	
	/*function for print*/
/*-------------------------------------------------------------------------------------------------------------------------*/	
	public static int do_print(String value)
	{
		if(value.contentEquals(""))  //parameter is not enough, continue
			return 0;		
		System.out.print("==>");
		System.out.println(value);
		return 1;
	}
	
	/*function for erase*/
/*-------------------------------------------------------------------------------------------------------------------------*/	
	public static int do_erase(int isGlobal, String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue
			return 0;
		
		if(!MUA.globalName.containsKey(name))  //not exist, error
		{
			System.out.println("==>can not find the name! No erase");
			return -1;
		}  
		//exist, remove name and its value
		if(isGlobal == 0)
		{
			if(Func.localName.containsKey(name))
				Func.localName.remove(name);
			else
				MUA.globalName.remove(name);
		}
		else
			MUA.globalName.remove(name);
		System.out.println("==>Erase Success!");
		return 1;
	}
	
	/*function for isname*/
/*---------------------------------------------------------------------------------------------------------------------------*/	
	public static int do_isname(int isGlobal, String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue
			return 0;
		
		String result;
		
		if(isGlobal == 0)
		{
			if(Func.localName.containsKey(name))
				result = "true";
			else
			{
				if(!MUA.globalName.containsKey(name))
				{
					result = "false";
				}
				else
					result ="true";
			}
		}
		else
		{
			if(!MUA.globalName.containsKey(name))
			{
				result = "false";
			}
			else
				result ="true";
		}
				
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for repeat*/
/*----------------------------------------------------------------------------------------------------------*/
	public static int do_repeat(int isGlobal, String number, String list)
	{
		if(number == "" || list == "")
			return 0;
		if(isInt(number) != 1)
		{
			System.out.println("==>operation repeat parameter number wrong!");
			return -1;
		}
		if(MUA.judge_list(list) != 1)
		{
			System.out.println("==>operatoin repeat parameter list wrong!");
			return -1;
		}
		
		String command = list.substring(2, list.length() - 2);
		int num = Integer.valueOf(number);
		int i;
		isRepeat = 1;
		for(i = 0; i < num ; i++)
		{
			MUA.explain(command, isGlobal);
		}
		isRepeat = 0;
		return 1;		
	}
	
	/*function for isnumber*/
/*-------------------------------------------------------------------------------------------------------*/
	public static int do_isnumber(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_number(in) == 1)
			result = "true";
		else
			result = "false";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for isword*/
/*-------------------------------------------------------------------------------------------------------*/
	public static int do_isword(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(!in.contains(" "))
			result = "true";
		else
			result = "false";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for isbool*/
/*-------------------------------------------------------------------------------------------------------*/
	public static int do_isbool(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(in.equals("true") || in.equals("false"))
			result = "true";
		else
			result = "false";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for islist*/
/*-------------------------------------------------------------------------------------------------------*/
	public static int do_islist(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_list(in) == 1)
			result = "true";
		else
			result = "false";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for isempty*/
/*-------------------------------------------------------------------------------------------------------*/
	public static int do_isempty(String in)
	{
		if(in.equals(""))
			return 0;
		String result = "false";
		if(MUA.judge_list(in) != 1 && in.contains(" "))
		{
			System.out.println("==>error, not list or word");
			return -1;
		}
		if(MUA.judge_list(in) == 1)
		{
			if(in.equals("[ ]"))
				result = "true";
			else
				result = "false";
		}
		if(!in.contains(" "))
		{
			if(in.equals(""))
				result = "true";
			else
				result = "false";
		}
		
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
	/*function for random*/
/*-------------------------------------------------------------------------------------------------------*/	
	public static int do_random(String in)
	{
		if(in.equals(""))
			return 0;
		if(isInt(in) != 1)
		{
			System.out.println("==> input a right number for random(positive natural)");
			return -1;
		}
		
		Random rand = new Random();
		Integer result = rand.nextInt(Integer.valueOf(in));
				
		if(setValue(result.toString()) == -1)
			return -1;
		return 1;
	}
	
	/*function for sqrt*/
/*-------------------------------------------------------------------------------------------------------*/	
	public static int do_sqrt(String in)
	{
		if(in.equals(""))
			return 0;
		if(MUA.judge_number(in) != 1 || in.charAt(0) == '-')
		{
			System.out.println("==> input a right number for sqrt(positive natural)");
			return -1;
		}
		
		Double result = Math.sqrt(Double.valueOf(in));
				
		if(setValue(result.toString()) == -1)
			return -1;
		return 1;
	}
	
	
	/*function for int*/
/*-------------------------------------------------------------------------------------------------------*/	
	public static int do_int(String in)
	{
		if(in.equals(""))
			return 0;
		if(MUA.judge_number(in) != 1)
		{
			System.out.println("==> input a right number for sqrt(positive natural)");
			return -1;
		}
		
		Double result = Math.floor(Double.valueOf(in));
				
		if(setValue(result.toString()) == -1)
			return -1;
		return 1;
	}
	
	
	/*function to judge a character is a letter*/
	public static int isLetter(char c)
	{
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return 1;
		return 0;
	}
	
	public static int isInt(String number)
	{
		int i;
		for(i = 0; i < number.length(); i++)
		{
			if(number.charAt(i) >= '0' && number.charAt(i) <= '9')
				;
			else
				return 0;
		}
		return 1;
	}
	
	public static int setValue(String result)
	{
		int i;
		if(MUA.paraIndex >= 2 * MUA.paraNumber)  //return the value to last operation
		{
			for(i = 0; i < MUA.paraNumber; i++)
			{
				if(MUA.exeParam.get(MUA.paraIndex - 2 * MUA.paraNumber + i).equals(""))
				{
					MUA.exeParam.set(MUA.paraIndex - 2 * MUA.paraNumber + i, result);
					break;
				}
			}
			if(i == MUA.paraNumber)
			{
				System.out.println("==>parameter number error");
				return -1;
			}
		}
		else if(MUA.paraIndex == MUA.paraNumber)  //just do thing, have no affection
			;
		else
		{
			System.out.println("==>parameter number error");
			return -1;
		}
		return 1;
	}
}