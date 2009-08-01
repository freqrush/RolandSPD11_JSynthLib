package synthdrivers.RolandSPD11;
import core.*;
 
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.event.*;

import synthdrivers.RolandSPD11.PadInfo;
import synthdrivers.RolandSPD11.RolandSPD11Device;
import core.CheckBoxWidget;
import core.ComboBoxWidget;
import core.ErrorMsg;
import core.KnobLookupWidget;
import core.ParamModel;
import core.Patch;
import core.PatchEditorFrame;
import core.PatchNameWidget;
import core.ScrollBarLookupWidget;
import core.ScrollBarWidget;
import core.SysexWidget;
import core.TreeWidget;
import core.VertScrollBarWidget;

final class SPD11PadEditor extends PatchEditorFrame
{  
    /** selected pad */
    private int pad;  
    private final PadInfo [] padList;
    /** TreeWidget for instruction tree */
    private TreeWidget treeWidget;
    /** slider number */
    private int snum = 0;
    /**
     * Creates a new <code>SPD11PatchEditor</code> instance.
     *
     * @param patch a <code>Patch</code> value
     */
  public SPD11PadEditor(Patch patch)
  {
    super ("Roland SPD11 Pad Editor",patch);
    
    // defined here since padList is used by TD6PadModel()
    padList = ((RolandSPD11Device) patch.getDevice()).activePadInfo();
    
  JTabbedPane patchPane = new JTabbedPane();
       JPanel ptchstPane = new JPanel();
       ptchstPane.setLayout(new GridBagLayout());
     patchPane.addTab("PatchSettings",ptchstPane);
       addWidget(ptchstPane,new ScrollBarWidget(" Fx Time", //the label
        patch, //always "patch"
        0,31, //min & max values for fader
        0, //offset value
        new ParamModel(patch,100),
        new VcedSender(21)),
        1,1, // horizontal/vertical location
        3,1, //horizontal/vertical size
        1); //unique fader number
  //     addWidget...
       JTabbedPane padPane = new JTabbedPane();
     patchPane.addTab("PadSettings",padPane);
       JPanel pds1to8Pane = new JPanel();
       padPane.addTab("1>8",pds1to8Pane);
     
  }
}
class VcedSender extends SysexSender
{
    public VcedSender(int param)
    {
        
    }
}
class AcedSender extends SysexSender
{
    public AcedSender(int param)
    {
        
    }
}