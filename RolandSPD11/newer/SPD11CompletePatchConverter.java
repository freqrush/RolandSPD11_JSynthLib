/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;

import core.*;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author peter
 */
public class SPD11CompletePatchConverter extends Converter {
    //private static final int PADSIZE = 28;
    //private static final int SETTINGSSIZE = 19;
    //private static final int PATCHSIZE = (32*28)+19;
    protected SPD11PadDriver padDriver;
    protected SPD11SettingsDriver settingsDriver;
    public SPD11CompletePatchConverter(SPD11PadDriver padDriver, SPD11SettingsDriver settingsDriver){
        super("CompletePatch Converter", "Peter Geirnaert");
        sysexID = "F041**6012";
        this.padDriver = padDriver;
        this.settingsDriver = settingsDriver;
    }
    public SPD11CompletePatchConverter(){
        super("Complete Patch Converter", "Peter Geirnaert");
        sysexID = "F041**601200**00";
    }
    /** Convert a SinglePatch into a BankPatch
     * with 32 "pad" and 1 "settings" Patch values.
     * @param p Patch to be converted
     * @return converted Patch
     */
    public Patch[] extractPatch(Patch p) {
        SysexMessage[] msgs = p.getMessages(); // get the sysexMessages from the patch
        Patch[] pads = new Patch[33];
        pads[0] = settingsDriver.createNewPatch(msgs[0].getData());
        for (int i=0;i<msgs.length-2;i++) {
            byte[] sysex = msgs[i+1].getData();
            pads[i+1] = padDriver.createNewPatch(sysex);
        }
        //return new SPD11PatchDriver.createNewPatch(pads);
        return pads;
    }
    // If extractPatch returns an array of Patches whose drivers are set
    // properly, override this by;
    public IPatch[] createPatches(byte[] sysex) {
        return extractPatch(new Patch(sysex, this));
    }
}
