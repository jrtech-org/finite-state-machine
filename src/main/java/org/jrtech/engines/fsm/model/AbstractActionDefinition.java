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
package org.jrtech.engines.fsm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract definition of an action in a design time. This class also represents a specification of the runtime <code>org.jumin.engines.fsm.model.Action</code>.
 * 
 * @author Jumin
 *
 */
public abstract class AbstractActionDefinition extends AbstractParameterizableDefinition {

    private static final long serialVersionUID = -4516828963659036280L;

    private String outputEvent;
    
    private Set<String> alternateEvents;
    
    public AbstractActionDefinition() {
        super();
        
        this.alternateEvents = new HashSet<>();
    }
    
    public String getOutputEvent() {
        return outputEvent;
    }

    public void setOutputEvent(String outputEvent) {
        this.outputEvent = outputEvent;
        internalLabel = null;
    }

    public Set<String> getAlternateEvents() {
        return alternateEvents;
    }

    public void setAlternateEvents(Set<String> alternateEvents) {
        this.alternateEvents = alternateEvents;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        internalLabel = null;
    }
    
    public boolean isValid() {
        if (getDefinitionId() == null || "".equals(getDefinitionId().trim())) return false;
        if (getName() == null || "".equals(getName().trim())) return false;
        if (outputEvent == null || "".equals(outputEvent.trim())) return false;

        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((alternateEvents == null) ? 0 : alternateEvents.hashCode());
        result = prime * result + ((outputEvent == null) ? 0 : outputEvent.hashCode());
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
        AbstractActionDefinition other = (AbstractActionDefinition) obj;
        if (alternateEvents == null) {
            if (other.alternateEvents != null)
                return false;
        } else if (!alternateEvents.equals(other.alternateEvents))
            return false;
        if (outputEvent == null) {
            if (other.outputEvent != null)
                return false;
        } else if (!outputEvent.equals(other.outputEvent))
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (internalLabel == null) {
            internalLabel = getName() + " - " + "(" + getScope() + "): " + outputEvent;
        }
        
        return internalLabel;
    }
    
}
