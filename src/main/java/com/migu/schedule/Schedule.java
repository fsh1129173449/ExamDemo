package com.migu.schedule;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *类名和方法不能修改
 */
public class Schedule {

	// 服务器list
	private List<TaskInfo> nodeList = new ArrayList<TaskInfo>();
	// 服务器任务list
	private List<TaskInfo> nodeTaskList = new ArrayList<TaskInfo>();
	// 挂起任务
	private	Map<Object, Integer> taskMap = new HashMap<Object, Integer>();

	public int init() {

		// 清空所以的数据
		nodeList.clear();
		taskMap.clear();
		return ReturnCodeKeys.E001;
	}

	public int registerNode(int nodeId) {
		// 如果服务节点ID小于等于0 则返回服务节点编号非法
		if (nodeId <= 0) {
			return ReturnCodeKeys.E004;
		}
		for (TaskInfo taskInfo : nodeList) {
			// 如果有相同的服务节点ID，则返回服务节点已注册
			if (taskInfo.getNodeId() == nodeId) {
				return ReturnCodeKeys.E005;
			}
		}
		// 注册添加服务信息
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setNodeId(nodeId);
		//taskInfo.setTaskId(0);
		nodeList.add(taskInfo);
		return ReturnCodeKeys.E003;
	}

	public int unregisterNode(int nodeId) {

		// 如果服务节点ID小于等于0 则返回服务节点编号非法
		if (nodeId <= 0) {
			return ReturnCodeKeys.E004;
		}
		// 判断是否有相同的服务节点
		List<TaskInfo> nodeList3 = new ArrayList<TaskInfo>();
		//
		for (int j = 0; j < nodeList.size(); j++) {
			if (nodeList.get(j).getNodeId() == nodeId) {
				nodeList3.add(nodeList.get(j));
			}
		}
		// 如果没有则返回服务节点不存在
		if (nodeList3 == null & nodeList3.size() <= 0) {
			return ReturnCodeKeys.E007;
		} else {
			// 如果存在则删除
			nodeList.remove(nodeList3.get(0));
		}
		return ReturnCodeKeys.E006;
	}

	public int addTask(int taskId, int consumption) {
		// 如果任务节点ID小于等于0 则返回任务点编号非法
		if (taskId <= 0) {
			return ReturnCodeKeys.E009;
		}
		for (TaskInfo taskInfo : nodeList) {
			// 如果有相同的任务点ID，则返回任务节点已注册
			if (taskInfo.getTaskId() == taskId) {
				return ReturnCodeKeys.E010;
			}
			if(taskInfo.getTaskId()==0){
				taskInfo.setTaskId(taskId);
				// 注册添加任务信息
				taskMap.put(taskInfo, consumption);
			}
		}
		return ReturnCodeKeys.E008;
	}

	public int deleteTask(int taskId) {
		// 如果任务节点ID小于等于0 则返回任务点编号非法
		if (taskId <= 0) {
			return ReturnCodeKeys.E009;
		}
		// 判断是否有相同的任务
		List<TaskInfo> taskList3 = new ArrayList<TaskInfo>();
		//
		for (int j = 0; j < nodeList.size(); j++) {
			if (nodeList.get(j).getTaskId() == taskId) {
				taskList3.add(nodeList.get(j));
			}
		}
		// 如果没有则返回任务点不存在
		if (taskList3 == null & taskList3.size() <= 0) {
			return ReturnCodeKeys.E012;
		} else {
			// 如果存在则删除
			nodeList.remove(taskList3.get(0));
		}

		return ReturnCodeKeys.E011;
	}

	public int scheduleTask(int threshold) {
		// 如果调度阈值小于等于0 则返回调度阈值非法
		if (threshold <= 0) {
			return ReturnCodeKeys.E002;
		}
		
		return ReturnCodeKeys.E013;
	}

	public int queryTaskStatus(List<TaskInfo> tasks) {
		if (tasks == null & tasks.size() <= 0) {
			return ReturnCodeKeys.E016;
		}
		for (TaskInfo taskInfo : tasks) {
			// 如果挂起的任务删除
			if (taskInfo.getTaskId() ==-1) {
				tasks.remove(taskInfo);
			}
		}
		//任务编号升序
		 Collections.sort(tasks, new Comparator<TaskInfo>() {
	            @Override
	            public int compare(TaskInfo o1, TaskInfo o2) {
	                return o1.getTaskId()-o2.getTaskId();
	            }
	        });
		
		return ReturnCodeKeys.E015;
	}

}
