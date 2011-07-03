/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;

import core.*;

/**
 *
 * @author peter
 */
public class SPD11Sender extends SysexSender{
    private byte[] sysex = new byte[12];
    /**
     *
     * @param p
     * @param param
     */
    public SPD11Sender(Patch p,int param) {
        sysex = p.sysex;

        sysex[5]=(byte)0X00;
        sysex[8]=(byte)param;
        //sysex[9]=(byte)(value);
        //sysex[10]=(byte)checksum;
        sysex[11]=(byte)0XF7;

    }
    /**
     *
     * @param param
     */
    public SPD11Sender(int param){
        
    }
    /**
     *
     * @param param
     * @param nibbled
     */
    public SPD11Sender(int param, boolean nibbled){

    }
    public byte[] generate(int value){
        sysex[3] = (byte)(channel - 1);
        sysex[9]=(byte)value;
        int remainder = (sysex[5]+sysex[6]+sysex[7]+sysex[8]+sysex[9]) % 0x80;
        sysex[sysex.length-2]=(byte)(0x80-((remainder == 0x0) ? 0x80 : remainder));
        //sysex[sysex.length-2]=(byte)(0x80-((((sysex[5]+sysex[6]+sysex[7]+sysex[8]+sysex[9])%0x80)==0) ? 0x80 : (0x80-(((sysex[5]+sysex[6]+sysex[7]+sysex[8]+sysex[9])%0x80)))));
        return sysex;
    }
}