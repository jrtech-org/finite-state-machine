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
package org.jrtech.engines.fsm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A definition of action set and their chain of event and action execution in a state machine. 
 * 
 * @author Jumin
 *
 */
public class FiniteStateMachineDefinition extends AbstractActionDefinition {

    private static final long serialVersionUID = 2216555446505511658L;
    
    private String initialActionInstanceId;
    
    private Set<String> targetEvents;
    
    private List<ActionInstanceDefinition> actionInstances;
    
    private List<EventChainConfiguration> eventChainConfigurations;
    
    public FiniteStateMachineDefinition() {
        super();
        
        this.targetEvents = new LinkedHashSet<>();
        this.actionInstances = new ArrayList<>(); 
        this.eventChainConfigurations = new ArrayList<>();
    }
    
    public String getInitialActionInstanceId() {
        return initialActionInstanceId;
    }
    
    public void setInitialActionInstanceId(String initialActionInstanceId) {
        this.initialActionInstanceId = initialActionInstanceId;
    }
    
    public Set<String> getTargetEvents() {
        return targetEvents;
    }
    
    public void setTargetEvents(Set<String> targetEvents) {
        this.targetEvents = targetEvents;
    }
    
    public List<ActionInstanceDefinition> getActionInstances() {
        return actionInstances;
    }
    
    public void setActionInstances(List<ActionInstanceDefinition> actionInstances) {
        this.actionInstances = actionInstances;
    }

    public List<EventChainConfiguration> getEventChainConfigurations() {
        return eventChainConfigurations;
    }
    
    public void setEventChainConfigurations(List<EventChainConfiguration> eventChainConfigurations) {
        this.eventChainConfigurations = eventChainConfigurations;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actionInstances == null) ? 0 : actionInstances.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FiniteStateMachineDefinition other = (FiniteStateMachineDefinition) obj;
        if (actionInstances == null) {
            if (other.actionInstances != null)
                return false;
        } else if (!actionInstances.equals(other.actionInstances))
            return false;
        return true;
    }

    public static class EventChainConfiguration implements Serializable {

        private static final long serialVersionUID = 8263781327466203939L;

        private String sourceInstanceName;
        
        private String sourceOutputEvent = "*";
        
        private String targetInstanceName;

        public String getSourceInstanceName() {
            return sourceInstanceName;
        }

        public void setSourceInstanceName(String sourceInstanceName) {
            this.sourceInstanceName = sourceInstanceName;
        }

        public String getSourceOutputEvent() {
            return sourceOutputEvent;
        }

        public void setSourceOutputEvent(String sourceOutputEvent) {
            this.sourceOutputEvent = sourceOutputEvent;
        }

        public String getTargetInstanceName() {
            return targetInstanceName;
        }

        public void setTargetInstanceName(String targetInstanceName) {
            this.targetInstanceName = targetInstanceName;
        } 
        
        @Override
        public String toString() {
            return sourceInstanceName + " (" + sourceOutputEvent + ") -> " + targetInstanceName;
        }
    }
}
