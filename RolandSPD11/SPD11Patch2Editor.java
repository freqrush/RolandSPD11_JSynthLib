/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;
import core.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author peter
 */
public class SPD11Patch2Editor extends PatchEditorFrame
{
    //private Patch patch;
    public SPD11Patch2Editor(Patch patch) {
        super ("Roland SPD11 Patch Editor",patch);
        // ptchNum will stay the same for the whole patch
        int ptchNum = patch.sysex[6];
        int pdNum = 0; // padNum will go up to 32
        // start with FxPane, copied from settingsEditor.
        scrollPane.setLayout(new GridLayout(0,1));
        // SPD11SettingsEditor.addFxPane(patch,scrollPane);
        //this.patch = patch;
        pack();
        //**
        JPanel fxPane = new JPanel(); // instantiate a new JPanel named lfoPane
        fxPane.setLayout(new GridBagLayout());
        // add ScrollBarWidgets:
        addWidget(fxPane,
                new ScrollBarWidget("Fx Time ", //TODO: change label according to the Fx Type.
                patch,0,31,1,
                new ParamModel(patch, 13),
                new SPD11PadParamSender(ptchNum,pdNum,4)),
                3,0,1,1,1); // slidernum
        addWidget(fxPane,new ScrollBarWidget("Fx Level",patch,0,15,0,
                new ParamModel(patch,14),
                new SPD11PadParamSender(ptchNum,pdNum,5)),
                3,1,1,1,2);
        addWidget(fxPane,new ScrollBarWidget("Pedal Level",patch,0,15,0,
                new ParamModel(patch,16),
                new SPD11PadParamSender(ptchNum,pdNum,7)),
                4,1,1,1,3);
        // add CheckBoxWidgets:
        addWidget(fxPane,
                new CheckBoxWidget("Layer On",patch,new ParamModel(patch, 10),
                new SPD11PadParamSender(ptchNum,pdNum,1)),1,1,1,1,4);
        addWidget(fxPane,
                new CheckBoxWidget("FX On",patch,new ParamModel(patch, 11),
                new SPD11PadParamSender(ptchNum,pdNum,2)),2,1,1,1,5);
        // add ComboBoxWidgets:"Bank A/B", "FX Type" and "Pedal Control"
        addWidget(fxPane,
                new ComboBoxWidget("Bank",patch,new ParamModel(patch,9),
                new SPD11PadParamSender(ptchNum,pdNum,0),
                new String[] {"A","B"}),0,1,1,1,6);
        addWidget(fxPane,
                new ComboBoxWidget("FxType",patch,new ParamModel(patch,12),
                new SPD11PadParamSender(ptchNum,pdNum,3),
                SPD11_Constants.SPD11_FXTYPES),0,0,3,1,6);
        addWidget(fxPane,
                new ComboBoxWidget(
                "PedalControl",patch,new ParamModel(patch,15),
                new SPD11PadParamSender(ptchNum,pdNum,6),
                SPD11_Constants.SPD11_PEDALCONTROL),4,0,1,1,6);
        //scrollPane.add(fxPane);
        // now the panes for each 8 pads, 32 in total
        JPanel partOne = new JPanel();
        partOne.setLayout(new GridLayout(0,1));
        for (int i=0;i<8;i++) {
            JPanel padPane = new JPanel();
            padPane.setLayout(new GridBagLayout());
            addPadPane(patch,padPane,i);
            partOne.add(padPane);
        }
        JPanel partTwo = new JPanel();
        partTwo.setLayout(new GridLayout(0,1));
        for (int i=0;i<8;i++) {
            JPanel padPane = new JPanel();
            padPane.setLayout(new GridBagLayout());
            addPadPane(patch,padPane,i+8);
            partTwo.add(padPane);
        }
        JPanel partThree = new JPanel();
        partThree.setLayout(new GridLayout(0,1));
        for (int i=0;i<8;i++) {
            JPanel padPane = new JPanel();
            padPane.setLayout(new GridBagLayout());
            addPadPane(patch,padPane,i+16);
            partThree.add(padPane);
        }
        JPanel partFour = new JPanel();
        partFour.setLayout(new GridLayout(0,1));
        for (int i=0;i<8;i++) {
            JPanel padPane = new JPanel();
            padPane.setLayout(new GridBagLayout());
            addPadPane(patch,padPane,i+24);
            partFour.add(padPane);
        }
        JTabbedPane mixer = new JTabbedPane();
        mixer.addTab("Settings", fxPane);
        mixer.addTab("1 to 8",partOne);
        mixer.addTab("9 to 16",partTwo);
        mixer.addTab("17 to 24",partThree);
        mixer.addTab("25 to 32",partFour);
        scrollPane.add(mixer);
         //*/
        pack();
    }
    /**
     * adds a PadPane to a given JPanel for a given CompletePatch at a given offset.
     * @param cPatch the patch,
     * @param pane
     * @param padNum a value from 0 to 31
     */
    protected void addPadPane (Patch cPatch, JPanel pane, int padNum) {
        //temporary hack to get the editing working, but the changes don't stay in JSynthLib because we create a new padpatch.
        //TODO: rewrite SPD11PadModel/Senders to work with a CompletePatch
        byte[] ped = new byte[28];
        System.arraycopy(cPatch.sysex,19+(28*padNum),ped,0,28);
        Patch pad = new Patch(ped);
        int pdNum = cPatch.sysex[19+(28*padNum)+7];//ped[7];
        int ptchNum = ped[6]; // I can use this to check if we're working with the right pad/settings, but remove all that ped stuff
        gbc.weightx = 0;
        addWidget(pane,new ComboBoxWidget("Inst",pad,new SPD11PadModel(cPatch,19+(28*padNum)+9,true),new SPD11PadInstSender(ptchNum,pdNum), SPD11_Constants.INSTRUMENTS), //Inst
        // gridx, gridy, gridwidth, gridheight, slidernum
            0, 0, 1, 1, 0);
        addWidget(pane,new ComboBoxWidget("Curv",pad,new SPD11PadModel(pad, 15),new SPD11PadParamSender(ptchNum,pdNum,6), SPD11_Constants.CURVES),
            0, 1, 1, 1, 0);
        addWidget(pane,new KnobWidget("Level",pad,0,15,0, new SPD11PadModel(pad, 11),new SPD11PadParamSender(ptchNum,pdNum,2)),
            1, 0, 1, 2, 1+padNum);
        addWidget(pane,new KnobWidget("Pitch",pad,0,48,-24,new SPD11PadModel(pad,12),new SPD11PadParamSender(ptchNum,pdNum,3)),
            2, 0, 1, 2, 0);
        addWidget(pane,new KnobWidget("Decay",pad,0,62,-31,new SPD11PadDecayModel(pad,13),new SPD11PadDecaySender(ptchNum,pdNum,4)),
            3, 0, 1, 2, 0);
        addWidget(pane,new KnobLookupWidget("Pan", pad, new SPD11PadModel(pad,14),new SPD11PadParamSender(ptchNum,pdNum,5),SPD11_Constants.PANPOSITIONS),
            4, 0, 1, 2, 33+padNum);
        addWidget(pane,new KnobWidget("FxSend",pad,0,15,0,new SPD11PadModel(pad,16),new SPD11PadParamSender(ptchNum,pdNum,7)),
            5, 0, 1, 2, 65+padNum);
        addWidget(pane,new ComboBoxWidget("MidiNote",pad,new SPD11PadModel(pad,18),new SPD11PadParamSender(ptchNum,pdNum,9), SPD11_Constants.NOTENUMBERS),
            6, 0, 1, 1, 0);
        addWidget(pane,new ComboBoxWidget("MidiProg",pad,new SPD11PadModel(pad,24),new SPD11PadParamSender(ptchNum,pdNum,15), SPD11_Constants.PROGNUMBERS),
            6, 1, 1, 1, 0);
        addWidget(pane,new ComboBoxWidget("MidiCurv",pad,new SPD11PadModel(pad,22),new SPD11PadParamSender(ptchNum,pdNum,13), SPD11_Constants.CURVES),
            7, 0, 1, 1, 0);
        addWidget(pane,new ComboBoxWidget("MidiTxCh",pad,new SPD11PadModel(pad,17),new SPD11PadParamSender(ptchNum,pdNum,8), SPD11_Constants.CHANNELNUMBERS),
            7, 1, 1, 1, 0);
        addWidget(pane,new KnobWidget("MidiGtTime",pad,0,40,0,new SPD11PadModel(pad,20),new SPD11PadParamSender(ptchNum,pdNum,11)),
            8, 0, 1, 2, 0);
        addWidget(pane,new KnobWidget("MidiPan",pad,0,16,-8,new SPD11PadModel(pad,21),new SPD11PadParamSender(ptchNum,pdNum,12)),
            9, 0, 1, 2, 0);
        addWidget(pane,new KnobWidget("MidiSens",pad,0,14,0,new SPD11PadModel(pad,23),new SPD11PadParamSender(ptchNum,pdNum,14)),
            10, 0, 1, 2, 0);
        gbc.gridwidth = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.NONE;  //
        gbc.anchor = GridBagConstraints.CENTER; //
        pack();
    }

}
