package synthdrivers.RolandSPD11;

//import synthdrivers.RolandSPD11.SPD11PadDriver;
//import synthdrivers.RolandSPD11.SPD11SettingsDriver;
import core.*;
import javax.sound.midi.SysexMessage;
//import java.io.UnsupportedEncodingException;
/**
 * Patch Driver for Roland SPD-11 Total Percussion Pad
 *
 * @author <a href="mailto:peter.geirnaert@gmail.com">Peter Geirnaert</a>
 * @version $Id :SPD11PatchDriver.java 01 02:05 AM Monday, January 26 2009 $ 
 * 
 */
public class SPD11PatchDriver extends BankDriver {
    /**
     *
     */
    public final int numPatches = 33; //maybe this shouldn't be commented out
    /**
     *
     */
    public final int numCols = 3; // and this also not!
    private final SPD11PadDriver padDriver;
    private final SPD11SettingsDriver settingsDriver;
    /**
     *
     * @param padDriver
     * @param settingsDriver
     */
    public SPD11PatchDriver(SPD11PadDriver padDriver, SPD11SettingsDriver settingsDriver) {
        super ("Patch","Peter Geirnaert",33,3);
        this.padDriver = padDriver;
        this.settingsDriver = settingsDriver;
        sysexID= "F041**601200";
        deviceIDoffset=2;
        bankNumbers = SPD11_Constants.SPD11_PATCHES;
        patchNumbers=SPD11_Constants.SPD11_PADS;

        singleSysexID="F041**601200";
        singleSize = (0); // (can be 19 or 28) -> set to 0 for 'variable singleSize'.
        
    }
    /**
     * SPD11PatchDriver CheckSum calculation replaces the offset byte<br> 
     * with the result of the formula 128 -(sum % 128)
     * @param p the patch we work with
     * @param start is where the values start adding to the sum
     * @param end  is where the values stops adding to the sum
     * @param offset is where the checksum value is replaced
     */
    //declared in SPD11_Constants.caluclateChecksum(Patch p, int start, int end, int offset)
    /*
    @Override
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
     */
    /**
     * 
     * @return a complete patch containing system and 32 pad messages.
     */
    public Patch createNewPatch(){
        byte[] patchSysex = new byte[(32*28)+19];

        //get a single settings
        Patch settings = settingsDriver.createNewPatch();
        //arraycopy(Object src,  int  srcPos,Object dest, int destPos,int length)
        System.arraycopy(settings.sysex, 0, patchSysex, 0, 19);

        // get a single pad
        Patch pad1 = padDriver.createNewPatch();

        for (int i=0;i<32;i++) {
            pad1.sysex[7] = (byte) (i+1); //+1 because 0 is "settings" and we must start at 0 for the arraycopy offsetvalue calculation
            //arraycopy(Object src,  int  srcPos,Object dest, int destPos,int length)
            System.arraycopy(pad1.sysex, 0, patchSysex, 19+(i * 28), 28);
        }
        Patch patchPatch = new Patch(patchSysex,this);
        return patchPatch;
    }
    /** commented out to test and if I don't override this method, this driver returns a CompletePatch. When I reassign it to a Patch, I can edit the bankPatch in a BankEditor frame that can be printed.
    @Override
    public Patch[] createPatches(SysexMessage[] msgs){
        Patch[] padarray = new Patch[33]; // create a patch array for 32pads+1settings
        //if msgs[0].getMessage()
        padarray[0] = settingsDriver.createNewPatch(msgs[0].getMessage());
        //equals: padarray[0] = new Patch(msgs[0].getData(),settingsDriver);
        for (int i = 1; i < 33; i++) { // 32 times do this: (with each patch)
            padarray[i] = padDriver.createNewPatch(msgs[i].getMessage());
            //padarray[i]= new Patch(msgs[i].getData(),padDriver); // copy the sysex of each message in a new patch with this
            /*
            //check if byte nr8 is zero, set the driver to settingsDriver, else to padDriver.
            //padarray[i].setDriver((padarray[i].sysex[7] == 0x00) ? settingsDriver : padDriver);
            if ((padarray[i].sysex[7] == 0) && padarray[i].sysex.length == 19) {
                padarray[i].setDriver(settingsDriver);
            }
            else {
                padarray[i].setDriver(padDriver);
            }
            
        }
    return padarray;

    }
    */
    

