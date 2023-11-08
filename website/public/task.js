const XMLHttpRequest = require('xmlhttprequest').XMLHttpRequest;
const HOST_URL = "http://localhost:8090";
const APP_NAME = "mis";
const MODEL_NAME_MAP = new Map([[1, 'Test'], [2, 'Test1']]);

/**
 * 魔方网表中生成BPM任务后回调此方法向第三方系统创建待办
 * @param id 任务ID
 * @param name 任务名称
 * @param assignee 任务处理人账号
 * @param url 任务处理链接
 */
function createTask(id, name, assignee, url) {
    const targets = {'LoginName': assignee};
    const type = 1;
    const notifyTodoSendContext = new NotifyTodoSendContext(APP_NAME, MODEL_NAME_MAP.get(type), id, name, url, type, targets);
    notifyTodoSendContext.docCreator = assignee;
    notifyTodoSendContext.others = {'mobileLink': url};
    sendMessages("/v1/message", notifyTodoSendContext);
}

/**
 * 魔方网表中完成BPM任务后回调此方法向第三方修改待办状态
 * @param id 任务ID
 *
 */
function completeTask(id) {
    const type = 1;
    const notifyTodoRemoveContext = new NotifyTodoRemoveContext(APP_NAME, MODEL_NAME_MAP.get(type), id, type);
    notifyTodoRemoveContext.targets = {'LoginName': 'User'};
    sendMessages("/v1/message", notifyTodoRemoveContext);
}

function sendMessages(url, body) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", HOST_URL + url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(body));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            console.log('Error: ' + xhr.status);
            return;
        }
        console.log(xhr.responseText);
        let responseJson = JSON.parse(xhr.responseText);
        if (responseJson.returnState === 0) {
            console.log('Not operated.');
        } else if (responseJson.returnState === 1) {
            console.log('Operation failed:' + responseJson.message);
        } else {
            console.log('Operation successful.');
        }
    };
    xhr.onerror = function (){
        console.log('Network Error.');
    }
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
    const year = date.getUTCFullYear();
    const month = ("0" + (date.getUTCMonth() + 1)).slice(-2);
    const day = ("0" + date.getUTCDate()).slice(-2);
    const hours = ("0" + date.getUTCHours()).slice(-2);
    const minutes = ("0" + date.getUTCMinutes()).slice(-2);
    const seconds = ("0" + date.getUTCSeconds()).slice(-2);
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

createTask("1", "Title", "User", "http://schemas.xmlsoap.org/soap/envelope/");

completeTask("1");