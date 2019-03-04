import java.io.*;
import java.util.*;

public class NumOpe 
{
	public static int op_two_num(String value1, String value2, String op)
	{
		if(value1.contentEquals("") || value2.contentEquals(""))  // //if parameters is not enough, continue to read input
			return 0;
		
		if(MUA.judge_number(value1) != 1 || MUA.judge_number(value2) != 1)  //if not all number, error
		{
			System.out.println("==>illegal number");
			return -1;
		}
		
		double op1 = Double.valueOf(value1);  //change string to double
		double op2 = Double.valueOf(value2);
		double result;
		switch(op)  //execute
		{
		case "add": result = op1 + op2; break;
		case "sub": result = op1 - op2; break;
		case "mul": result = op1 * op2; break;
		case "div": result = op1 /op2; break;
		case "mod": result = op1 % op2; break;
		default: result =0; break;
		}
		
		if(Exe.setValue(String.valueOf(result)) == -1)
			return -1;
		return 1;
	}

/*-------------------------------------------------------------------------------------------------------------------------*/
	public static int op_two_judge(String value1, String value2, String op)
	{
		if(value1.contentEquals("") || value2.contentEquals(""))   //if parameters is not enough, continue to read input
			return 0;
		
		boolean re = false;
		if(MUA.judge_number(value1) == 1 && MUA.judge_number(value2) == 1)
		{
			double op1 = Double.valueOf(value1);
			double op2 = Double.valueOf(value2);
			switch(op)
			{
			case "eq": re = op1 == op2; break;
			case "gt": re = op1 > op2; break;
			case "lt": re = op1 < op2; break;
			}
		}
		else
		{
			if(value1.contains(" ") || value2.contains(" "))
			{
				System.out.println("==>no list to compare!");
				return -1;
			}
			String op1 = value1;
			String op2 = value2;
			switch(op)
			{
			case "eq": re = op1.contentEquals(op2); break;
			case "gt": re = op1.compareTo(op2) > 0 ? true : false; break;
			case "lt" : re = op1.compareTo(op2) < 0 ? true : false; break;
			}
		}
						
		String result = re == true ? "true" : "false";
				
		if(Exe.setValue(result) == -1)
			return -1;
		return 1;
	}
	
/*----------------------------------------------------------------------------------------------------------------------------------*/
	public static int op_two_logic(String value1, String value2, String op)
	{
		if(value1.contentEquals("") || value2.contentEquals(""))   //if parameters is not enough, continue to read input
			return 0;
				
		if(!value1.contentEquals("true") && !value1.contentEquals("false"))  //judge the input
		{
			System.out.println("==>illegal parameter(not bool)");
			return -1;
		}
		else
		{
			if(!value2.contentEquals("true") && !value2.contentEquals("false"))
			{
				System.out.println("==>illegal parameter(not bool)");
				return -1;
			}
			else  //input is valid, get bool value and operate
			{
				boolean op1 = value1.contentEquals("true") ? true : false;
				boolean op2 = value2.contentEquals("true") ? true : false;
				
				boolean re;
				switch(op)
				{
				case "and": re = op1 && op2; break;
				case "or": re = op1 || op2; break;
				default: re = false; break;
				}
				
				String result;
				if(re == true)
					result = "true";
				else
					result = "false";
				if(Exe.setValue(result) == -1)
					return -1;
			}
		}
		return 1;
	}
	
/*-------------------------------------------------------------------------------------------------------------------*/
	public static int op_not(String value)
	{
		if(value.contentEquals(""))   //if parameters is not enough, continue to read input
			return 0;
		if(!value.contentEquals("true") && !value.contentEquals("false"))  //judge the input
		{
			System.out.println("==>illegalparameter(not bool)");
			return -1;
		}
		
		String result = value.contentEquals("true") ? "false" : "true";  //calculate and return
		if(Exe.setValue(result) == -1)
			return -1;
		return 1;
	}
}
