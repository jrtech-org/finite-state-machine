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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple finite state machine that executes actions in memory within the same
 * thread.
 * 
 * @author Jumin
 *
 */
public class FiniteStateMachine extends AbstractAlternableEventsAction {

    private static final long serialVersionUID = 7859885632583695876L;

    private static final String INITIAL_EVENT = "fsm.initialEvent";

    public static final String LAST_EXECUTING_ACTION_CONTEXT_KEY = "LastExecutingActionContextKey";
    public static final String LAST_SUCCESS_ACTION_CONTEXT_KEY = "LastSuccessActionContextKey";
    public static final String LAST_PRODUCED_EVENT_CONTEXT_KEY = "LastProducedEventContextKey";
    public static final String STATE_MACHINE_COMPLETION_LEVEL_CONTEXT_KEY = "StateMachineCompletionLevelContextKey";

    // State machine completed with the last event in the target events
    public static final int COMPLETION_LEVEL_FULL = 2;
    // State machine completed with the last event not in the target events
    public static final int COMPLETION_LEVEL_PARTIAL = 1;
    // State machine did not complete properly. Potential cause: uncaught
    // exception and etc.
    public static final int COMPLETION_LEVEL_NONE = 0;

    private final Map<String, Action> stateMachineConfig;

    private final String name;

    private final Set<String> targetEvents;

    private final CommonUtils utils = new CommonUtils();
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public FiniteStateMachine(String name, String outputEvent) {
        this(name, new HashSet<>(), outputEvent);
    }

    public FiniteStateMachine(String name, Set<String> targetEvents, String outputEvent) {
        this(name, targetEvents, outputEvent, new ArrayList<>());
    }

