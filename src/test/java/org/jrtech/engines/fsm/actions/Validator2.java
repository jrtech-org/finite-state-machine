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
package org.jrtech.engines.fsm.actions;

import java.util.Arrays;

import org.jrtech.engines.fsm.AbstractAlternableEventsAction;
import org.jrtech.engines.fsm.CommonUtils;
import org.jrtech.engines.fsm.Events;
import org.jrtech.engines.fsm.ProcessContext;

public class Validator2 extends AbstractAlternableEventsAction {

    private static final long serialVersionUID = 7318967581601948676L;

    public String inputContextKey = "inputContextKey";
    public String validationResultContextKey = "validationResultContextKey";

    public Validator2() {
        super(Events.VALIDATION_VALID, Arrays.asList(Events.VALIDATION_NOTVALID_1, Events.VALIDATION_NOTVALID_2));
    }

    @Override
    public String run(ProcessContext processContextData) {
        CommonUtils utils = new CommonUtils();
        System.out.println("Executing action: " + utils.getActionSignature(this));
        String inputValueToCheck = processContextData.getContextData(inputContextKey);
        String effectiveOutputEvent = getOutputEvent();

        if (inputValueToCheck.toLowerCase().contains("harmless error 1")) {
            processContextData.setContextData(validationResultContextKey, "Validation 2 Warning 1");
            effectiveOutputEvent = getAlternateEvents().get(0);
        } else if (inputValueToCheck.toLowerCase().contains("harmless error 2 ")) {
            processContextData.setContextData(validationResultContextKey, "Validation 2 Warning 2");
            effectiveOutputEvent = getAlternateEvents().get(1);
        } else {
            processContextData.setContextData(validationResultContextKey, "");
        }

        return effectiveOutputEvent;
    }

}