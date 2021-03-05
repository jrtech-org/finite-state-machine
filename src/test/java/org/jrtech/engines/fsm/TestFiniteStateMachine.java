/*
 * Copyright (c) 2016-2018 Jumin Rubin
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

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.time.StopWatch;
import org.jrtech.engines.fsm.FiniteStateMachine.StateMachineRuntimeException;
import org.jrtech.engines.fsm.actions.Converter;
import org.jrtech.engines.fsm.actions.ErrorHandler;
import org.jrtech.engines.fsm.actions.Transformer;
import org.jrtech.engines.fsm.actions.Validator1;
import org.jrtech.engines.fsm.actions.Validator2;
import org.junit.Assert;
import org.junit.Test;

public class TestFiniteStateMachine {

    @Test
    public void testSimpleStateMachine() {
        String fsmSuccessOutputEvent = "Completed";
        
        FiniteStateMachine fsm = new FiniteStateMachine("Simple-FSM1",
                new HashSet<String>(Arrays.asList(Events.DATA_TRANSFORMED)), fsmSuccessOutputEvent);

        String inputContextKey = "inputContextKey";

        Validator1 validator1 = new Validator1();
        validator1.inputContextKey = inputContextKey;
        
        Validator2 validator2 = new Validator2();
        validator2.inputContextKey = inputContextKey;
        
        Converter converter1 = new Converter();
        converter1.setName("converter1");
        converter1.inputContextKey = inputContextKey;
        
        Converter converter2 = new Converter();
        converter2.setName("converter2");
        converter2.inputContextKey = inputContextKey;
        
        Transformer transformer = new Transformer();
        transformer.inputContextKey = converter1.outputContextKey;
        
        ErrorHandler fatalErrorHandler = new ErrorHandler();
        fatalErrorHandler.setName("fatalErrorHandler");
        
        ErrorHandler nonFatalErrorHandler = new ErrorHandler();
        nonFatalErrorHandler.setName("nonFatalErrorHandler");

        ErrorHandler nonFatalErrorHandlerFromConversion = new ErrorHandler();
        nonFatalErrorHandler.setName("nonFatalErrorHandlerFromConversion");

        fsm.setInitialAction(validator1).configure(validator1, Events.VALIDATION_VALID, validator2)
        // @formatter:off
                .configure(validator1, fatalErrorHandler)
                // pass both validation.
                .configure(validator2, Events.VALIDATION_VALID, converter1) 
                .configure(converter1, Events.DATA_CONVERTED, transformer)
                // error handling for data conversion
                .configure(converter1, Events.DATA_CONVERSION_FAILURE, nonFatalErrorHandlerFromConversion)
                // does not pass 2nd validation
                .configure(validator2, nonFatalErrorHandler) 
                .configure(nonFatalErrorHandler, Events.ERROR_HANDLED, converter2);
        // @formatter:on

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
        
        String[] expectedOutputs = new String[] {
                // @formatter:off
                    
                // @formatter:on
            };
        String[] expectedFsmOutputEvents = new String[] {
                // @formatter:off
                    
                // @formatter:on
            };

        StringBuffer sb = new StringBuffer();
        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            ProcessContext pcData = new SimpleProcessContext();
            pcData.setContextData(inputContextKey, input);
            try {
                sb.append("### Index: " + i).append("\n");
                String fsmOutputEvent = fsm.run(pcData);
                sb.append(input).append("\n");
                sb.append("  -> state machine output event: ").append(fsmOutputEvent).append("\n");
                sb.append("  -> conversion result: " + pcData.getContextData(converter1.outputContextKey)).append("\n");
                sb.append("  -> transformation result: " + pcData.getContextData(transformer.outputContextKey)).append("\n");
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
