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

import java.util.HashMap;
import java.util.Map;

/**
 * Simple concrete process context.
 *
 */
public class SimpleProcessContext implements ProcessContext {

    private static final long serialVersionUID = -2589949732138854502L;

    private final Map<String, Object> contextData;

    public SimpleProcessContext() {
        this(new HashMap<>());
    }

    protected SimpleProcessContext(Map<String, Object> contextData) {
        this.contextData = contextData;
    }

    @Override
    public boolean containsContextKey(String key) {
        return contextData.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getContextData(String key) {
        if (key == null)
            return null;

        Object value = contextData.get(key);

        try {
            return (T) value;
        } catch (ClassCastException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getContextDataOrDefault(String key, T defaultValue) {
        if (key == null)
            return defaultValue;

        try {
            T contextDataValue = getContextData(key);
            if (contextDataValue != null)
                return contextDataValue;
        } catch (Exception e) {
        }

        return defaultValue;
    }

    @Override
    public void removeContextData(String key) {
        contextData.remove(key);
    }

    @Override
    public <T> void setContextData(String key, T value) {
        contextData.put(key, value);
    }

    @Override
    public <T> void setAllContextData(Map<String, T> otherContextData) {
        if (otherContextData == null || otherContextData.size() < 1)
            return;

        contextData.putAll(otherContextData);
    }

}