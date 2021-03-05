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

import org.apache.commons.lang3.StringUtils;
import org.jrtech.engines.fsm.AbstractAlternableEventsAction;
import org.jrtech.engines.fsm.CommonUtils;
import org.jrtech.engines.fsm.Events;
import org.jrtech.engines.fsm.ProcessContext;

public class Converter extends AbstractAlternableEventsAction {

    private static final long serialVersionUID = -669884911595690473L;

    public String inputContextKey = "converterInputContextKey";
    public String outputContextKey = "converterOutputContextKey";

    public Converter() {
        super(Events.DATA_CONVERTED, Arrays.asList(Events.DATA_CONVERSION_FAILURE));
    }

    @Override
    public String run(ProcessContext processContextData) {
        CommonUtils utils = new CommonUtils();
        System.out.println("Executing action: " + utils.getActionSignature(this));
        String inputValue = "" + (Object) processContextData.getContextData(inputContextKey);
        String conversionResult = "";
        String effectiveOutputEvent = getOutputEvent();

        if (inputValue.toLowerCase().contains("expect converter failure")) {
            utils.addErrorToErrorList(processContextData,
                    "Fail to convert input: '" + StringUtils.replace(inputValue, "\n", "&#13;") + "'");
            effectiveOutputEvent = getAlternateEvents().get(0);
        } else {
            conversionResult = "Converted -> " + inputValue;
        }

        processContextData.setContextData(outputContextKey, conversionResult);

        return effectiveOutputEvent;
    }

}