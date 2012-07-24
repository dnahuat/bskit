package com.baco.ui.util;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileFilter;

/**
 *
 * 
 *
 * CHANGELOG
 * ----------
 * 2011-03-23 : Formato y estilo
 *
 */
public class BSCustomFileFiltered extends FileFilter {

    private String description;
    
    private List<String> extentions;
    
    private static Pattern extentionExtractor;
    
    static {
        extentionExtractor = Pattern.compile("\\.([a-zA-Z0-9]+)$");
    }

    public BSCustomFileFiltered(List<String> extentions, String description) {
        this.description = description;
        this.extentions = extentions;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;

        } else {
            Matcher matcher = extentionExtractor.matcher(f.getName());

            if (matcher.find()) {
                String extention = matcher.group(1).toLowerCase();
                return extentions.contains(extention);

            } else {
                return false;
            }
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
