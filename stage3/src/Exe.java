import java.util.*;
import java.io.*;

public class Exe
{
	public static int isRepeat = 0;
	public static int isRun = 0;
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
			Func.localName.get(Func.localName.size() - 1).put(word, value);
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
			if(Func.localName.get(Func.localName.size() - 1).containsKey(name))
			{
				if(setValue(Func.localName.get(Func.localName.size() - 1).get(name).toString()) == -1)
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
		 
		//exist, remove name and its value
		if(isGlobal == 0)
		{
			if(Func.localName.get(Func.localName.size() - 1).containsKey(name))
				Func.localName.get(Func.localName.size() - 1).remove(name);
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
			if(Func.localName.get(Func.localName.size() - 1).containsKey(name))
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
	
	/*word function*/
/*-----------------------------------------------------------------------------------------------------------*/
	
	public static int do_word(String w1, String w2)
	{
		if(w1.equals("") || w2.equals(""))
			return 0;
		
		if(MUA.judge_list(w1) == 1)
		{
			System.out.println("==>parameter 1 of word is not a word");
			return -1;
		}	
		if(MUA.judge_list(w2) == 1)
		{
			System.out.println("==>parameter 2 of word is not a word");
			return -1;
		}
		String result = "" + w1 + w2;
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
	
/*------------------------------------------------------------------------------------------*/
	public static int do_if(int isGlobal, String cond, String list1, String list2)
	{
		if(cond.equals("") || list1.equals("") || list2.equals(""))
			return 0;
		if(!cond.equals("true") && !cond.equals("false"))
			return -1;
		if(MUA.judge_list(list1) == 0 || MUA.judge_list(list2) == 0)
			return -1;
		if(cond.equals("true"))
		{
			MUA.ifQueue.set(MUA.ifQueue.size() - 1, 1);
			MUA.explain(list1.substring(1, list1.length() - 1), isGlobal);
		}		
		else
		{
			MUA.ifQueue.set(MUA.ifQueue.size() - 1, 1);
			MUA.explain(list2.substring(1, list2.length() - 1), isGlobal);
		}	
		MUA.ifQueue.remove(MUA.ifQueue.size() - 1);
		return 1;
	}
/*------------------------------------------------------------------------------------*/	
	public static int do_sentence(String v1, String v2)
	{
		if(v1.equals("") || v2.equals(""))
			return 0;
		String result;
		
		if(MUA.judge_list(v1) == 1)
		{
			if(MUA.judge_list(v2) == 1)
				result = v1.substring(0, v1.length() - 1) + v2.substring(2);
			else
				result = v1.substring(0, v1.length() - 1) + v2 + " ]";
		}	
		else 
		{
			if(MUA.judge_list(v2) == 1)
				result = "[ " + v1 + v2.substring(1);
			else
				result = "[ " + v1 +" " + v2 + " ]";
		}
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*-----------------------------------------------------------------------------------*/	
	public static int do_list(String v1, String v2)
	{
		if(v1.equals("") || v2.equals(""))
			return 0;
		String result = "[ " + v1 + " " + v2 + " ]";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*----------------------------------------------------------------------------------------*/	
	public static int do_join(String list, String value)
	{
		if(list.equals("") || value.equals(""))
			return 0;
		if(MUA.judge_list(list) == 0)
			return -1;
		String result = list.substring(0, list.length() - 1) + value + " ]";
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*---------------------------------------------------------------------------------------*/	
	public static int do_first(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_list(in) == 0)
		{
			if(in.equals(""))
				result = "";
			else
				result = in.substring(0,1);
		}
		else
		{
			if(in.equals("[ ]"))
				result = "";
			else
			{
				result = in.substring(2);
				result = result.substring(0, result.indexOf(" "));
			}			
		}
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*--------------------------------------------------------------------------------*/	
	public static int do_last(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_list(in) == 0)
		{
			if(in.equals(""))
				result = "";
			else
				result = in.substring(in.length() - 1, in.length());
		}		
		else
		{
			if(in.equals("[ ]"))
				result = "";
			else
			{
				result = in.substring(0, in.length() - 2);
				result = result.substring(result.lastIndexOf(" ") + 1, result.length());
			}
				
		}
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*------------------------------------------------------------------------------------------*/	
	public static int do_butfirst(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_list(in) == 0)
		{
			if(in.equals(""))
				result = "";
			else
				result = in.substring(1);
		}			
		else
		{
			if(in.equals("[ ]"))
				result = "";
			else
			{
				result = in.substring(2);
				result = "[" + result.substring(result.indexOf(" "));
			}			
		}
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*----------------------------------------------------------------------------*/
	public static int do_butlast(String in)
	{
		if(in.equals(""))
			return 0;
		String result;
		if(MUA.judge_list(in) == 0)
		{
			if(in.equals(""))
				result = "";
			else
				result = in.substring(0, in.length() - 1);
		}		
		else
		{
			if(in.equals("[ ]"))
				result = "";
			else
			{
				result = in.substring(0, in.length() - 2);
				result = result.substring(0, result.lastIndexOf(" ") + 1) + " ]";
			}
				
		}
		if(setValue(result) == -1)
			return -1;
		return 1;
	}
/*---------------------------------------------------------------------------*/
	public static int do_wait(String time)
	{
		if(time.equals(""))
			return 0;
		if(MUA.judge_number(time) == 0)
			return -1;
		new Thread(){
			public void run(){
			try {
				System.out.println("==>wait...");
				Thread.sleep(Integer.valueOf(time));	
			} catch (InterruptedException e) { }
			}
			}.start();
		return 1;
	}
/*------------------------------------------------------------------------------*/	
	public static int do_erall(int isGlobal)
	{
		if(isGlobal == 1)
			MUA.globalName.clear();
		else if(isGlobal == 0)
			Func.localName.clear();
		return 1;		
	}
/*----------------------------------------------------------------------------*/	
	public static int do_poall(int isGlobal)
	{
		if(isGlobal == 1)
		{
			Iterator<String> iterator = MUA.globalName.keySet().iterator();
			while(iterator.hasNext())
			{
				String name = iterator.next();
				System.out.println("==>" + name);
			}
		}
		else if(isGlobal == 0)
		{
			Iterator<String> iterator = Func.localName.get(Func.localName.size() - 1).keySet().iterator();
			while(iterator.hasNext())
			{
				String name = iterator.next();
				System.out.println("==>" + name);
			}
		}
		return 1;
	}
	
	public static int do_save(int isGlobal, String name) throws IOException
	{
		if(name.equals(""))
			return 0;
		if(MUA.judge_list(name) == 1)
			return -1;
		File file = new File(name);
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		
		if(isGlobal == 1)
		{
			Iterator<String> iterator = MUA.globalName.keySet().iterator();
			while(iterator.hasNext())
			{
				String outname = iterator.next();
				if(MUA.globalName.get(outname) == null)
				{
					fw.write(outname + "\t\r\n");
				}
				else
				{
					String outvalue = MUA.globalName.get(outname).toString();
					fw.write(outname + "\t" + outvalue + "\r\n");
				}				
			}
		}
		else
		{
			Iterator<String> iterator = Func.localName.get(Func.localName.size() - 1).keySet().iterator();
			while(iterator.hasNext())
			{
				String outname = iterator.next();
				if(Func.localName.get(Func.localName.size() - 1).get(outname) == null)
				{
					fw.write(outname + "\t\n");
				}
				else
				{
					String outvalue = Func.localName.get(Func.localName.size() - 1).get(outname).toString();
					fw.write(outname + "\t" + outvalue + "\n");
				}	
			}
		}
		fw.close();
		return 1;
	}
/*---------------------------------------------------------------------------------------------------------------*/
	public static int do_load(int isGlobal, String name) throws IOException
	{
		if(name.equals(""))
			return 0;
		if(MUA.judge_list(name) == 1)
			return -1;
		File file = new File(name);
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));  
		String line = null;
		while ((line = reader.readLine()) != null) 
		{  
              String entry[] = line.split("\t");
              if(isGlobal == 1)
              {
            	  if(entry.length == 2)
            		  MUA.globalName.put(entry[0], entry[1]);
            	  else
            		  MUA.globalName.put(entry[0], null);
              }
              else
              {
            	  if(entry.length == 2)
            		  Func.localName.get(Func.localName.size() - 1).put(entry[0], entry[1]);
            	  else
            		  Func.localName.get(Func.localName.size() - 1).put(entry[0], null);
              }
              
        }  
		reader.close();
		return 1;
	}
/*---------------------------------------------------------------------------------------------------------*/	
	public static int do_run(int isGlobal, String listToRun)
	{
		if(listToRun.equals(""))
			return 0;
		if(MUA.judge_list(listToRun) != 1)
			return -1;
		isRun = 1;
		MUA.explain(listToRun.substring(2, listToRun.length() - 2), isGlobal);
		isRun = 0;
		return 1;
	}
	
/*non-operation functions*/
/*-----------------------------------------------------------------------------------------------------------*/
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
	
/*----------------------------------------------------------------------------------------*/
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