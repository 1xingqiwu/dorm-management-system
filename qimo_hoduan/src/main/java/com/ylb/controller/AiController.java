package com.ylb.controller;

import com.ylb.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "AI助手")
@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    // 返回 SseEmitter，且 produce 指定为 text/event-stream
    @Operation(summary = "流式智能对话")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@RequestParam String question) {
        return aiService.streamChat(question);
    }
}