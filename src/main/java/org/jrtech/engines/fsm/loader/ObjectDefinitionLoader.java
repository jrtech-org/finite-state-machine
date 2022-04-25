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
package org.jrtech.engines.fsm.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import org.jrtech.engines.fsm.model.AbstractActionDefinition;
import org.jrtech.engines.fsm.model.FiniteStateMachineDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A class that contains methods to load object definitions e.g.
 * <ul>
 * <li>Catalog of ActionDefinition
 * <li>FiniteStateMachineDefinition
 * </ul>
 * <p>
 * 
 * @author Jumin
 *
 */
public class ObjectDefinitionLoader {

    public Map<String, AbstractActionDefinition> loadActionCatalog(InputStream jsonStream) throws ObjectLoadingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AbstractActionDefinition.class, new ActionJsonDeserializer());
        mapper.registerModule(module);
        try {
            AbstractActionDefinition[] objectArray = mapper.readValue(jsonStream, AbstractActionDefinition[].class);

            Map<String, AbstractActionDefinition> catalog = new TreeMap<>();
            for (int i = 0; i < objectArray.length; i++) {
                AbstractActionDefinition actionDef = objectArray[i];
                if (!actionDef.isValid()) {
                    // Invalid action definition
                    continue;
                }
                catalog.put(actionDef.getDefinitionId(), actionDef);
            }

            return catalog;
        } catch (Exception e) {
            throw new ObjectLoadingException(AbstractActionDefinition.class.getSimpleName(), e);
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                }
        }
    }

    public FiniteStateMachineDefinition loadFiniteStateMachineDefinition(InputStream jsonStream)
            throws ObjectLoadingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FiniteStateMachineDefinition fsmDef = mapper.readValue(jsonStream, FiniteStateMachineDefinition.class);

            return fsmDef;
        } catch (Exception e) {
            throw new ObjectLoadingException(FiniteStateMachineDefinition.class.getSimpleName(), e);
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                }
        }
    }
}
