//
// JODConverter - Java OpenDocument Converter
// Copyright 2009 Art of Solving Ltd
// Copyright 2004-2009 Mirko Nasato
//
// JODConverter is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// JODConverter is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General
// Public License along with JODConverter.  If not, see
// <http://www.gnu.org/licenses/>.
//
package org.artofsolving.jodconverter.office;

import static org.testng.Assert.assertTrue;

import org.artofsolving.jodconverter.office.ConnectionOfficeManager;
import org.artofsolving.jodconverter.office.UnoUrl;
import org.artofsolving.jodconverter.office.OfficeProcess;
import org.artofsolving.jodconverter.office.OfficeProcessConfiguration;
import org.artofsolving.jodconverter.util.ReflectionUtils;
import org.testng.annotations.Test;

@Test(groups="integration")
public class ConnectionOfficeManagerTest {

    public void executeTask() throws Exception {
        UnoUrl unoUrl = UnoUrl.socket(2002);
        OfficeProcess officeProcess = new OfficeProcess(new OfficeProcessConfiguration(unoUrl));
        officeProcess.start();
        Thread.sleep(2000);
        
        ConnectionOfficeManager manager = new ConnectionOfficeManager(2002);
        manager.start();
        
        MockOfficeTask task = new MockOfficeTask();
        manager.execute(task);
        assertTrue(task.isCompleted());
        
        manager.stop();
        //TODO replace when OfficeProcess has a forciblyTerminate()
        Process process = (Process) ReflectionUtils.getPrivateField(officeProcess, "process");
        process.destroy();
    }

    //TODO test auto-reconnection

}