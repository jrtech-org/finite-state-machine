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

/**
 * A local class action definition in a design time.
 * 
 * @author Jumin
 *
 */
public class LocalClassActionDefinition extends AbstractActionDefinition {

    private static final long serialVersionUID = -7696317287856289465L;

    private String actionClassName;

    /**
     * @return name of the concrete class that implements
     *         <code>Action</code> interface.
     */
    public String getActionClassName() {
        return actionClassName;
    }

    /**
     * @param actionClassName
     *            name of the class that implements
     *            <code>Action</code> interface. The class
     *            must be instantiable without any argument.
     */
    public void setActionClassName(String actionClassName) {
        this.actionClassName = actionClassName;
        internalLabel = null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actionClassName == null) ? 0 : actionClassName.hashCode());
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
        LocalClassActionDefinition other = (LocalClassActionDefinition) obj;
        if (actionClassName == null) {
            if (other.actionClassName != null)
                return false;
        } else if (!actionClassName.equals(other.actionClassName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (internalLabel == null) {
            internalLabel = getName() + " - " + actionClassName + "(" + getScope() + "): " + getOutputEvent();
        }

        return internalLabel;
    }

}
