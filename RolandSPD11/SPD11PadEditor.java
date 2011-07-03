/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;

import core.*;
// import javax.swing.*;
import java.awt.*;
/**
 *
 * @author peter
 */
public class SPD11PadEditor extends PatchEditorFrame {
    /**
     * editor for pad data
     * @param pad a SinglePatch describing one drumpad
     */
    public SPD11PadEditor(Patch pad){
        super("SPD11 Pad editor", pad);
        int pdNum = pad.sysex[7];
        int ptchNum = pad.sysex[6];
        gbc.weightx = 0;
        addWidget(scrollPane,new ComboBoxWidget("Inst",pad,new SPD11PadModel(pad,9,true),new SPD11PadInstSender(ptchNum,pdNum), SPD11_Constants.INSTRUMENTS), //Inst
        // gridx, gridy, gridwidth, gridheight, slidernum
            0, 0, 1, 1, 1);
        addWidget(scrollPane,new ComboBoxWidget("Curv",pad,new SPD11PadModel(pad, 15),new SPD11PadParamSender(ptchNum,pdNum,6), SPD11_Constants.CURVES),
            0, 1, 1, 1, 2);
        addWidget(scrollPane,new KnobWidget("Level",pad,0,15,0, new SPD11PadModel(pad, 11),new SPD11PadParamSender(ptchNum,pdNum,2)),
            1, 0, 1, 2, 3);
        addWidget(scrollPane,new KnobWidget("Pitch",pad,0,48,-24,new SPD11PadModel(pad,12),new SPD11PadParamSender(ptchNum,pdNum,3)),
            2, 0, 1, 2, 4);
        addWidget(scrollPane,new KnobWidget("Decay",pad,0,62,-31,new SPD11PadDecayModel(pad,13),new SPD11PadDecaySender(ptchNum,pdNum,4)),
            3, 0, 1, 2, 5);
        addWidget(scrollPane,new KnobLookupWidget("Pan", pad, new SPD11PadModel(pad,14),new SPD11PadParamSender(ptchNum,pdNum,5),SPD11_Constants.PANPOSITIONS),
            4, 0, 1, 2, 6);
        addWidget(scrollPane,new KnobWidget("FxSend",pad,0,15,0,new SPD11PadModel(pad,16),new SPD11PadParamSender(ptchNum,pdNum,7)),
            5, 0, 1, 2, 7);
        addWidget(scrollPane,new ComboBoxWidget("MidiNote",pad,new SPD11PadModel(pad,18),new SPD11PadParamSender(ptchNum,pdNum,9), SPD11_Constants.NOTENUMBERS),
            6, 0, 1, 1, 8);
        addWidget(scrollPane,new ComboBoxWidget("MidiProg",pad,new SPD11PadModel(pad,24),new SPD11PadParamSender(ptchNum,pdNum,15), SPD11_Constants.PROGNUMBERS),
            6, 1, 1, 1, 9);
        addWidget(scrollPane,new ComboBoxWidget("MidiCurv",pad,new SPD11PadModel(pad,22),new SPD11PadParamSender(ptchNum,pdNum,13), SPD11_Constants.CURVES),
            7, 0, 1, 1, 10);
        addWidget(scrollPane,new ComboBoxWidget("MidiTxCh",pad,new SPD11PadModel(pad,17),new SPD11PadParamSender(ptchNum,pdNum,8), SPD11_Constants.CHANNELNUMBERS),
            7, 1, 1, 1, 11);
        addWidget(scrollPane,new KnobWidget("MidiGtTime",pad,0,40,0,new SPD11PadModel(pad,20),new SPD11PadParamSender(ptchNum,pdNum,11)),
            8, 0, 1, 2, 12);
        addWidget(scrollPane,new KnobWidget("MidiPan",pad,0,16,-8,new SPD11PadModel(pad,21),new SPD11PadParamSender(ptchNum,pdNum,12)),
            9, 0, 1, 2, 13);
        addWidget(scrollPane,new KnobWidget("MidiSens",pad,0,14,0,new SPD11PadModel(pad,23),new SPD11PadParamSender(ptchNum,pdNum,14)),
            10, 0, 1, 2, 14);
        gbc.gridwidth = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.NONE;  //
        gbc.anchor = GridBagConstraints.CENTER; //
        pack();
    }


}

class SPD11PadModel extends ParamModel {
    /** true for instrument data */
    private boolean nibbled;

    /**
     * Creates a new <code>SPD11PadModel</code> instance.
     *
     * @param patch a <code>Patch</code> value
     * @param param offset address in pad parameters.
     * @param nibbled true if nibbled (2 byte) data
     */
    SPD11PadModel(Patch pad, int param, boolean nibbled) {
	super(pad, param);
	this.nibbled = nibbled; // must be true for instrument name (name of sound)
    }

    /**
     * Creates a new <code>SPD11PadModel</code> instance for 1 byte data.
     *
     * @param p a <code>Patch</code> value
     * @param param offset address in pad parameters.
     */
    SPD11PadModel(Patch pad, int param) {
	this(pad, param, false);
    }



    /**
     * Set data in sysex byte array.
     *
     * @param d an <code>int</code> value
     */
    public void set(int d) {
	ErrorMsg.reportStatus("SPD11PadModel.set(): d =  " + d + " :" + nibbled);
	ErrorMsg.reportStatus("SPD11PadModel.set(): ofs =  " + ofs
			      + "(0x" + Integer.toHexString(ofs) + ")");
	if (nibbled) {
	    patch.sysex[ofs]     = (byte) (d / 16);
	    patch.sysex[ofs + 1] = (byte) (d % 16);
            // calculateChecksum(patch); // ??
	} else {
	    patch.sysex[ofs] = (byte) d;
	}
	//ErrorMsg.reportStatus(patch.sysex);
    }

    /**
     * Get data from sysex byte array.
     *
     * @return an <code>int</code> value
     */
    public int get() {
	if (nibbled) {
	    int d = (((patch.sysex[ofs])*16)+(patch.sysex[ofs + 1]));
	    //*/
	      ErrorMsg.reportStatus("SPD11PadModel.get(): " + d);
	      ErrorMsg.reportStatus("SPD11PadModel.get(): ofs =  " + ofs
	      + "(0x" + Integer.toHexString(ofs) + ")");
	    //*/
	    return d;
	} else {
	    return patch.sysex[ofs];
	}
    }
}