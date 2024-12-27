package com.genaipeople.openai.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genaipeople.openai.Role;
import com.genaipeople.openai.message.Message;
import com.genaipeople.openai.text.ChatResponse;

public class LogProbsTest {

    public void testLogProbsDeserialization() throws Exception {
        String json = "{\"content\":[{\"token\":\"Hello\",\"logprob\":-0.07907805,\"bytes\":[72,101,108,108,111]}],\"top_logprobs\":[]}";
        ObjectMapper mapper = new ObjectMapper();
        LogProbs logProbs = mapper.readValue(json, LogProbs.class);
        assertEquals(1, logProbs.getContent().size());
        assertEquals("Hello", logProbs.getContent().get(0).getToken());
        assertEquals(-0.07907805, logProbs.getContent().get(0).getLogprob());
    }
    
    @Test
    public void testLogProbsSerialization() throws Exception {
        // Create content tokens
        List<ContentToken> tokens = Arrays.asList(
            new ContentToken("Hello", -0.07907805, Arrays.asList(72, 101, 108, 108, 111)),
            new ContentToken("!", -6.704273e-7, Arrays.asList(33)),
            new ContentToken(" How", -2.9352968e-6, Arrays.asList(32, 72, 111, 119)),
            new ContentToken(" can", -3.7697225e-6, Arrays.asList(32, 99, 97, 110)),
            new ContentToken(" I", 0.0, Arrays.asList(32, 73)),
            new ContentToken(" assist", -0.0036000712, Arrays.asList(32, 97, 115, 115, 105, 115, 116)),
            new ContentToken(" you", 0.0, Arrays.asList(32, 121, 111, 117)),
            new ContentToken(" today", 0.0, Arrays.asList(32, 116, 111, 100, 97, 121)),
            new ContentToken("?", -2.3392786e-6, Arrays.asList(63))
        );

        // Create LogProbs object
        LogProbs logProbs = new LogProbs();
        logProbs.setContent(tokens);

        // Serialize to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(logProbs);
        System.out.println(json);
        // Verify JSON structure
        assertTrue(json.contains("\"content\":["));
        assertTrue(json.contains("\"token\":\"Hello\""));
        assertTrue(json.contains("\"logprob\":-0.07907805"));
        assertTrue(json.contains("\"bytes\":[72,101,108,108,111]"));
        assertTrue(json.contains("\"top_logprobs\":[]"));

        // Deserialize and verify
        LogProbs deserializedLogProbs = mapper.readValue(json, LogProbs.class);
        assertEquals(9, deserializedLogProbs.getContent().size());
        assertEquals("Hello", deserializedLogProbs.getContent().get(0).getToken());
        assertEquals(-0.07907805, deserializedLogProbs.getContent().get(0).getLogprob());
    }

    @Test
    public void testChoiceSerialization() throws Exception {
        Choice choice = new Choice();
        List<ContentToken> tokens = Arrays.asList(
            new ContentToken("Hello", -0.07907805, Arrays.asList(72, 101, 108, 108, 111)),
            new ContentToken("!", -6.704273e-7, Arrays.asList(33)),
            new ContentToken(" How", -2.9352968e-6, Arrays.asList(32, 72, 111, 119)),
            new ContentToken(" can", -3.7697225e-6, Arrays.asList(32, 99, 97, 110)),
            new ContentToken(" I", 0.0, Arrays.asList(32, 73)),
            new ContentToken(" assist", -0.0036000712, Arrays.asList(32, 97, 115, 115, 105, 115, 116)),
            new ContentToken(" you", 0.0, Arrays.asList(32, 121, 111, 117)),
            new ContentToken(" today", 0.0, Arrays.asList(32, 116, 111, 100, 97, 121)),
            new ContentToken("?", -2.3392786e-6, Arrays.asList(63))
        );

        // Create LogProbs object
        LogProbs logProbs = new LogProbs();
        logProbs.setContent(tokens);
        choice.setLogprobs(logProbs);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(choice);
        System.out.println(json);
        assertTrue(json.contains("\"logprobs\":{\"content\":["));
        assertTrue(json.contains("\"token\":\"Hello\""));
        assertTrue(json.contains("\"logprob\":-0.07907805"));
        assertTrue(json.contains("\"bytes\":[72,101,108,108,111]"));
        assertTrue(json.contains("\"top_logprobs\":[]"));
    }

