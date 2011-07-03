/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;
import core.*;
/**
 * Decay values are from -31 to 31,
 * they are represented by their 2's complement
 * @author peter
 */
public class SPD11PadDecaySender extends SysexSender{
    int patch;
    int pad;
    int offset;

    /**
     * Uses SysexSender(String) constructor
     * @param pad the pad this sender instance works for. Used for <code>calculateChecksum</code>
     * @param patch same as param pad
     * @param offset the location of the value on the SPD11's memory,
     * aka "parameter address".
     *
     */
    public SPD11PadDecaySender(int patch, int pad, int offset) {
     //offset: 0 1 2 3 4 5 6 7 8 9 10
        super("F041@@601200000000**00F7"); //calls SysexSender(String)
        this.patch = patch;
        this.pad = pad;
        this.offset = offset;


    }
    public byte[] generate(int value) {
        if (value < 31){
            byte[] syse = super.generate(value+65);
            syse[6] = (byte) patch;
            syse[7] = (byte) pad;
            syse[8] = (byte) offset;
            syse[10] = (byte) SPD11_Constants.calculateChecksum(syse);
            try {
                Thread.sleep(22);  // wait more than 20 milliseconds internal.
            } catch (Exception e) {
                ErrorMsg.reportStatus(e);
            }
            return syse;
            }
        else{
            byte[] syse = super.generate(value-31);
            syse[6] = (byte) patch;
            syse[7] = (byte) pad;
            syse[8] = (byte) offset;
            syse[10] = (byte) SPD11_Constants.calculateChecksum(syse);
            try {
                Thread.sleep(22);  // wait more than 20 milliseconds internal.
            } catch (Exception e) {
                ErrorMsg.reportStatus(e);
            }
            return syse;
            }
    }

}
