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

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the common functions 
 * 
 * @author Jumin
 *
 */
public class CommonUtils {
    
    /**
     * Show the error list from process context as line separated string.
     * 
     * @param processContextData
     * @return errors presented as string.
     */
    public final String showErrorListToString(ProcessContext processContextData) {
        List<String> errorList = processContextData.getContextData(ContextKeyConstants.ERROR_LIST_CONTEXT_KEY);
        if (errorList == null) {
            return "";
        }
        
        return String.join("\n", errorList);
    }

    /**
     * Add an error message (string) into the error list in the process context.
     * 
     * @param processContextData
     * @param errorMessage
     * @return process context
     */
    public final ProcessContext addErrorToErrorList(ProcessContext processContextData, String errorMessage) {
        List<String> errorList = processContextData.getContextData(ContextKeyConstants.ERROR_LIST_CONTEXT_KEY);
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        errorList.add(errorMessage);
        processContextData.setContextData(ContextKeyConstants.ERROR_LIST_CONTEXT_KEY, errorList);
        
        return processContextData;
    }
    
    /**
     * Produce/generate a signature for an action.
     * 
     * @param action
     * @return signature of an action
     */
    public final String getActionSignature(Action action) {
        return action.getName() + " (" + action.getClass().getName() + ")";
    }
}
