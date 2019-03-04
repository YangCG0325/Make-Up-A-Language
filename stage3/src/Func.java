import java.util.*;


public class Func
{
	public static ArrayList<HashMap<String, Object>> localName = new ArrayList<HashMap<String, Object>>();

	public static int func()
	{
		int i;
		int paraNeed = MUA.isFunc(MUA.currentState);
		String funcList = MUA.globalName.get(MUA.currentState).toString();
		
		if(paraNeed == 0)
		{
			Func.localName.add(new HashMap<String, Object>());
			MUA.isDoFunc.set(MUA.isDoFunc.size() - 1, 1);
			MUA.explain(funcList.substring(funcList.indexOf(']') + 4, funcList.length() - 4), 0);			
			return 1;
		}
		
		for(i = 0; i < paraNeed; i++)
		{
			if(MUA.exeParam.get(MUA.paraIndex - MUA.paraNumber + i).equals(""))
				return 0;
		}
		
		String params = funcList.substring(4, funcList.indexOf("]") - 1);
		String para[] = params.split(" ");
		
		Func.localName.add(new HashMap<String, Object>());
		for(i = 0; i < paraNeed; i++)
			Exe.do_make(0, para[i], MUA.exeParam.get(MUA.paraIndex - MUA.paraNumber + i));
		
		MUA.isDoFunc.set(MUA.isDoFunc.size() - 1, 1);
		MUA.explain(funcList.substring(funcList.indexOf(']') + 4, funcList.length() - 4), 0);
		return 1;
	}
/*------------------------------------------------------------------------------------------------*/
	public static int do_output(String value)
	{
		if(value.equals(""))
			return 0;
		MUA.exeParam.set(MUA.funcReturnIndex.get(MUA.funcReturnIndex.size() - 1), value);
		return 1;
	}
/*-------------------------------------------------------------------------------------------------*/	
	public static int do_export(String name)
	{
		if(name.equals(""))
			return 0;
		if(!localName.get(localName.size() - 1).containsKey(name))
		{
			System.out.println("==>name " + name + " not find in local space");
			return -1;
		}
		MUA.globalName.put(name, localName.get(localName.size() - 1).get(name));
		return 1;
	}
}
