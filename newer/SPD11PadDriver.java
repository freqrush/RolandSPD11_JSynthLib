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
public class SPD11PadDriver extends Driver {
    /**
     * 0->4: header
     * 5->8: address (bankNum is about the patch in SPD-11 memory
     *                patchNum is about the pad)
     */
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5       6         7     8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 00 *bankNum* *patchNum* 00 00 00 00 11 *checkSum* F7");
    /**
     *
     */
    public SPD11PadDriver(){
        super ("Pad" , "Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID        = "F041**601200";
        deviceIDoffset    = 2 ;
        bankNumbers     = SPD11_Constants.SPD11_PATCHES;
        patchNumbers     = new String[] {
        "A-int-1", "A-int-2", "A-int-3", "A-int-4",
        "A-int-5", "A-int-6", "A-int-7", "A-int-8",
        "A-ext-h1", "A-ext-h2", "A-ext-h3", "A-ext-h4",
        "A-ext-r1", "A-ext-r2", "A-ext-r3", "A-ext-r4",
        "B-int-1", "B-int-2", "B-int-3", "B-int-4",
        "B-int-5", "B-int-6", "B-int-7", "B-int-8",
        "B-ext-h1", "B-ext-h2", "B-ext-h3", "B-ext-h4",
        "B-ext-r1", "B-ext-r2", "B-ext-r3", "B-ext-r4"
        };
        patchSize    = 28;
        patchNameSize =0;
        //sysexRequestDump = null; //I don't have to use this field as I'm overriding the requestPatchDump method.
    }
    /**
     * SPD11PadDriver CheckSum calculation replaces the offset byte<br> 
     * with the result of the formula 128 -(sum % 128)
     * @param p the patch we work with
     * @param start is where the values start adding to the sum
     * @param end  is where the values stops adding to the sum
     * @param offset is where the checksum value is replaced
     */
    protected void calculateChecksum(Patch p, int start, int end, int offset) {
        int sum=0x00;
        //add all values of address and data
        for(int i=start;i<offset;i++){
            sum += p.sysex[i];
        }
        //calculate checkSum with a simple formula
        int chkSm = 128-(sum % 128);
        p.sysex[offset] = (byte)chkSm;
    }
/**
 * @see core.Driver#requestPatchDump(int, int)
 * @see SPD11SettingsDriver#requestPatchDump(int, int)
 * @param bankNum is the SPD-11 patch (0~63)
 * @param patchNum is increased with +1 because patchNum 0 is "settings"
 */
    public void requestPatchDump(int bankNum, int patchNum) {
        int checkSum = 0x7f -bankNum - patchNum - 0x11;
        final SysexHandler.NameValue[] nameValues = {
            new SysexHandler.NameValue("patchNum", patchNum+1), //= pad
            new SysexHandler.NameValue("bankNum", bankNum),     //= patch = drumkit
            new SysexHandler.NameValue("checkSum", checkSum)};
        send(SYS_REQ.toSysexMessage(getDeviceID(), nameValues));
    }
    public Patch createNewPatch() {
        byte[] sysex = {
            (byte)0xF0, (byte)0x41, (byte)0x09, (byte)0x60, (byte)0x12,
            (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00,
            (byte)0x06, (byte)0x05, (byte)0x00, (byte)0x18,
            (byte)0x14, (byte)0x07, (byte)0x00, (byte)0x05,
            (byte)0x09, (byte)0x2F, (byte)0x00, (byte)0x01,
            (byte)0x10, (byte)0x00, (byte)0x0B, (byte)0x00,
            (byte)0x00, (byte)0x68, (byte)0xF7
            };
        Patch p = new Patch(sysex, this);
        return p;
    }
    /* Create new padPatch from byte[28] sysex
     * This may be called by a SPD11PatchDriver of type BankDriver or Converter
     * @param sysex 28 bytes pad data
    */

    public Patch createNewPatch(byte[] sysex) {
        Patch p = new Patch(sysex, this);
        return p;
    }
    public IPatch createPatch(byte[] sysex) {
            return createNewPatch(sysex);
            /*
        Patch p = new Patch(sysex, this);
        return p;
             */
    }
    /**
     * Sends a pad to a set location on a synth.<p>
     * Override this if required.
     * @param p 
     * @param bankNum
     * @param patchNum
     * @see Patch#send(int, int)
     */
    protected void storePatch(Patch p, int bankNum, int patchNum) {
        //replace old values in Patch p
        p.sysex[6]=(byte)bankNum;
        p.sysex[7]=(byte)(patchNum+1);
        //recalculate the new checksum with the new bankNum and patchNum in Patch p.
        SPD11_Constants.calculateChecksum(p,5,25,26);
        //replace p.sysex[deviceIDoffset] with getDeviceID()-1 and send
        p.sysex[deviceIDoffset] = (byte) (getDeviceID() - 1);
        send(p.sysex);
    }
    /**
     *
     * @param p the patch with pad data
     * @return the name of the pad this is about.
     */
    protected String getPatchName(Patch p){
        /**
         * pad 0x00 = patch Settings, pad 0x01 = patchNumbers[0]
         */
        return SPD11_Constants.SPD11_PADS[(p.sysex[7])]
                + " _ P"
                + SPD11_Constants.SPD11_PATCHES1[p.sysex[6]];
    }
    public JSLFrame editPatch(Patch p) {
        return new SPD11PadEditor((Patch) p);
    }
}
