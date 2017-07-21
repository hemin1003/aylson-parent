package com.aylson.dc.wx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		//结果集:会员-业主列表
		Map<String,List<String>> result = new HashMap<String,List<String>>(); 
		//同一个业主资料最多推送给设计师的数量
		Integer limit = 1;  
		//会员集合以及可以获取的业主数
		Map<String,Integer> memberMap = new HashMap<String,Integer>();
		memberMap.put("M1", 4);
		memberMap.put("M2", 3);
		memberMap.put("M3", 2);
		memberMap.put("M4", 1);
		memberMap.put("M5", 1);
		memberMap.put("M6", 1);
		memberMap.put("M7", 1);
		memberMap.put("M8", 1);
		memberMap.put("M9", 1);
		memberMap.put("M10", 1);
		//业主资料集合
		List<String> customList = new ArrayList<String>();
		customList.add("c1");
		customList.add("c2");
		customList.add("c3");
		customList.add("c4");
		customList.add("c5");
		customList.add("c6");
		customList.add("c7");
		customList.add("c8");
		customList.add("c9");
		customList.add("c10");
		customList.add("c11");
		customList.add("c12");
		customList.add("c13");
		customList.add("c14");
		customList.add("c15");
		customList.add("c16");
		customList.add("c17");
		customList.add("c18");
		customList.add("c19");
		customList.add("c20");
		Map<String,Integer> customMap = new HashMap<String,Integer>();//业主资料==>已经分配次数
		for (Map.Entry<String, Integer> entry : memberMap.entrySet()) {
			   //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			   Integer hadCustom = entry.getValue();           //会员分配的业主资料数
			   List<String> myCustom = new ArrayList<String>();//我的业主资料
			   for(int i=0; i<customList.size(); i++){
				  if(myCustom.size() < hadCustom){
					  if(customMap.containsKey(customList.get(i))){
						  if(customMap.get(customList.get(i)) == limit)continue;
						  customMap.put(customList.get(i),customMap.get(customList.get(i))+1);
					  }else{
						  customMap.put(customList.get(i), 1);
					  }
					  myCustom.add(customList.get(i));
				  }
			   }
			   result.put(entry.getKey(), myCustom);
//			   for(String temp:myCustom){
//				   System.out.println(entry.getKey()+"==>"+temp);
//			   }
		}
		for (Map.Entry<String, List<String>> entry : result.entrySet()) {
//			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			List<String> list = entry.getValue();
			System.out.print(entry.getKey()+":");
			for(String temp:list){
				System.out.print(temp+",");
			}
			System.out.println(";");
		}
	}
}
