import java.io.*;
import java.util.*;

public class exe
{
	/*function for make*/
/*--------------------------------------------------------------------------------------------------------------------*/	
	public static int do_make(String word, String value)
	{
		if(word.contentEquals("") || value.contentEquals(""))  //if parameters is not enough, continue to read input
			return 0;
		if(word.charAt(0) != '"')  //if input string is not start with ", error
		{
			System.out.println("illegal word!");
			return -1;
		}
		if(isLetter(word.charAt(1)) != 1) //if the word is not start with letter, error
		{
			System.out.println("illegal word!");
			return -1;
		}
		
		int index = isInName(word);  //judge if the name exist
		if(index < compile.name.size())  //exist, just change the value
			compile.value.set(index, value);
		else  //not exist, add to the space
		{
			compile.name.add(word);
			compile.value.add(value);
		}
		return 1;  //successful with 1 return
	}
	
	/*function for thing*/
/*---------------------------------------------------------------------------------------------------------------------*/	
	public static int do_thing(String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue to read
			return 0;
		
		int index = isInName(name);  //judge if is a name, not then error
		if(index  == compile.name.size())
		{
			System.out.println("==>can not find the name!");
			return -1;
		}
		
		if(compile.paraIndex >= 4)  //return the value to last operation
		{
			if(compile.exeParam.get(compile.paraIndex - 4).contentEquals(""))
				compile.exeParam.set(compile.paraIndex - 4, compile.value.get(index));
			else if(compile.exeParam.get(compile.paraIndex - 3).contentEquals(""))
				compile.exeParam.set(compile.paraIndex - 3, compile.value.get(index));
			else
			{
				System.out.println("==>parameter number error");
				return -1;
			}
		}
		else if(compile.paraIndex == 2)  //just do thing, have no affection
			;
		else
		{
			System.out.println("==>parameter number error");
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
		if(value.charAt(0) == '"')  //output word, without "
			System.out.println(value.substring(1));
		else
			System.out.println(value);
		return 1;
	}
	
	/*function for erase*/
/*-------------------------------------------------------------------------------------------------------------------------*/	
	public static int do_erase(String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue
			return 0;
		
		int index = isInName(name);  //judge if the name exist
		if(index  == compile.name.size())  //not exist, error
		{
			System.out.println("==>can not find the name! No erase");
			return -1;
		}  //exist, remove name and its value
		compile.name.remove(index);
		compile.value.remove(index);
		System.out.println("==>Erase Success!");
		return 1;
	}
	
	/*function for isname*/
/*---------------------------------------------------------------------------------------------------------------------------*/	
	public static int do_isname(String name)
	{
		if(name.contentEquals(""))  //parameter is not enough, continue
			return 0;
		
		int index = isInName(name);  //judge if name exist and return value to last operation
		String result;
		if(index == compile.name.size())
		{
			result = "false";
		}
		else
			result ="true";
		
		if(compile.paraIndex >= 4)
		{
			if(compile.exeParam.get(compile.paraIndex - 4).contentEquals(""))
				compile.exeParam.set(compile.paraIndex - 4, result);
			else if(compile.exeParam.get(compile.paraIndex - 3).contentEquals(""))
				compile.exeParam.set(compile.paraIndex - 3, result);
			else
			{
				System.out.println("==>parameter number error");
				return -1;
			}
		}
		else if(compile.paraIndex == 2)
			;
		else
		{
			System.out.println("==>parameter number error");
			return -1;
		}
		return 1;
	}
	
	
	/*function to judge if the input is a name*/
/*------------------------------------------------------------------------------------------------------------------------*/	
	public static int isInName(String word)
	{
		int i = 0;
		for(i = 0; i < compile.name.size(); i++)
		{
			if(compile.name.get(i).contentEquals(word))
				return i;
		}
		return i;
	}
	
	/*function to judge a character is a letter*/
	public static int isLetter(char c)
	{
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return 1;
		return 0;
	}
}