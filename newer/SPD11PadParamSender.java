/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;
import core.*;

/**
 *
 * @author peter
 * to set a parameter, use this sysex message:
 * F0 41 09 60 12 00 "patch" "pad" "parameter" "value" "checksum" F7
 */
public class SPD11PadParamSender extends SysexSender {
    // String dataSet = "F041@@601200" + "000000**00F7";
    int patch;
    int pad;
    int offset;

    /**Uses SysexSender(String) constructor
     * @param pad the pad this sender instance works for. Used for <code>calculateChecksum</code>
     * @param patch same as param pad
     * @param offset the location of the value on the SPD11's memory,
     * aka "parameter address".
     * 
     */
    public SPD11PadParamSender(int patch, int pad, int offset) {
     //offset: 0 1 2 3 4 5 6 7 8 9 10
        super("F041@@601200000000**00F7"); //calls SysexSender(String)
        this.patch = patch;
        this.pad = pad;
        this.offset = offset;


    }
    public byte[] generate(int value) {
        byte[] syse = super.generate(value);
        // ofsets:  0  1  2  3  4  5  6  7  8  9  10 11
        // returns "F0 41 @@ 60 12 00 00 00 00 ** 00 F7" with the @@ and ** replaced.
        syse[6] = (byte) patch;
        syse[7] = (byte) pad;
        syse[8] = (byte) offset;
        int remainder = (syse[5]+syse[6]+syse[7]+syse[8]+syse[9]) % 0x80;
        //SPD11 doesn't accept 0x80 as checksum so when all values are 0, checksum must be 0 too.
        syse[syse.length-2]=(byte)(0x80-((remainder == 0x0) ? 0x80 : remainder));
        //syse[10] = (byte) SPD11_Constants.calculateChecksum(syse);
        try {
            Thread.sleep(22);  // wait more than 20 milliseconds internal.
        } catch (Exception e) {
            ErrorMsg.reportStatus(e);
        }
        return syse;
    }

        /*
    public PadParamSender(int patch, int pad, int parameter, Boolean nibbled){
        if (nibbled == false) {
            byte b[] = {
                //0exclusive,1manufId   2Device-ID  3 modelId   4 Data set  5 addres
                (byte)0xF0, (byte)0x41, (byte)0x00, (byte)0x60, (byte)0x12, (byte)0x00,
                //6 patch      7  pad    8 param      9 value   10 checksum
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                //11  EOX
                (byte)0xF7 };
            b[2] = (byte)(channel - 1);
            b[6] = (byte)patch;
            b[7] = (byte)pad;
            b[8] = (byte)parameter;
        }
        else {
            byte b[] = {
                (byte)0xF0, (byte)0x41, (byte)0x00, (byte)0x60, (byte)0x12, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0xF7 };
            b[2] = (byte)(channel - 1); //this
            b[6] = (byte)patch; // is
            b[7] = (byte)pad; //nothing
            b[8] = (byte)parameter; //yet
        }

    };
     */
/*
    @Override
    public void send(IPatchDriver driver, int value){
        channel = (byte) driver.getDevice().getDeviceID();
        byte[] sysex = generate(value);
        SysexMessage m = new SysexMessage();
        try {
            m.setMessage(sysex, sysex.length);
            driver.send(m);
        } catch (InvalidMidiDataException e) {
            ErrorMsg.reportStatus(e);
        }
    }
*/

}
