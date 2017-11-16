/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.drools.workbench.screens.guided.dtable.client.wizard.column.plugins.commons;

import org.drools.workbench.models.datamodel.rule.FactPattern;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatternWrapperTest {

    @Test
    public void testMakeFactPattern() throws Exception {

        final String factType = "factType";
        final String boundName = "boundName";
        final PatternWrapper patternWrapper = new PatternWrapper(factType, boundName);

        final FactPattern factPattern = patternWrapper.makeFactPattern();

        assertEquals(factType, factPattern.getFactType());
        assertEquals(boundName, factPattern.getBoundName());
    }
}