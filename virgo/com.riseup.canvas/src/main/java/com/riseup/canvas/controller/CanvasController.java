package com.riseup.canvas.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riseup.canvas.vo.Edge;
import com.riseup.canvas.vo.Node;

@Controller
public class CanvasController {

	@RequestMapping("/saveNetWork")
	@ResponseBody
	public String saveNetWork(String netWorkJsonStr) throws IOException {

		JSONObject obj = new JSONObject();
		obj.put("1", "edges1");
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("node", Node.class);
		classMap.put("edge", Edge.class);
		JSONObject netWorkJson = JSONObject.fromObject(netWorkJsonStr);
		JSONObject _nodes=netWorkJson.getJSONObject("nodes");
		JSONObject _edges=netWorkJson.getJSONObject("edges");
		JSONObject nodes_json=_nodes.getJSONObject("_data");
		JSONObject edges_json=_edges.getJSONObject("_data");
		@SuppressWarnings("unchecked")
		Iterator<String> node_keys=nodes_json.keys();
		JSONArray node_jsonArray=new JSONArray();
		while(node_keys.hasNext()){
			String key=node_keys.next();
			node_jsonArray.add(nodes_json.get(key));
		}
		
		@SuppressWarnings("unchecked")
		Iterator<String> edges_keys=edges_json.keys();
		JSONArray edge_jsonArray=new JSONArray();
		while(edges_keys.hasNext()){
			String key=edges_keys.next();
			edge_jsonArray.add(edges_json.get(key));
		}
		JSONObject datas_json=new JSONObject();
		datas_json.put("nodes", node_jsonArray);
		datas_json.put("edges", edge_jsonArray);
		
		FileWriter writer = new FileWriter("d:/1.txt", false);
		writer.write(datas_json.toString());
		writer.close();

		// NetWork netWork=(NetWork)
		// JSONObject.toBean(netWorkJson,NetWork.class,classMap);
		return "success";

	}

	@RequestMapping("/getNetWork")
	@ResponseBody
	public String getNetWork(String netWorkname) throws IOException {
		  StringBuffer sb= new StringBuffer("");
		FileReader reader = new FileReader("d://1.txt");

		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(reader);

		String str = null;

		while ((str = br.readLine()) != null) {
			sb.append(str);

		}
		return sb.toString();

	}
}
