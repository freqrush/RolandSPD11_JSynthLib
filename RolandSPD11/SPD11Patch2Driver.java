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
import core.JSLFrame;

/**
 *
 * @author peter
 */
public class SPD11Patch2Driver extends Driver {
    private final SPD11PadDriver pdDriver;
    private final SPD11SettingsDriver sttngsDriver;
    /** SysexHandler to get 1 pad or the settings (->patchNum 0x00) */
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5       6         7     8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 00 *bankNum* *patchNum* 00 00 00 00 11 *checkSum* F7");
    /**
     * 
     * @param padDriver
     * @param settingsDriver
     */
    public SPD11Patch2Driver(SPD11PadDriver padDriver, SPD11SettingsDriver settingsDriver){
        super("CompletePatch-+","Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID        = "F041**601200";
        deviceIDoffset    = 2 ;
        bankNumbers     = new String[]{"Roland SPD-101 Patch"};
        patchNumbers     = SPD11_Constants.SPD11_PATCHES;
        patchSize    = 19+(32*28); //settings + 32 pads
        patchNameSize =0;
        this.pdDriver = padDriver;
        this.sttngsDriver = settingsDriver;
        //sysexRequestDump = null; //I don't have to use this field as I'm overriding the requestPatchDump method.
    }
    // constructor for use with DumpAllConverter
    public SPD11Patch2Driver(){
        super("CompletePatch","Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID        = "F041**601200";
        deviceIDoffset    = 2 ;
        bankNumbers     = new String[]{"Roland SPD-11 Patch"};
        patchNumbers     = SPD11_Constants.SPD11_PATCHES;
        patchSize    = 19+(32*28); //settings + 32 pads
        patchNameSize =0;
        this.pdDriver = null;
        this.sttngsDriver = null;
    }
    public void requestPatchDump(int bankNum, int patchNum) {
        sttngsDriver.requestPatchDump(patchNum,123);
        try {
            Thread.sleep(30);  // wait at least 20 milliseconds .
        } catch (Exception e) {
            ErrorMsg.reportStatus(e);
        }
        for (int i=0;i<33;i++) {
            pdDriver.requestPatchDump(patchNum,i);
            try {
                Thread.sleep(30);  // wait at least 20 milliseconds .
            } catch (Exception e) {
                ErrorMsg.reportStatus(e);
            }
        }
    }
        /**
     * Used in <code>SPD11PadConverter</code> to find out if
     * @param i is 1 or 0
     * @return if i is 0 returns SPD11PadDriver, if i is 1 SPD11SettingsDriver
     * @TODO put method in SPD11_Constants because SPD11PatchDriver also uses this
     */
    Driver getPatchDriver(int i) {
        if(i == 0){
            return pdDriver;
        }
        else {
            return sttngsDriver;
        }
    }
    protected String getPatchName(Patch p){
        return SPD11_Constants.SPD11_PATCHES[p.sysex[6]];
    }
    public JSLFrame editPatch(Patch p)
    {
     return new SPD11Patch2Editor((Patch)p);
    }
}
