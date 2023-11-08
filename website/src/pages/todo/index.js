import React, {Component, memo} from 'react';

// var Map = Java.type('java.util.Map');
// var Gson = Java.type('com.google.gson.Gson');
// var System = Java.type('java.lang.System');

/**
 * 魔方网表中生成BPM任务后回调此方法向第三方系统创建待办
 * @param id 任务ID
 * @param name 任务名称
 * @param assignee 任务处理人账号
 * @param url 任务处理链接
 */
function createTask(id, name, assignee, url) {
//TODO:调用第三方待办接口创建待办
}

/**
 * 魔方网表中完成BPM任务后回调此方法向第三方修改待办状态
 * @param id 任务ID
 *
 */
function completeTask(id) {
//TODO:调用第三方待办处理接口改变待办为已办
}

class TaskToDo extends Component{
    render() {
        return (
            <div>
                TaskTo
            </div>
        );
    }
}
export default memo(TaskToDo);