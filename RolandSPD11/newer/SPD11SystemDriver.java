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

import core.*;

/**
 *
 * @author peter
 */
public class SPD11SystemDriver extends Driver {
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5  6  7  8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 01 00 00 00 00 00 00 1c 63 f7");
    /**
     *
     */
    public SPD11SystemDriver(){
        super ("System","Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID         = "F041**601201";
        deviceIDoffset  = 2 ;
        bankNumbers     = new String[] {"System Settings"};
        patchNumbers    = new String[] {"Off Topic"};
        patchSize    = 39;
        patchNameSize =0;
    }
    public void requestPatchDump(int bankNum, int patchNum) {
        send(SYS_REQ.toSysexMessage(getDeviceID()));
    }
    public Patch createNewPatch() {
        byte sysex[] = { 
            (byte)0xF0, (byte)0x41, (byte)0x09, (byte)0x60, (byte)0x12,
            (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x7f, (byte)0x7f, (byte)0x7f, (byte)0x7f,
            (byte)0x3e, (byte)0xf7, 
            };
        Patch p = new Patch(sysex, this);
        return p;
    }

}
