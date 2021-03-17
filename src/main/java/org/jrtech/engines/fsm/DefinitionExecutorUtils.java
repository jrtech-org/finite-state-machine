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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.jrtech.engines.fsm.model.AbstractActionDefinition;
import org.jrtech.engines.fsm.model.ActionInstanceDefinition;
import org.jrtech.engines.fsm.model.FiniteStateMachineDefinition;
import org.jrtech.engines.fsm.model.LocalClassActionDefinition;

public class DefinitionExecutorUtils {

    public static final FiniteStateMachine createFiniteStateMachine(
            Map<String, AbstractActionDefinition> actionDefinitionCatalog, FiniteStateMachineDefinition fsmDefinition) {
        // Check null values
        if (actionDefinitionCatalog == null) {
            throw new RuntimeException(
                    "Action definition catalog provided is null. Please provide a valid action definition catalog.");
        }

        if (fsmDefinition == null) {
            throw new RuntimeException(
                    "Finite state machine definition provided is null. Please provide a valid finite state machine definition.");
        }

        // Control if action definition catalog is not empty
        if (actionDefinitionCatalog.isEmpty())
            throw new RuntimeException(
                    "Empty action definition catalog. System cannot create a finite state machine from an empty action definition catalog.");

        // Resolve Action Definition Id
        final List<String> invalidActionDefinitionIdList = new ArrayList<>();
        final Map<String, Action> actionInstanceCatalog = new LinkedHashMap<>();
        for (ActionInstanceDefinition aidef : fsmDefinition.getActionInstances()) {
            AbstractActionDefinition actiondef = actionDefinitionCatalog.get(aidef.getActionDefinitionId());
            if (actiondef == null) {
                invalidActionDefinitionIdList.add(aidef.getActionDefinitionId() + " - Cannot be found.");
                continue;
            }

            // Instantiate Action Definition -> Potential for async processing
            Action actionInstance = instantiateActionFromDefinition(actiondef);
            if (actionInstance == null) {
                invalidActionDefinitionIdList.add(aidef.getActionDefinitionId() + " - Fail to instatiate.");
                continue;
            }

            // Set Instance Name
            setInstanceName(actionInstance, aidef.getInstanceName());

            // Set action instance parameter mapping
            setParameterMapping(actionInstance, aidef.getParameterMappingTable());

            actionInstanceCatalog.put(aidef.getInstanceName(), actionInstance);
        }

        if (!invalidActionDefinitionIdList.isEmpty()) {
            throw new RuntimeException(
                    "Invalid action definition id(s).\n" + StringUtils.join("\n  - ", invalidActionDefinitionIdList));
        }

        FiniteStateMachine fsm = new FiniteStateMachine(fsmDefinition.getName(), fsmDefinition.getTargetEvents(),
                fsmDefinition.getOutputEvent(), new ArrayList<>(fsmDefinition.getAlternateEvents()));
        fsm.setInitialAction(actionInstanceCatalog.get(fsmDefinition.getInitialActionInstanceId()));
        // Chain Action and Events
        for (FiniteStateMachineDefinition.EventChainConfiguration ecConfig : fsmDefinition.getEventChainConfigurations()) {
            Action sourceActionInstance = actionInstanceCatalog.get(ecConfig.getSourceInstanceName());
            Action targetActionInstance = actionInstanceCatalog.get(ecConfig.getTargetInstanceName());

            fsm.configure(sourceActionInstance, ecConfig.getSourceOutputEvent(), targetActionInstance);
        }

        return fsm;
    }

    private static void setParameterMapping(Action actionInstance, Map<String, String> parameterMappingTable) {
        for (Entry<String, String> en : parameterMappingTable.entrySet()) {
            try {
                FieldUtils.writeField(actionInstance, en.getKey(), en.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private static void setInstanceName(Action actionInstance, String instanceName) {
        try {
            MethodUtils.invokeMethod(actionInstance, "setName", instanceName);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static Action instantiateActionFromDefinition(AbstractActionDefinition actionDefinition) {
        if (actionDefinition instanceof LocalClassActionDefinition) {
            return instantiateActionFromDefinition((LocalClassActionDefinition) actionDefinition);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static Action instantiateActionFromDefinition(LocalClassActionDefinition actionDefinition) {
        Action action = null;

        String className = actionDefinition.getActionClassName();
        try {
            Class<? extends Action> clazz = (Class<? extends Action>) Class.forName(className);
            action = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find action class from definition: " + actionDefinition, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot instantiate action class from definition: " + actionDefinition, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Cannot instantiate action class (illegal access) from definition: " + actionDefinition, e);
        }

        return action;
    }

}
