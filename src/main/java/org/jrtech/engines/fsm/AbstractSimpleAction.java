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

import java.util.List;

/**
 * An abstract simple action that includes several necessary initialization.
 * 
 * @author Jumin
 *
 */
public abstract class AbstractSimpleAction implements Action {
    
    private static final long serialVersionUID = -6219828463842018809L;
    
    private String name = getClass().getSimpleName();
    
    private final String outputEvent;
    
    public String errorListContextKey = ContextKeyConstants.ERROR_LIST_CONTEXT_KEY;

    public AbstractSimpleAction(String outputEvent) {
        this.outputEvent = outputEvent;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOutputEvent() {
        return outputEvent;
    }
    
    public List<String> getErrorListFromContext(ProcessContext processContextData) {
        return processContextData.getContextData(errorListContextKey);
    }
    
    @Override
    public String toString() {
        return name + " (" + getClass().getName() + ")";
    }
}
