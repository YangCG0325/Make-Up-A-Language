import java.util.*;

public class Func 
{
	public static HashMap<String, Object> localName = new HashMap<String, Object>();
	
	public static int isDoFunc = 0;
	
	public static int func()
	{
		
		int i, j;
		int paraNeed = MUA.isFunc(MUA.currentState);
		String funcList = MUA.globalName.get(MUA.currentState).toString();
		
		if(paraNeed == 0)
		{
			isDoFunc = 1;
			MUA.explain(funcList.substring(funcList.indexOf(']') + 4, funcList.length() - 4), 0);
			isDoFunc = 0;
			localName.clear();
			return 1;
		}
		
		for(i = 0; i < paraNeed; i++)
		{
			if(MUA.exeParam.get(MUA.paraIndex - MUA.paraNumber + i).equals(""))
				return 0;
		}
		
		
		String [] para = funcList.substring(2, funcList.indexOf(']') + 1).split(" ");
		String code = funcList.substring(funcList.indexOf(']') + 4, funcList.length() - 4);
		String []spCode = code.split(" ");
		code = "";
		
		for(i = 1; i <= paraNeed; i++)
		{							
			for(j = 0; j < spCode.length; j++)
			{
				if(MUA.globalName.containsKey(spCode[j]) && MUA.globalName.get(spCode[j]) == null)
					;
				else
				{
					if(spCode[j].contains(para[i]))
						spCode[j] = spCode[j].replace(para[i], MUA.exeParam.get(MUA.paraIndex - MUA.paraNumber + i - 1));
				}									
				if(j == 0)
					code = code + spCode[j];
				else
					code = code + " " + spCode[j];
			}
		}
		
		isDoFunc = 1;
		MUA.explain(code , 0);
		isDoFunc = 0;
		localName.clear();
		return 1;			
	}
	
	public static int do_output(String value)
	{
		if(value.equals(""))
			return 0;
		MUA.exeParam.set(MUA.funcReturnIndex, value);
		return 1;
	}
	
	public static int do_export()
	{
		Set<String> c1 = localName.keySet();
		Iterator<String> iter = c1.iterator();
		while (iter.hasNext()) 
		{
		Object key = iter.next();
		Object value = localName.get(key.toString());
		MUA.globalName.put(key.toString(), value);
		}
		return 1;
	}
}
