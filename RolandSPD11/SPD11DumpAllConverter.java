/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;
import core.*;
//import javax.sound.midi.SysexMessage;
/**
 *
 * @author peter
 */
public class SPD11DumpAllConverter extends Converter {
    protected SPD11Patch2Driver patch2Driver;
    protected SPD11ChainDriver chainDriver;
    protected SPD11SystemDriver systemDriver;
    private static final int NR_OF_PATCHBANKS = 64;
    private static final int PADSIZE = 28;
    private static final int SETTINGSSIZE = 19;
    private static final int PATCHBANKSIZE = (32*PADSIZE)+SETTINGSSIZE;
    private static final int CHAINSIZE = 39;
    private static final int SYSTEMSIZE = 75;


    public SPD11DumpAllConverter(){
        super("Dump All Converter", "Peter Geirnaert");
        sysexID = "F041**601200000000";
        patch2Driver = new SPD11Patch2Driver();
        chainDriver = new SPD11ChainDriver();
        systemDriver = new SPD11SystemDriver();
    }
    public SPD11DumpAllConverter(SPD11Patch2Driver patch2Driver, SPD11ChainDriver chainDriver, SPD11SystemDriver systemDriver){
        super("Dump All Converter", "Peter Geirnaert");
        sysexID = "F041**601200000000";
        this.patch2Driver = patch2Driver;
        this.chainDriver = chainDriver;
        this.systemDriver = systemDriver;
    }
    public Patch[] extractPatch(Patch p) {
        // get all bytes from the (64 * ((32*pads)+settings)) + chain + system
        byte[] msgs = p.getByteArray(); 
        // create a Patch[] pa to hold the created Patches (2*Single and 64*Bank)
        Patch[] pa = new Patch[66];
        // do 64 times this:
        for (int i=0;i<NR_OF_PATCHBANKS;i++) {
            //create a new byte array for a CompletePatch
            byte[] ptchbnk = new byte[PATCHBANKSIZE];
            //copy the current 915 bytes to this byte array
            System.arraycopy(msgs,i*PATCHBANKSIZE,ptchbnk,0,PATCHBANKSIZE);
            // create a new CompletePatch with these bytes and put it in pa at the current position
            pa[i] = new Patch(ptchbnk,patch2Driver);
            // increase i with 1 or go on if i is already 63
        }
        // all CompletePatches are created
        // now create the chain patch byte array chn
        byte[] chn = new byte[CHAINSIZE];
        // copy the chain bytes into chn
        System.arraycopy(msgs,NR_OF_PATCHBANKS*PATCHBANKSIZE,chn,0,CHAINSIZE);
        // create a new chain patch with these bytes and put it in the patcharray
        pa[64] = new Patch(chn,chainDriver);
        //now create the system byte array sstm
        byte[]sstm = new byte[SYSTEMSIZE];
        // copy the system bytes into sstm
        System.arraycopy(msgs,(NR_OF_PATCHBANKS*PATCHBANKSIZE)+CHAINSIZE,sstm,0,SYSTEMSIZE);
        // create a new system patch with these bytes and put it in the patcharray
        pa[65] = new Patch(sstm,systemDriver);
        // return the full array of (64*CompletePatch)+Chain+System
        return pa;
    }
    // If extractPatch returns an array of Patches whose drivers are set
    // properly, override this by;
    public IPatch[] createPatches(byte[] sysex) {
        return extractPatch(new Patch(sysex, this));
    }
}
