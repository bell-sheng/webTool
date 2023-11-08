var XMLHttpRequest = require('xmlhttprequest').XMLHttpRequest;
const HOST_URL = "http://localhost:8090";
const APP_NAME = "mis";
const MODEL_NAME = "Test";

/**
 * 魔方网表中生成BPM任务后回调此方法向第三方系统创建待办
 * @param id 任务ID
 * @param name 任务名称
 * @param assignee 任务处理人账号
 * @param url 任务处理链接
 */
function createTask(id, name, assignee, url) {
    var targets = {'LoginName': '12fe2de141b7b97b32d1af34204a9f54'};
    var notifyTodoSendContext = new NotifyTodoSendContext(APP_NAME, MODEL_NAME, id, name, url, 1, targets);
    notifyTodoSendContext.docCreator = assignee;
    sendMessages("/v1/message", notifyTodoSendContext);
}

/**
 * 魔方网表中完成BPM任务后回调此方法向第三方修改待办状态
 * @param id 任务ID
 *
 */
function completeTask(id) {
    var notifyTodoRemoveContext =  new NotifyTodoRemoveContext(APP_NAME, MODEL_NAME, id, 1);
    sendMessages("/v1/message", notifyTodoRemoveContext);
}

function sendMessages(url, body) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", HOST_URL + url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(body));
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log(xhr.responseText);
        } else {
            console.log('Error: ' + xhr.status);
        }
    };
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

function NotifyTodoRemoveContext(appName, modelName, modelId, optType) {
    this.appName = appName;
    this.modelName = modelName;
    this.modelId = modelId;
    this.optType = optType;
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

createTask("1", "2", "3", "4");

completeTask("1");