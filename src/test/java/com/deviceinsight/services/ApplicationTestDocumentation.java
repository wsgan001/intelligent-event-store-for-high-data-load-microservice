/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deviceinsight.services;

import com.deviceinsight.services.request.GoodbyeRequest;
import com.deviceinsight.services.request.HelloRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// Sample test class for spring mvc app using Spring Rest Docs to generate API documentation.
// One important thing to note here is that the client is able to send different objects to the same endpoint (greeting)
/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
*/
public class ApplicationTestDocumentation {

/*    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private static ParameterDescriptor[] greetingPathParams() {
        return new ParameterDescriptor[]{
                parameterWithName("userId").description("this is the users name")
        };
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();


        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }

    //the hello request has a single field that is defined in the abstract class from which HelloRequest extends.
    @Test
    public void getGreeting() throws Exception {

        //RestDocumentationResultHandler document = documentPrettyPrintReqResp("getGreeting");
        document.snippets(
          //      pathParameters(greetingPathParams())
        //);

        HelloRequest req = new HelloRequest();
        req.setName("Will");

        ObjectMapper objectMapper = new ObjectMapper();


        this.document.snippets(
                responseFields(
                        fieldWithPath("id").description("The person's ID"),
                        fieldWithPath("firstName").description("The persons' first name"),
                        fieldWithPath("lastName").description("The persons' last name")
                )
        );


        mockMvc.perform(post("/api/greeting").content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("hello Will")))
                .andDo(document("hello",
                        requestFields(
                                fieldWithPath("type").description("haha").optional(),
                                fieldWithPath("name").description("haha").optional()),
                        responseFields(
                                //fieldWithPath("[]").description("An array of X"),
                                fieldWithPath("message").description("Ta message like hello Will")
                        )
                ));

    }

    //goodbye request has an additional field - 'fullname'. the 'name' field nfrom the abstract class can be
    // sent too however this is not used in the implementation.
    @Test
    public void getGoodbye() throws Exception {

        //RestDocumentationResultHandler document = documentPrettyPrintReqResp("getGoodbye");
        //document.snippets(
          //      pathParameters(greetingPathParams())
        //);

        GoodbyeRequest req = new GoodbyeRequest();
        req.setFullname("Will ");
        req.setName("Will Grigg");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/greeting").content(objectMapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Will Grigg")))
                .andDo(document("goodbye"));
    }

    private RestDocumentationResultHandler documentPrettyPrintReqResp(String useCase) {
        return document(useCase,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
    }
*/
}
