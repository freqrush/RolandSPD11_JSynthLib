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
public class SPD11PadConverter extends Converter {

    final SPD11PadDriver padDriver;
    final SPD11SettingsDriver settingsDriver;
    final SPD11PatchDriver kitDriver;
    final SPD11Patch2Driver patchDriver;

    /**
     * convert a bankPatch to singlePatch[]
     * should be named SPD11CompletePatchConverter and/or SPD11DumpallConverter
     * SPD11CompletePatchConverter: converts a dump of one spd11 patch into a jsynthlib SPD11 "Patch" Patch,
     * which is a BankPatch containing 32 "Pad" and 1 "Settings" SinglePatch values.
     * SPD11DumpallConverter: converts a spd11 triggered "dump all" sysexfile into a jsynthlib SPD11 "Dump All" Patch,
     * which is a bankPatch containing 64 "Patch" BankPatches , a "System" SinglePatch and a "Chain" Single Patch.
     */
    public SPD11PadConverter(SPD11PatchDriver theDriver) {
		super("Converter","Peter Geirnaert");

		this.kitDriver = theDriver;
		sysexID         = "F041**601200";
		padDriver    = (SPD11PadDriver) theDriver.getPatchDriver(0);
                settingsDriver =(SPD11SettingsDriver) theDriver.getPatchDriver(1); // was kitDriver.getSettingsDriver(1);
                patchDriver = null;
    }
    /**
     * convert a CompletePatch to a  Bank of 32 pad and 1 settings patches
     */
    public SPD11PadConverter(SPD11Patch2Driver theDriver) {
		super("Converter","Peter Geirnaert");

		this.patchDriver = theDriver;
		sysexID         = "F041**601200";
		padDriver    = (SPD11PadDriver) theDriver.getPatchDriver(0);
                settingsDriver =(SPD11SettingsDriver) theDriver.getPatchDriver(1); // was kitDriver.getSettingsDriver(1);
                kitDriver = null;
    }
    public boolean supportsPatch(String header, byte[] sysex) {
        if (sysex.length < 16)
	        return false;
        return sysex[0] == (byte) 0xF0 && sysex[1] == 0x41 &&
    		sysex[3] == 0x60 && sysex[4] == 0x12;
    }
     /**
     * Convert a bulk patch into an array of single and/or bank patches.
     */
    public Patch[] extractPatch(Patch p) {
        // byte[] sysexpatch = p.getByteArray();
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
    public IPatch[] createPatches(byte[] sysex) {
    return extractPatch(new Patch(sysex, this));
    }


}
