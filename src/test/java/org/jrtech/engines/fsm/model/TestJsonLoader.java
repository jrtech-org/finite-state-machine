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

import java.io.IOException;
import java.io.InputStream;

import org.jrtech.engines.fsm.loader.ActionJsonDeserializer;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class TestJsonLoader {

    @Test
    public void loadActionCatalog() {
        ObjectMapper mapper = new ObjectMapper();
        
        SimpleModule module = new SimpleModule();
        //module.addAbstractTypeMapping(AbstractActionDefinition.class, LocalClassActionDefinition.class);
        //module.addAbstractTypeMapping(AbstractActionDefinition.class, FiniteStateMachineDefinition.class);
        module.addDeserializer(AbstractActionDefinition.class, new ActionJsonDeserializer());
        mapper.registerModule(module);
        
        InputStream jsonStream = getClass().getResourceAsStream("/test_data/basic/action-def-catalog.json");
        try {
            AbstractActionDefinition[] objectList = mapper.readValue(jsonStream, AbstractActionDefinition[].class);
            for (int i = 0; i < objectList.length; i++) {
                System.out.println(objectList[i]);
            }
            // ArrayList<ActionDefinition> actionDefList = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            try {
                jsonStream.close();
            } catch (IOException e) {
            }
        }
    }
}
