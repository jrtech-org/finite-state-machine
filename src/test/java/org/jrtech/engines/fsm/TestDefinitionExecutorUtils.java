/*
 * Copyright (c) 2016-2026 Jumin Rubin
 * LinkedIn: https://www.linkedin.com/in/juminrubin/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jrtech.engines.fsm;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.jrtech.engines.fsm.FiniteStateMachine.StateMachineRuntimeException;
import org.jrtech.engines.fsm.loader.ObjectDefinitionLoader;
import org.jrtech.engines.fsm.loader.ObjectLoadingException;
import org.jrtech.engines.fsm.model.AbstractActionDefinition;
import org.jrtech.engines.fsm.model.FiniteStateMachineDefinition;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Map;

public class TestDefinitionExecutorUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(TestDefinitionExecutorUtils.class);

    private ObjectDefinitionLoader defLoader = new ObjectDefinitionLoader();

    @Test
    public void createFiniteStateMachine() {
        Map<String, AbstractActionDefinition> actionDefinitionCatalog = null;
           InputStream jsonStream = getClass().getResourceAsStream("/test_data/basic/action-def-catalog.json");
        try {
            actionDefinitionCatalog = defLoader.loadActionCatalog(jsonStream);
        } catch (ObjectLoadingException e) {
            LOGGER.error("Failure in loading action definition catalog.", e);
            Assert.fail(e.getMessage());
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                    LOGGER.error("Failure in closing file of action definition catalog.", e);
                }
        }
        
        FiniteStateMachineDefinition fsmDefinition = null;
        jsonStream = getClass().getResourceAsStream("/test_data/basic/fsm-def-1.json");
        try {
            fsmDefinition = defLoader.loadFiniteStateMachineDefinition(jsonStream);
        } catch (ObjectLoadingException e) {
            LOGGER.error("Failure in loading FSM definition.", e);
            Assert.fail(e.getMessage());
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                    LOGGER.error("Failure in closing file of FSM definition.", e);
                }
        }   
                
        FiniteStateMachine fsm = DefinitionExecutorUtils.createFiniteStateMachine(actionDefinitionCatalog, fsmDefinition);

        String[] inputs = new String[] {
                // @formatter:off
                "A correct input data with no error inside 1.",
                "A correct input data with no error inside 2.",
                "An input data with fatal error 1 inside 1.",
                "An input data with fatal error 2 inside 1.",
                "An input data with harmless error 1 inside 1.",
                "An input data with harmless error 2 inside 1.",
                // @formatter:on
            };

        String inputContextKey = "inputContextKey";
        
        StringBuffer sb = new StringBuffer();
        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            ProcessContext pcData = new SimpleProcessContext();
            pcData.setContextData(inputContextKey, input);
            //pcData.setContextData(ContextKeyConstants.FSM_TRACE_EXECUTION_FLAG_CONTEXT_KEY, "true");
            try {
                sb.append("### Index: " + i).append("\n");
                String fsmOutputEvent = fsm.run(pcData);
                sb.append(input).append("\n");
                sb.append("  -> state machine output event: ").append(fsmOutputEvent).append("\n");
                sb.append("  -> conversion result: " + pcData.getContextData("converterOutputContextKey")).append("\n");
                sb.append("  -> transformation result: " + pcData.getContextData("transformerOutputContextKey")).append("\n");
                sb.append("  -> execution trace:").append("\n");
                String executionTraces = pcData.getContextData(ContextKeyConstants.FSM_TRACE_RESULT_CONTEXT_KEY); 
                sb.append("     ").append(StringUtils.replace(executionTraces, "\n", "\n     ")).append("\n");
                sb.append("\n");
            } catch (StateMachineRuntimeException e) {
                Assert.fail(e.getMessage());
            }
        }
        sw.stop();
        
        System.out.println(sb.toString());
        System.out.println("### Summary ###");
        System.out.println("Elapsed time: " + NumberFormat.getIntegerInstance().format(sw.getNanoTime()) + " ns.");
    }
}
