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

class SPD11PadDecayModel implements SysexWidget.IParamModel  {
    /** <code>Patch</code> data. */
    protected Patch patch;
    /** Offset of the data for which this model is. */
    protected int ofs;
    /**
     * Creates a new <code>SPD11PadDecayModel</code> instance.
     *
     * @param patch a <code>Patch</code> value
     * @param offset an offset in <code>patch.sysex</code>.
     */
    SPD11PadDecayModel(Patch pad, int param) {
        this.patch = pad;
        this.ofs = param;
    }
    // SysexWidget.IParamModel interface methods
    /** Set a parameter value <code>value</code>. */
    public void set(int value) {
        if (value < 31){
            patch.sysex[ofs]  = (byte) (value + 97);
        }
        else {
            patch.sysex[ofs]  = (byte) (value - 31);
        }
    }

    /** Get a parameter value. */
    public int get() {
        int val = patch.sysex[ofs];
        // -31~-1 = 65~127 on SPD11, 0~31 is value from sysexWidget
        if (val > 96) {
            val = val - 97;
            return val;
        }
        // 0~31 = 0~31 on SPD11, 32~
        else {
            val = val + 31;
            return val;
        }
    }
}
