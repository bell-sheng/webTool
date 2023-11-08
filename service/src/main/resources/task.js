var HashMap = Java.type('java.util.HashMap');
var Map = Java.type('java.util.Map');
var System = Java.type('java.lang.System');

/**
 * 魔方网表中生成BPM任务后回调此方法向第三方系统创建待办
 * @param id 任务ID
 * @param name 任务名称
 * @param assignee 任务处理人账号
 * @param url 任务处理链接
 */
function createTask(id, name, assignee, url) {
//TODO:调用第三方待办接口创建待办
    var targets = {'Id': '12fe2de141b7b97b32d1af34204a9f54'};
    var notifyTodoSendContext = new NotifyTodoSendContext("1", "流程管理", id, name, url, 2, targets);
    notifyTodoSendContext.docCreator = assignee;
    var body = JSON.stringify(notifyTodoSendContext);
    print(body);
}

/**
 * 魔方网表中完成BPM任务后回调此方法向第三方修改待办状态
 * @param id 任务ID
 *
 */
function completeTask(id) {
//TODO:调用第三方待办处理接口改变待办为已办

}

function NotifyTodoSendContext(appName, modelName, modelId, subject, link, type, targets) {
    this.createTime = formatDate(new Date());
    this.appName = appName;
    this.modelName = modelName;
    this.modelId = modelId;
    this.subject = subject;
    this.link = link;
    this.type = type;
    this.targets = targets;
}

function formatDate(date) {
    var year = date.getUTCFullYear();
    var month = ("0" + (date.getUTCMonth() + 1)).slice(-2);
    var day = ("0" + date.getUTCDate()).slice(-2);
    var hours = ("0" + date.getUTCHours()).slice(-2);
    var minutes = ("0" + date.getUTCMinutes()).slice(-2);
    var seconds = ("0" + date.getUTCSeconds()).slice(-2);
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}