package synthdrivers.RolandSPD11;

import core.Driver;
import core.SysexHandler;

public class SPD11ChainDriver extends Driver {
    final static SysexHandler SYS_REQ = new SysexHandler
    //0  1  2  3  4  5  6  7  8  9  10 11 12     13     14
    ("F0 41 @@ 60 11 02 00 00 00 00 00 00 40 3E F7");
    public SPD11ChainDriver(){
        super ("Chain", "Peter Geirnaert");
        //offset numbers: 0 1 2 3 4 5
        sysexID         = "F041**601202";
        deviceIDoffset  = 2 ;
        bankNumbers     = new String[] {"Chain Setup"};
        patchNumbers    = new String[] {"Off Topic"};
        patchSize    = 75;
        patchNameSize =0;
    }
    public void requestPatchDump(int bankNum, int patchNum) {
        final SysexHandler.NameValue[] nameValues = { };
        send(SYS_REQ.toSysexMessage(getDeviceID(), nameValues));
    }

}
