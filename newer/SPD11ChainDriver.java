/*
 * Copyright 2009 Peter Geirnaert
 *
 * This file is part of JSynthLib.
 *
 * JSynthLib is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JSynthLib is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JSynthLib; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package synthdrivers.RolandSPD11;

import core.Driver;
import core.SysexHandler;
import core.Patch;
import core .JSLFrame;

/**
 * SingleDriver for getting the "chain setup" sysexmessage
 * @author peter
 */
public class SPD11ChainDriver extends Driver {
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5  6  7  8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 02 00 00 00 00 00 00 40 3E F7");
    /**
     * Creates a new instance of SPD11ChainDriver
     */
    public SPD11ChainDriver(){
        super ("Chain", "Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID         = "F041**601202";
        deviceIDoffset  = 2 ;
        bankNumbers     = new String[] {"Chain Setup"};
        patchNumbers    = new String[] {"Off Topic"};
        patchSize    = 75;
        patchNameSize =0;
    }
    public void requestPatchDump(int bankNum, int patchNum) {
        final SysexHandler.NameValue[] nameValues = { };
        send(SYS_REQ.toSysexMessage(getDeviceID(), nameValues));
    }
    public JSLFrame editPatch(Patch p) {
     return new SPD11ChainEditor(p);
    }
    public void sendPatch(Patch p) {
        send(p.sysex);
    }
}
