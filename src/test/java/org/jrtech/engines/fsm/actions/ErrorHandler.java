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
package org.jrtech.engines.fsm.actions;

import org.jrtech.engines.fsm.AbstractSimpleAction;
import org.jrtech.engines.fsm.CommonUtils;
import org.jrtech.engines.fsm.Events;
import org.jrtech.engines.fsm.ProcessContext;

public class ErrorHandler extends AbstractSimpleAction {

    private static final long serialVersionUID = -5116222967953037235L;

    public String validationResultContextKey = "validationResultContextKey";

    public ErrorHandler() {
        super(Events.ERROR_HANDLED);
    }
    
    @Override
    public String run(ProcessContext processContextData) {
        CommonUtils utils = new CommonUtils();
        System.out.println("Executing action: " + utils.getActionSignature(this));
        System.out.println("Show Error By ErrorHandler: " + processContextData.getContextData(validationResultContextKey));

        return getOutputEvent();
    }

}