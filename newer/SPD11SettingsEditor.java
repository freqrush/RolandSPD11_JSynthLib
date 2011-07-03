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
public class SPD11SettingsEditor extends PatchEditorFrame {
    private Patch patch;
    public SPD11SettingsEditor(Patch patch) {
        super ("SPD11 FX & Pedal settings Editor",patch);
        /**
        int pdNum = 0;
        int ptchNum = patch.sysex[6];
        JPanel fxPane = new JPanel(); // instantiate a new JPanel named lfoPane
        fxPane.setLayout(new GridBagLayout());
        // add ScrollBarWidgets:
        addWidget(fxPane,
                //TODO: finish the code starting from here, I'm gonnan get som sliep nouw...
                new ScrollBarWidget("Fx Time ", //TODO: change label according to the Fx Type.
                patch,0,31,1,
                new ParamModel(patch, 13),
                new SPD11PadParamSender(ptchNum,pdNum,4)),
                3, // gridx
                0, // gridy
                1, // gridwidth
                1, // gridheight
                1); // slidernum
        addWidget(fxPane,new ScrollBarWidget("Fx Level",patch,0,15,0,
                new ParamModel(patch,14),
                new SPD11PadParamSender(ptchNum,pdNum,5)),
                3,1,1,1,2);
        addWidget(fxPane,new ScrollBarWidget("Pedal Level",patch,0,15,0,
                new ParamModel(patch,16),
                new SPD11PadParamSender(ptchNum,pdNum,7)),
                3,2,1,1,3);
        // add CheckBoxWidgets:
        addWidget(fxPane,
                new CheckBoxWidget(
                "Layer On",
                patch,
                new ParamModel(patch, 10),
                new SPD11PadParamSender(ptchNum,pdNum,1)),
                1,1,1,1,4);
        addWidget(fxPane,
                new CheckBoxWidget(
                "FX On",
                patch,
                new ParamModel(patch, 11),
                new SPD11PadParamSender(ptchNum,pdNum,2)),
                2,1,1,1,5);
        // add ComboBoxWidgets:"Bank A/B", "FX Type" and "Pedal Control"
        addWidget(fxPane,
                new ComboBoxWidget(
                "Bank",patch,
                new ParamModel(patch,9),
                new SPD11PadParamSender(ptchNum,pdNum,0),
                new String[] {"A","B"}),
                0,1,1,1,6);
        addWidget(fxPane,
                new ComboBoxWidget(
                "FxType",patch,
                new ParamModel(patch,12),
                new SPD11PadParamSender(ptchNum,pdNum,3),
                SPD11_Constants.SPD11_FXTYPES),
                0,0,3,1,6);
        addWidget(fxPane,
                new ComboBoxWidget(
                "PedalControl",patch,
                new ParamModel(patch,15),
                new SPD11PadParamSender(ptchNum,pdNum,6),
                SPD11_Constants.SPD11_PEDALCONTROL),
                0,2,3,1,6);
        scrollPane.add(fxPane);
        */
        scrollPane.setLayout(new GridLayout(0,1));
        addFxPane(patch,scrollPane);
        this.patch = patch;
        pack();
    }
    /**
     * adds an FxPane to a given JPanel for a given Settingspatch, BankPatch or CompletePatch 
     * @param patch the patch, 
     * @param panel
     */
    protected void addFxPane (Patch patch, JPanel panel) {
        int pdNum = 0;
        int ptchNum = patch.sysex[6];
        JPanel fxPane = new JPanel(); // instantiate a new JPanel named lfoPane
        fxPane.setLayout(new GridBagLayout());
        // add ScrollBarWidgets:
        addWidget(fxPane,
                new ScrollBarWidget(SPD11_Constants.FX_PARAMS[patch.sysex[12]] ,//"Fx Time ", //TODO: change label according to the Fx Type.
                patch,0,31,1,
                new ParamModel(patch, 13),
                new SPD11PadParamSender(ptchNum,pdNum,4)),
                3, // gridx
                0, // gridy
                1, // gridwidth
                1, // gridheight
                1); // slidernum
        addWidget(fxPane,new ScrollBarWidget("Fx Level",patch,0,15,0,
                new ParamModel(patch,14),
                new SPD11PadParamSender(ptchNum,pdNum,5)),
                3,1,1,1,2);
        addWidget(fxPane,new ScrollBarWidget("Pedal Level",patch,0,15,0,
                new ParamModel(patch,16),
                new SPD11PadParamSender(ptchNum,pdNum,7)),
                3,2,1,1,3);
        // add CheckBoxWidgets:
        addWidget(fxPane,
                new CheckBoxWidget(
                "Layer On",
                patch,
                new ParamModel(patch, 10),
                new SPD11PadParamSender(ptchNum,pdNum,1)),
                1,1,1,1,4);
        addWidget(fxPane,
                new CheckBoxWidget(
                "FX On",
                patch,
                new ParamModel(patch, 11),
                new SPD11PadParamSender(ptchNum,pdNum,2)),
                2,1,1,1,5);
        // add ComboBoxWidgets:"Bank A/B", "FX Type" and "Pedal Control"
        addWidget(fxPane,
                new ComboBoxWidget(
                "Bank",patch,
                new ParamModel(patch,9),
                new SPD11PadParamSender(ptchNum,pdNum,0),
                new String[] {"A","B"}),
                0,1,1,1,6);
        addWidget(fxPane,
                new ComboBoxWidget(
                "FxType",patch,
                new ParamModel(patch,12),
                new SPD11PadParamSender(ptchNum,pdNum,3),
                SPD11_Constants.SPD11_FXTYPES),
                0,0,3,1,6);
        addWidget(fxPane,
                new ComboBoxWidget(
                "PedalControl",patch,
                new ParamModel(patch,15),
                new SPD11PadParamSender(ptchNum,pdNum,6),
                SPD11_Constants.SPD11_PEDALCONTROL),
                0,2,3,1,6);
        panel.add(fxPane);
    }
}
