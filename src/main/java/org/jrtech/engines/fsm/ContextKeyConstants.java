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

/**
 * This class defines the typical/common context keys used in a finite state machine.
 * 
 * @author Jumin
 *
 */
public class ContextKeyConstants {
    
    /**
     * The context key to the error list.
     */
    public static final String ERROR_LIST_CONTEXT_KEY = "ErrorListContextKey";
    
    /**
     * The context key to debug flag for the FSM.
     */
    public static final String FSM_DEBUG_FLAG_CONTEXT_KEY = "Fsm.DebugFlagContextKey";
    
    /**
     * The context key to trace flag for the FSM.  
     */
    public static final String FSM_TRACE_EXECUTION_FLAG_CONTEXT_KEY = "Fsm.TraceExecutionFlagContextKey";
    
    /**
     * The context key to the trace result of the FSM.  
     */
    public static final String FSM_TRACE_RESULT_CONTEXT_KEY = "Fsm.TraceResultContextKey";

    /**
     * The context key to the value of the id of last executed action.  
     */
    public static final String FSM_LAST_EXECUTED_ACTION_ID_CONTEXT_KEY = "Fsm.LastExecutedAction.IdContextKey";
    
    /**
     * The context key to the value of the output event of last executed action.  
     */
    public static final String FSM_LAST_EXECUTED_ACTION_OUTPUT_EVENT_CONTEXT_KEY = "Fsm.LastExecutedAction.OutputEventContextKey";
    
}
