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

public class ParameterDefinition implements Serializable {

    private static final long serialVersionUID = -4133525774675004722L;
    
    public static final String DIRECTION_INPUT = "IN";
    
    public static final String DIRECTION_OUTPUT = "OUT";
    
    private String name;

    private String dataType = DataTypes.STRING;

    private String defaultValue = null;
    
    private String direction = DIRECTION_INPUT;

    public ParameterDefinition() {
        this(null, DataTypes.STRING, null);
    }

    public ParameterDefinition(String name) {
        this(name, DataTypes.STRING, null);
    }

    public ParameterDefinition(String name, String dataType) {
        this(name, dataType, null);
    }

    public ParameterDefinition(String name, String dataType, String defaultValue) {
        this.name = name;
        this.dataType = (dataType == null) ? DataTypes.STRING : dataType;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        if (direction == null) {
            this.direction = "";
        } else if (direction.toUpperCase().startsWith(DIRECTION_INPUT)) {
            this.direction = DIRECTION_INPUT;
        } else if (direction.toUpperCase().startsWith(DIRECTION_OUTPUT)) {
            this.direction = DIRECTION_OUTPUT;
        } else {
            this.direction = "";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
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
        ParameterDefinition other = (ParameterDefinition) obj;
        if (dataType == null) {
            if (other.dataType != null)
                return false;
        } else if (!dataType.equals(other.dataType))
            return false;
        if (defaultValue == null) {
            if (other.defaultValue != null)
                return false;
        } else if (!defaultValue.equals(other.defaultValue))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (direction == null) {
            if (other.direction != null)
                return false;
        } else if (!direction.equals(other.direction))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(name);
        sb.append("(").append(dataType);
        if (defaultValue == null)
            sb.append(")");
        else
            sb.append("):").append(defaultValue);

        return sb.toString();
    }

}
