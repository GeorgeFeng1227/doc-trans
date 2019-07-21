package com.georgefeng.trans.doc_trans;

import java.io.File;

import javax.swing.filechooser.FileFilter;



public class DocFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("doc") ||
                extension.equals("docx")) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }
    

    //The description of this filter
    public String getDescription() {
        return "Just word documents";
    }
    
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}


