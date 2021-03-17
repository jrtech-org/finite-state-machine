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

import java.util.ArrayList;
import java.util.List;

/**
 * A common definition that has one or more parameters.
 * 
 * @author Jumin
 *
 */
public abstract class AbstractParameterizableDefinition extends AbstractCommonDefinition {
    
    private static final long serialVersionUID = 6815933663808553992L;

    private List<ParameterDefinition> parameterDefinitions;
    
    public AbstractParameterizableDefinition() {
        super();
        
        this.parameterDefinitions = new ArrayList<>();
    }

    public List<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }
    
    public void setParameterDefinitions(List<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((parameterDefinitions == null) ? 0 : parameterDefinitions.hashCode());
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
        AbstractParameterizableDefinition other = (AbstractParameterizableDefinition) obj;
        if (parameterDefinitions == null) {
            if (other.parameterDefinitions != null)
                return false;
        } else if (!parameterDefinitions.equals(other.parameterDefinitions))
            return false;
        return true;
    }
    
}