    @Test
    public void testChatResponseSerialization() throws Exception {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId("chatcmpl-123");
        chatResponse.setObject("chat.completion");
        chatResponse.setCreated(1714852200L);
        chatResponse.setModel("gpt-4o");
        chatResponse.setSystemFingerprint("sf-123");
        Choice choice = new Choice();
        List<ContentToken> tokens = Arrays.asList(
            new ContentToken("Hello", -0.07907805, Arrays.asList(72, 101, 108, 108, 111)),
            new ContentToken("!", -6.704273e-7, Arrays.asList(33)),
            new ContentToken(" How", -2.9352968e-6, Arrays.asList(32, 72, 111, 119)),
            new ContentToken(" can", -3.7697225e-6, Arrays.asList(32, 99, 97, 110)),
            new ContentToken(" I", 0.0, Arrays.asList(32, 73)),
            new ContentToken(" assist", -0.0036000712, Arrays.asList(32, 97, 115, 115, 105, 115, 116)),
            new ContentToken(" you", 0.0, Arrays.asList(32, 121, 111, 117)),
            new ContentToken(" today", 0.0, Arrays.asList(32, 116, 111, 100, 97, 121)),
            new ContentToken("?", -2.3392786e-6, Arrays.asList(63))
        );
        Message message = new Message("Hello", Role.USER);
        choice.setMessage(message);
        choice.setIndex(0);
        choice.setFinishReason("stop");

        Usage usage = new Usage();
        usage.setPromptTokens(10);
        usage.setCompletionTokens(10);
        usage.setTotalTokens(20);
        chatResponse.setUsage(usage);

        // Create LogProbs object
        LogProbs logProbs = new LogProbs();
        logProbs.setContent(tokens);
        choice.setLogprobs(logProbs);
        chatResponse.setChoices(Arrays.asList(choice));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(chatResponse);
        System.out.println(json);
        assertTrue(json.contains("\"Hello\""));
        assertTrue(json.contains("\"logprob\":-0.07907805"));
        assertTrue(json.contains("\"bytes\":[72,101,108,108,111]"));
        assertTrue(json.contains("\"top_logprobs\""));
        assertTrue(json.contains("\"prompt_tokens\":10"));
        assertTrue(json.contains("\"completion_tokens\":10"));
        assertTrue(json.contains("\"total_tokens\":20"));
    }

    @Test
    public void testChatCompletionDeserialization() throws Exception {
        String json = "{"
            + "\"id\": \"chatcmpl-AimYvdHhMFlV0TOZ5KgJ1d2arPpd7\","
            + "\"object\": \"chat.completion\","
            + "\"created\": 1735236469,"
            + "\"model\": \"gpt-4o-2024-08-06\","
            + "\"system_fingerprint\": \"fp_f785eb5f47\","
            + "\"choices\": [{"
            + "  \"index\": 0,"
            + "  \"message\": {"
            + "    \"role\": \"assistant\","
            + "    \"content\": \"Hello! How can I assist you today?\","
            + "    \"refusal\": null"
            + "  },"
            + "  \"logprobs\": {"
            + "    \"content\": ["
            + "      {\"token\": \"Hello\", \"logprob\": -0.011093091, \"bytes\": [72,101,108,108,111], \"top_logprobs\": []},"
            + "      {\"token\": \"!\", \"logprob\": -5.5122365e-7, \"bytes\": [33], \"top_logprobs\": []},"
            + "      {\"token\": \" How\", \"logprob\": -1.504853e-6, \"bytes\": [32,72,111,119], \"top_logprobs\": []},"
            + "      {\"token\": \" can\", \"logprob\": -1.8624639e-6, \"bytes\": [32,99,97,110], \"top_logprobs\": []},"
            + "      {\"token\": \" I\", \"logprob\": 0.0, \"bytes\": [32,73], \"top_logprobs\": []},"
            + "      {\"token\": \" assist\", \"logprob\": -0.0036000712, \"bytes\": [32,97,115,115,105,115,116], \"top_logprobs\": []},"
            + "      {\"token\": \" you\", \"logprob\": 0.0, \"bytes\": [32,121,111,117], \"top_logprobs\": []},"
            + "      {\"token\": \" today\", \"logprob\": 0.0, \"bytes\": [32,116,111,100,97,121], \"top_logprobs\": []},"
            + "      {\"token\": \"?\", \"logprob\": -1.147242e-6, \"bytes\": [63], \"top_logprobs\": []}"
            + "    ],"
            + "    \"refusal\": null"
            + "  },"
            + "  \"finish_reason\": \"stop\""
            + "}],"
            + "\"usage\": {"
            + "  \"prompt_tokens\": 8,"
            + "  \"completion_tokens\": 10,"
            + "  \"total_tokens\": 18,"
            + "  \"prompt_tokens_details\": {\"cached_tokens\": 0, \"audio_tokens\": 0},"
            + "  \"completion_tokens_details\": {"
            + "    \"reasoning_tokens\": 0,"
            + "    \"audio_tokens\": 0,"
            + "    \"accepted_prediction_tokens\": 0,"
            + "    \"rejected_prediction_tokens\": 0"
            + "  }"
            + "}}";

        ObjectMapper mapper = new ObjectMapper();
        ChatResponse response = mapper.readValue(json, ChatResponse.class);

        assertEquals("chatcmpl-AimYvdHhMFlV0TOZ5KgJ1d2arPpd7", response.getId());
        assertEquals("chat.completion", response.getObject());
        assertEquals(1735236469L, response.getCreated());
        assertEquals("gpt-4o-2024-08-06", response.getModel());
        
        Choice choice = response.getChoices().get(0);
        assertEquals(0, choice.getIndex());
        assertEquals("assistant", choice.getMessage().getRole().toString());
        assertEquals("Hello! How can I assist you today?", choice.getMessage().getContent().getContent());
        assertEquals("Hello", choice.getLogprobs().getContent().get(0).getToken());
    }
}