    /**
     * getPatch gets a "Settings" or a "Pad" patch from the bank (known as SPD11patch)
     * @param patch
     * @param patchNum
     * @return the chosen patch
     */
    public Patch getPatch(Patch patch, int patchNum) {
        if (patchNum==0 && patch.sysex[7]==(byte)0){ //it's the settings
            byte[] settings = new byte[19];
            System.arraycopy(patch.sysex, 0, settings, 0, 19);
            return new Patch(settings, settingsDriver);
        }
        else {
            byte[] sysex = new byte[28]; //it's a pad
            System.arraycopy(patch.sysex, 19+(28*(patchNum-1)), sysex, 0, 28);
            return new Patch(sysex, padDriver);
        }
    }
    /**
     * Get the name of the patch at the given number patchNum.
     * @param patch the Patch of the drumkit aka bank
     * @param patchNum the given pad or the settings if patchNum is 0.
     * @return the name of the pad, a description of it's location
     */
    protected String getPatchName(Patch patch, int patchNum) {
        return SPD11_Constants.SPD11_PADS[patchNum];
    }
    /**
     * Get name of the bank i.e. the SPD11 Patch aka drumkit.
     * @see Patch#getName()
     */
    public String getPatchName(Patch bank) {
	// Find a way to return the name of the patch.
	//return "SPD11-Patch";
        int pat = bank.sysex[6];
        return SPD11_Constants.SPD11_PATCHES[pat];
    }
    public void putPatch(Patch bank, Patch single, int patchNum) {
        if ((single.getDriver() == settingsDriver)/**&&(patchNum == 0)*/){
            single.sysex[6] = bank.sysex[6];
            //recalculate the checksum for single with it's new address.
            SPD11_Constants.calculateChecksum(single, 5, 16, 17);
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
                SPD11_Constants.calculateChecksum(single, 5, 25, 26);
                // copy new single to new address in the bank
                System.arraycopy(single.sysex,0,bank.sysex,19+(28*patchNum),28);
            }
        }
    }
    public void setPatchName(Patch bank, int patchNum, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**
     * request a full SPD11patch -> this is a bank of pads + settings so patchNum is ignored
     * @param bankNum is the SPD-11 patch we request (0~63 = patch1~64)
     * @param patchNum
     */
    public void requestPatchDump(int bankNum, int patchNum) {
        singleSize = 19;//first get the settings data 19 bytes long
        settingsDriver.requestPatchDump(bankNum, 0);
        try {
            Thread.sleep(30);  // wait at least 20 milliseconds .
        } catch (Exception e) {
            ErrorMsg.reportStatus(e);
        }
        singleSize = 28;//then get the pads data, 28 bytes long each
        patchNameSize=0;//TODO: use a textlist with names for pads, patches and banks
        for (int i = 0; i < 32; i++) {
            padDriver.requestPatchDump(bankNum, i);
            try {
                Thread.sleep(30);  // wait 30 milliseconds .
            } catch (Exception e) {
                ErrorMsg.reportStatus(e);
            }
        }
    }
    /**
     * Sends an SPD11Patch to a new location, calculating the new<br>
     * checksums with the new value in the addresses of each pad or the settings.<p>
     * @param p
     * @param bankNum is the SPD11 patch
     * @param patchNum is ignored
     * @see Patch#send(int, int)
     */
    public void storePatch(Patch p, int bankNum, int patchNum) {
        //replace the bankNum value of the first part: "settings"
        p.sysex[6]=(byte)bankNum;
        //calculate the new "settings" checksum with the new bankNum .
        SPD11_Constants.calculateChecksum(p,5,16,17);
        //replace banknum values and calculate checkSums of "pad1" to "pad32"
        for(int i=0;i<32;i++){
        p.sysex[25+i*28]=(byte)bankNum;
        //recalculate the new "pad" checksums with the new bankNum .
        SPD11_Constants.calculateChecksum(p,24+i*28,25+i*28,36+i*28);
        }
        //send the new patch
        sendPatch(p);        
    }

    /**
     * Used in <code>SPD11***Converter</code> to find out if
     * @param i is 1 or 0
     * @return if i is 0 returns SPD11PadDriver, if i is 1 SPD11SettingsDriver
     * @TODO put method in SPD11_Constants because CompletePatchDriver also uses this
     */
    Driver getPatchDriver(int i) {
        if(i == 0){
            return padDriver;
        }
        else {
            return settingsDriver;
        }
    }
}
