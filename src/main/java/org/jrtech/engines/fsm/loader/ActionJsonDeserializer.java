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

import org.jrtech.engines.fsm.model.AbstractActionDefinition;
import org.jrtech.engines.fsm.model.FiniteStateMachineDefinition;
import org.jrtech.engines.fsm.model.LocalClassActionDefinition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ActionJsonDeserializer extends JsonDeserializer<AbstractActionDefinition> {

    @Override
    public AbstractActionDefinition deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        
        AbstractActionDefinition actionDef = null;
        if (node.get("actionClassName").asText() != null) {
            actionDef = oc.treeToValue(node, LocalClassActionDefinition.class);
        } else {
            // Sub Process Definition
            actionDef = oc.treeToValue(node, FiniteStateMachineDefinition.class);
        }
        
//        actionDef.setDefinitionId(node.get("definitionId") != null ? node.get("definitionId").asText() : null);
//        actionDef.setName(node.get("name") != null ? node.get("name").asText() : null);
//        actionDef.setOutputEvent(node.get("outputEventName") != null ? node.get("outputEventName").asText() : null);
//        actionDef.setScope(node.get("scope") != null ? node.get("scope").asText() : null);
        
        return actionDef;
    }

}
