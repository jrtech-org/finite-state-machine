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
import java.util.HashMap;
import java.util.Map;

public class ActionInstanceDefinition implements Serializable {

    private static final long serialVersionUID = -844224731653945088L;

    private String actionDefinitionId;

    private String instanceName;

    private Map<String, String> parameterMappingTable;

    public ActionInstanceDefinition() {
        super();

        parameterMappingTable = new HashMap<>();
    }

    public String getActionDefinitionId() {
        return actionDefinitionId;
    }

    public void setActionDefinitionId(String actionDefinitionId) {
        this.actionDefinitionId = actionDefinitionId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Map<String, String> getParameterMappingTable() {
        return parameterMappingTable;
    }

    public void setParameterMappingTable(Map<String, String> parameterMappingTable) {
        this.parameterMappingTable = parameterMappingTable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actionDefinitionId == null) ? 0 : actionDefinitionId.hashCode());
        result = prime * result + ((instanceName == null) ? 0 : instanceName.hashCode());
        result = prime * result + ((parameterMappingTable == null) ? 0 : parameterMappingTable.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActionInstanceDefinition other = (ActionInstanceDefinition) obj;
        if (actionDefinitionId == null) {
            if (other.actionDefinitionId != null)
                return false;
        } else if (!actionDefinitionId.equals(other.actionDefinitionId))
            return false;
        if (instanceName == null) {
            if (other.instanceName != null)
                return false;
        } else if (!instanceName.equals(other.instanceName))
            return false;
        if (parameterMappingTable == null) {
            if (other.parameterMappingTable != null)
                return false;
        } else if (!parameterMappingTable.equals(other.parameterMappingTable))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return instanceName + " (" + actionDefinitionId + ")";
    }
}
