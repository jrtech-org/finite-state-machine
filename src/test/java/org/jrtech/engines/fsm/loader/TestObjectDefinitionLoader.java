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
package org.jrtech.engines.fsm.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jrtech.engines.fsm.model.AbstractActionDefinition;
import org.jrtech.engines.fsm.model.FiniteStateMachineDefinition;
import org.junit.Assert;
import org.junit.Test;

public class TestObjectDefinitionLoader {

    private ObjectDefinitionLoader defLoader = new ObjectDefinitionLoader();

    @Test
    public void loadActionCatalog() {
        InputStream jsonStream = getClass().getResourceAsStream("/test_data/basic/action-def-catalog.json");
        try {
            Map<String, AbstractActionDefinition> catalog = defLoader.loadActionCatalog(jsonStream);
            catalog.values().forEach(v -> {
                System.out.println(v);
            });
        } catch (ObjectLoadingException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                }
        }
    }
    
    @Test
    public void loadFiniteStateMachineDefinition() {
        InputStream jsonStream = getClass().getResourceAsStream("/test_data/basic/fsm-def-1.json");
        try {
            FiniteStateMachineDefinition fsmDef = defLoader.loadFiniteStateMachineDefinition(jsonStream);
            System.out.println(fsmDef);
        } catch (ObjectLoadingException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            if (jsonStream != null)
                try {
                    jsonStream.close();
                } catch (IOException e) {
                }
        }   
    }
}
