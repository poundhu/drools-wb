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
package org.drools.workbench.screens.guided.dtable.client.widget.table.keyboard;

import com.google.gwt.event.dom.client.KeyCodes;
import org.junit.Test;
import org.uberfire.ext.wires.core.grids.client.widget.grid.impl.KeyboardOperation.TriStateBoolean;
import org.uberfire.ext.wires.core.grids.client.widget.grid.selections.SelectionExtension;
import org.uberfire.ext.wires.core.grids.client.widget.layer.GridLayer;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SelectNextCellOnRowTest extends BaseKeyboardOperationTest<SelectNextCellOnRow> {

    @Override
    protected SelectNextCellOnRow getHandler(final GridLayer layer) {
        return new SelectNextCellOnRow(layer);
    }

    @Override
    protected int getExpectedKeyCode() {
        return KeyCodes.KEY_TAB;
    }

    @Override
    protected TriStateBoolean getExpectedShiftKeyState() {
        return TriStateBoolean.FALSE;
    }

    @Override
    protected TriStateBoolean getExpectedControlKeyState() {
        return TriStateBoolean.FALSE;
    }

    @Test
    public void checkPerformance() {
        handler.perform(gridWidget,
                        true,
                        true);

        verify(gridWidget,
               times(1)).adjustSelection(eq(SelectionExtension.RIGHT),
                                         eq(false));
    }
}
