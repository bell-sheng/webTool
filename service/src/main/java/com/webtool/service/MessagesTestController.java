package com.webtool.service;

import com.webtool.api.MessageTestServiceApi;
import com.webtool.model.NotifyTodoAppResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessagesTestController implements MessageTestServiceApi {
    @Override
    public ResponseEntity<NotifyTodoAppResult> revMessage(String body) {
        log.info("start to add student:{}", body);
        NotifyTodoAppResult notifyTodoAppResult = new NotifyTodoAppResult();
        notifyTodoAppResult.setReturnState(0);
        notifyTodoAppResult.setMessage("");
        return ResponseEntity.ok(notifyTodoAppResult);
    }
}
