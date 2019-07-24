package com.georgefeng.trans.doc_trans;

import java.beans.*;

//NOT USING IT IN MAIN PROGRAM
//Achieved by using process method
public class FolderTransProg {
    private int proF = 0;
    private PropertyChangeSupport mPcs =
        new PropertyChangeSupport(this);
    
    public int getFProgress() {
        return proF;
    }
    
    public void setFProgress(int pf) {
        int oldproF = proF;
        proF = pf;
        mPcs.firePropertyChange("progressF",
                                   oldproF, pf);
    }

    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
}
