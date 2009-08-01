package synthdrivers.RolandSPD11;

import core.Driver;
import core.Patch;
import core.SysexHandler;

public class SPD11SettingsDriver extends Driver {    
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5       6         7     8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 00 *bankNum* 00 00 00 00 00 11 *checkSum* F7");
    public SPD11SettingsDriver(){
        super ("Settings" , "Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5 6 7
        sysexID        = "F041**601200**00";
        deviceIDoffset    = 2 ;
        bankNumbers     = new String[] {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64"
            };
        patchNumbers     = new String[] {
            "FX & Pedal Settings"
        };
        patchSize    = 19;
        patchNameSize =0;
        //sysexRequestDump = null; //I don't have to use this field as I'm overriding the requestPatchDump method.
    }
    /**
     * SPD11PatchDriver CheckSum calculation replaces the offset byte<br> 
     * with the result of the formula 128 -(sum % 128)
     * @param p the patch we work with
     * @param start is where the values start adding to the sum
     * @param end  is where the values stops adding to the sum
     * @param offset is where the checksum value is replaced
     * @author Peter Geirnaert
     */
    protected void calculateChecksum(Patch p, int start, int end, int offset) {
        int sum=0x00;
        //add all values of address and data
        for(int i=start;i<offset;i++){
            sum += p.sysex[i];
        };
        //calculate checkSum with a simple formula
        int chkSm = 128-(sum % 128);
        p.sysex[offset] = (byte)chkSm;
    }
    public void requestPatchDump(int bankNum, int patchNum) {
        int checkSum = 0x7f -bankNum - patchNum - 0x11;
        final SysexHandler.NameValue[] nameValues = {
            new SysexHandler.NameValue("bankNum", bankNum),
            new SysexHandler.NameValue("checkSum", checkSum)};
        send(SYS_REQ.toSysexMessage(getDeviceID(), nameValues));
    }
    public Patch createNewPatch() {
        //replace this by a standard "Settings" : FIXIT !! 
        byte sysex[] = {
            (byte)0xF0, (byte)0x41, (byte)0x09, (byte)0x60, (byte)0x12,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x06, (byte)0x05, (byte)0x00, (byte)0x18,
            (byte)0x14, (byte)0x07, (byte)0x00, (byte)0x05,
            (byte)0x09, (byte)0x2F,
            };
        Patch p = new Patch(sysex, this);
        return p;
    }
    /**
     * Sends a patch to a set location on a synth.<p>
     * Override this if required.
     * @see Patch#send(int, int)
     */
    protected void storePatch(Patch p, int bankNum, int patchNum) {
        //replace old values in Patch p
        p.sysex[6]=(byte)bankNum;
        p.sysex[7]=(byte)patchNum;
        //recalculate the new checksum with the new bankNum and patchNum in the patch.
        calculateChecksum(p,5,12,13); 
        //send program change equal to the bankNum value (=SPD11patch), not the patchNum (=SPD11pad) 
        //this makes the driver switch the SPD11 to the edited patch when sending a pad-data. So the user can immediately use the sound. deprecated because patchDriver handles it.
        //setPatchNum(bankNum);
        sendPatch(p);        
    }
}