    public FiniteStateMachine(String name, Set<String> targetEvents, String outputEvent, List<String> alternateEvents) {
        super(outputEvent, alternateEvents);

        this.name = name;
        this.targetEvents = targetEvents;

        stateMachineConfig = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getTargetEvents() {
        return targetEvents;
    }

    public Action getInitialAction() {
        return stateMachineConfig.get(INITIAL_EVENT);
    }

    public FiniteStateMachine setInitialAction(Action action) {
        stateMachineConfig.put(INITIAL_EVENT, action);
        return this;
    }

    /**
     * Configure specific event chain from previous to next action. The produced
     * event from the previous action with specific event will lead to the
     * execution of the next action.
     * 
     * Registration of an event of a previous action has higher order than wild
     * card.
     * 
     * @param previousAction
     * @param fromEvent
     * @param nextAction
     * @return
     */
    public FiniteStateMachine configure(Action previousAction, String fromEvent, Action nextAction) {
        stateMachineConfig.put(utils.getActionSignature(previousAction) + "/" + fromEvent, nextAction);
        return this;
    }

    /**
     * Configure wild card chain from previous to next action. Any produced
     * event from previous action will lead to the execution of the next action.
     * 
     * Wild card has lower order than a specific event for the related previous
     * action.
     * 
     * @param previousAction
     * @param nextAction
     * @return
     */
    public FiniteStateMachine configure(Action previousAction, Action nextAction) {
        stateMachineConfig.put(utils.getActionSignature(previousAction) + "/*", nextAction);
        return this;
    }

    /**
     * Run the configured state machine definition with the provided context data.
     *
     * @param processContextData the context data for processing
     * @return the output state of the last executed action
     */
    public String run(ProcessContext processContextData) throws StateMachineRuntimeException {
        Action initialAction = getInitialAction();
        if (initialAction == null) {
            throw new MissingInitialActionException(name);
        }

        Action currentAction = initialAction;
        String currentActionSignature = utils.getActionSignature(currentAction);
        Action previousAction = null;
        String lastProducedEvent = null;
        int completionLevel = COMPLETION_LEVEL_NONE;
        boolean hasTargetEvents = targetEvents.size() > 0;
        boolean traceExecution = "true".equalsIgnoreCase(
                processContextData.getContextData(ContextKeyConstants.FSM_TRACE_EXECUTION_FLAG_CONTEXT_KEY));
        StringBuffer traceExecutionBuffer = new StringBuffer();

        try {
            // Initial Action Run
            if (traceExecution) {
                traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Execute initial action start: '")
                        .append(utils.getActionSignature(initialAction)).append("'\n");
            }
            lastProducedEvent = currentAction.run(processContextData);
            if (traceExecution) {
                traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Execute initial action end: '")
                        .append(utils.getActionSignature(initialAction)).append("'\n");
            }
            previousAction = currentAction;
            String eventKey = utils.getActionSignature(currentAction) + "/" + lastProducedEvent;

            // Next Action after Initial Action
            if (traceExecution) {
                traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Find next action on event key: '").append(eventKey).append("'\n");
            }
            currentAction = stateMachineConfig.get(eventKey);
            if (currentAction == null) {
                // Try to find with action signature and wild card
                if (traceExecution) {
                    traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Find next action on event key: '").append(currentActionSignature)
                            .append("/*'\n");
                }
                currentAction = stateMachineConfig.get(currentActionSignature + "/*");
            }
            while (currentAction != null) {
                if (traceExecution) {
                    traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Execute next action start: '")
                            .append(utils.getActionSignature(currentAction)).append("'\n");
                }
                lastProducedEvent = currentAction.run(processContextData);
                if (traceExecution) {
                    traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Execute next action end: '")
                            .append(utils.getActionSignature(currentAction)).append("'\n");
                }
                previousAction = currentAction;
                currentActionSignature = utils.getActionSignature(currentAction);
                eventKey = currentActionSignature + "/" + lastProducedEvent;

                if ((hasTargetEvents
                        && (targetEvents.contains(eventKey) || targetEvents.contains(currentActionSignature + "/*")))) {
                    if (traceExecution) {
                        traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Target state achieved!").append("\n");
                    }
                    completionLevel = COMPLETION_LEVEL_FULL;
                    break;
                }

                if (traceExecution) {
                    traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Find next action on event key: '").append(eventKey).append("'\n");
                }
                currentAction = stateMachineConfig.get(eventKey);
                if (currentAction == null) {
                    // Try to find with action signature and wild card
                    if (traceExecution) {
                        traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Find next action on event key: '").append(currentActionSignature)
                                .append("/*'\n");
                    }
                    currentAction = stateMachineConfig.get(currentActionSignature + "/*");
                }
            }

            if (completionLevel >= COMPLETION_LEVEL_FULL)
                return getOutputEvent();

            // If there is no target event defined -> Completion Level is
            // considered FULL.
            completionLevel = targetEvents == null || targetEvents.isEmpty() ? COMPLETION_LEVEL_FULL
                    : COMPLETION_LEVEL_PARTIAL;

            processContextData.removeContextData(LAST_EXECUTING_ACTION_CONTEXT_KEY);

            if (completionLevel >= COMPLETION_LEVEL_FULL) {
                // Considered Full Completion Level.
                if (traceExecution) {
                    traceExecutionBuffer.append(sdf.format(new Date(System.currentTimeMillis()))).append(" Target state is considered achieved!").append("\n");
                }
                return getOutputEvent();
            }

            return lastProducedEvent;
        } catch (Exception e) {
            processContextData.setContextData(LAST_EXECUTING_ACTION_CONTEXT_KEY,
                    utils.getActionSignature(currentAction));
            utils.addErrorToErrorList(processContextData, e.getMessage());

            throw new StateMachineRuntimeException(name, lastProducedEvent, currentAction, e);
        } finally {
            if (processContextData != null) {
                processContextData.setContextData(LAST_SUCCESS_ACTION_CONTEXT_KEY,
                        previousAction == null ? "" : utils.getActionSignature(previousAction));
                processContextData.setContextData(LAST_PRODUCED_EVENT_CONTEXT_KEY, lastProducedEvent);
                processContextData.setContextData(STATE_MACHINE_COMPLETION_LEVEL_CONTEXT_KEY, completionLevel);
                if (traceExecution)
                    processContextData.setContextData(ContextKeyConstants.FSM_TRACE_RESULT_CONTEXT_KEY,
                            traceExecutionBuffer.toString());
            }
        }
    }

    public static class MissingInitialActionException extends RuntimeException {

        private static final long serialVersionUID = -4946190807242689696L;

        private final String stateMachineName;

        public MissingInitialActionException(String stateMachineName) {
            super("Missing initial action in state machine: '" + stateMachineName + "'.");
            this.stateMachineName = stateMachineName;
        }

        public String getStateMachineName() {
            return stateMachineName;
        }
    }

    /**
     * A runtime exception thrown when processing the state machine.
     *
     */
    public static class StateMachineRuntimeException extends RuntimeException {

        private static final long serialVersionUID = -8018820641667906345L;

        private final String stateMachineName;

        private final String triggeringEvent;

        private final Action failureAction;

        public StateMachineRuntimeException(String stateMachineName, String lastSuccessState, Action failureAction) {
            super("Failure executing action: '" + failureAction.getName() + "' in state machine: '" + stateMachineName
                    + "' with last success state: '" + lastSuccessState + "'.");

            this.stateMachineName = stateMachineName;
            this.triggeringEvent = lastSuccessState;
            this.failureAction = failureAction;
        }

        public StateMachineRuntimeException(String stateMachineName, String triggeringEvent, Action failureAction,
                Throwable cause) {
            super("Failure executing action: '" + failureAction.getName() + "' in state machine: '" + stateMachineName
                    + "' with last success state: '" + triggeringEvent + "'.", cause);

            this.stateMachineName = stateMachineName;
            this.triggeringEvent = triggeringEvent;
            this.failureAction = failureAction;
        }

        public String getStateMachineName() {
            return stateMachineName;
        }

        public String getTriggeringEvent() {
            return triggeringEvent;
        }

        public Action getFailureAction() {
            return failureAction;
        }
    }

}
