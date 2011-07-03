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
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author peter
 */
public class SPD11ChainEditor extends PatchEditorFrame {
    private String[] banks = {"A","B","C","D"};
    private String[] steps = DriverUtil.generateNumbers(1, 16, "##");
    private int header = 9;
    public SPD11ChainEditor(Patch p) {
        super("Roland SPD11 Chain Setup Editor", p);
        scrollPane.setLayout(new GridLayout(0,1));
        for(int i=0;i<4;i++){
            addChainPane(p,i);
        }
    }
    /**
     * Adds a new row to scrollPane
     * @param p the Chain patch
     * @param bank the number of the row: A, B, C, D = 0,1,2,3
     */
    protected void addChainPane(Patch p, int bank) {
        JPanel chainPane = new JPanel();
        chainPane.setLayout(new GridBagLayout());
        chainPane.add(new JLabel("Patch Chain " + banks[bank]));
        for (int i=0;i<16;i++) {
        addWidget (chainPane,
                new ComboBoxWidget(steps[i],p,
                    new ChainModel(p,header+(bank*16)+i),
                    new ChainSender(p,(bank*16)+i),SPD11_Constants.SPD11_PATCHES1),
                i+1,0,1,1,0);
        }
        scrollPane.add(chainPane);
    }
}
class ChainModel implements SysexWidget.IParamModel {
    /** <code>Patch</code> data. */
    protected Patch patch;
    /** Offset of the data for which this model is. */
    protected int ofs;

    public ChainModel(Patch p, int ofs) {
        this.patch = p;
        this.ofs = ofs;
    }
    public void set(int value) {
        patch.sysex[ofs] = (byte)value;
    }
    public int get() {
        return patch.sysex[ofs];
    }
}
class ChainSender extends SysexSender {
    Patch patch;
    byte[] sysx;
    int ofset;
    public ChainSender(Patch p, int offset){
        //super(p.sysex.toString()); //old way was sending complete patch whenever a step changed
        super("F041@@601202000000**00F7");
        this.patch = p;
        this.ofset = offset;
        this.sysx = patch.sysex;
    }
    public int calcChecksum(byte[] sysX) {
        int sum =0;
        for (int i=9;i<sysX.length-2;i++) {
            sum+= sysX[i];
        }
        return 128-(sum % 128);
    }
    public byte[] generate(int value) {
        sysx[ofset] = (byte) value;
        sysx[sysx.length-2] = (byte) calcChecksum(sysx);
        return sysx;
    }
}