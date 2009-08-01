package synthdrivers.RolandSPD11;

//import synthdrivers.RolandSPD11.SPD11PadDriver;
//import synthdrivers.RolandSPD11.SPD11SettingsDriver;
import core.BankDriver;
import core.Patch;

/**
 * Patch Driver for Roland SPD-11 Total Percussion Pad
 *
 * @author <a href="mailto:peter.geirnaert@gmail.com">Peter Geirnaert</a>
 * @version $Id :SPD11PatchDriver.java 01 02:05 AM Monday, January 26 2009 $ 
 * 
 */
public class SPD11PatchDriver extends BankDriver {
    private final SPD11PadDriver padDriver;
    private final SPD11SettingsDriver settingsDriver;
    public SPD11PatchDriver(SPD11PadDriver padDriver, SPD11SettingsDriver settingsDriver) {
        super ("Patch","Peter Geirnaert",33,8);
        this.padDriver = padDriver;
        this.settingsDriver = settingsDriver;
        sysexID= "F041**601200";
        deviceIDoffset=2;
        bankNumbers = new String[] {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64"
        };
        patchNumbers=new String[]{//"all data" //the patchNumbers are used for import/export of a single pad or settings
                                  //**
            "A-int1", "A-int2", "A-int3", "A-int4", "A-int5", "A-int6", "A-int7", "A-int8",
            "A-ext-h2", "A-ext-h2", "A-ext-h3", "A-ext-h4", "A-ext-r1", "A-ext-r2", "A-ext-r3", "A-ext-r4",
            "B-int1", "B-int2", "B-int3", "B-int4", "B-int5", "B-int6", "B-int7", "B-int8",
            "B-ext-h2", "B-ext-h2", "B-ext-h3", "B-ext-h4", "B-ext-r1", "B-ext-r2", "B-ext-r3", "B-ext-r4",
            "Fx & Pedal Settings"
            //*/
        };
        singleSysexID="F041**60";
        singleSize=28 | 19;
        
    }
    // private final int numPatches = 33;
    // private final int numColumns = 8;
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
    /**
     * getPatch gets a "Settings" or a "Pad" patch from the bank (known as SPD11patch)
     * @param Patch bank is the SPD11Patch data we're working with
     * @param int patchNum is the selected pad, or the patchSettings
     */
    // @Override
    public Patch getPatch(Patch bank, int patchNum) {
        if (patchNum==33){
            byte[] sysex = new byte[19];
            System.arraycopy(bank.sysex, 0, sysex, 0, 19);
            return new Patch(sysex, settingsDriver);
        }
        else {
            byte[] sysex = new byte[28];
            System.arraycopy(bank.sysex, 19+28*patchNum, sysex, 19+28*(patchNum+1), 28);
            return new Patch(sysex, padDriver);
        }
    }
    // @Override
    public String getPatchName(Patch bank, int patchNum) {
        // SPD11 doesn't use names but maybe i can make it use the origin of the pad "A-int1, B-ext5, ..."
        return null;
    }
    //  @Override
    public void putPatch(Patch bank, Patch single, int patchNum) {
        if ((single.getDriver() == settingsDriver)&&(patchNum == 33)){
            //recalculate the checksum for single with it's new address.
            calculateChecksum(single, 5, 16, 17);
            //copy the new single in the bank at location 0 for settings
            System.arraycopy(single,0,bank,0,19);
        }
        else {
            if (single.getDriver() == padDriver){
                //change patchNum in the single Patch 
                single.sysex[7]=(byte)patchNum;
                //put bankNum from the bank in the new single Patch
                single.sysex[6]=bank.sysex[6];
                //checksum here because new address changes checksum 
                //(maybe this should be done only once after also the bankNum has changed)
                calculateChecksum(single, 5, 25, 26);
                // copy new single to new address in the bank
                System.arraycopy(single,0,bank,19+28*patchNum,28);
            }
        }
    }
    // @Override
    public void setPatchName(Patch bank, int patchNum, String name) {
        // TODO Auto-generated method stub

    }
    /**
     * request a full SPD11patch -> this is a bank of pads + settings so patchNum is ignored
     */
    public void requestPatchDump(int bankNum, int patchNum) {
        
    }
    /**
     * Sends an SPD11Patch to a new location, calculating the new<br>
     * checksums with the new value in the addresses of each pad or the settings.<p>
     * @param bankNum is the SPD11 patch 
     * @param patchNum is ignored
     * @see Patch#send(int, int)
     */
    public void storePatch(Patch p, int bankNum, int patchNum) {
        //replace the bankNum value of the first part: "settings"
        p.sysex[6]=(byte)bankNum;
        //calculate the new "settings" checksum with the new bankNum .
        calculateChecksum(p,5,16,17); 
        //replace banknum values and calculate checkSums of "pad1" to "pad32"
        for(int i=0;i<32;i++){
        p.sysex[25+i*28]=(byte)bankNum;
        //recalculate the new "pad" checksums with the new bankNum .
        calculateChecksum(p,24+i*28,25+i*28,36+i*28); 
        }
        //send the new patch
        sendPatch(p);        
    }
   

}
