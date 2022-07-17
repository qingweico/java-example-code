package coretech2.secure.classloader;

import java.awt.*;

/**
 * @date 2022/7/14
 */
class GBC extends GridBagConstraints {
    /**
     * Constructs a GBC with a given grid_x and grid_y position and all other grid
     * bag constraint values set to the default
     * @param gridx the gridx position
     * @param gridy the gridy position
     */

    public GBC(int gridx, int gridy){
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * Constructs a GBC with given gridx, gridy, grid width, grid height and all
     * other grid bag constraint values set to the default.
     * @param gridx the gridx position
     * @param gridy the gridy position
     * @param gridwidth the cell span in x-direction
     * @param gridheight the cell span in y-direction
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight){
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    /**
     * Sets the anchor.
     * @param anchor the anchor value
     * @return this object for further modification
     */
    public GBC setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }

    /**
     * Sets the fill direction.
     * @param fill the fill direction
     * @return this object for further modification
     */
    public GBC setFill(int fill){
        this.fill = fill;
        return this;
    }

    /**
     * Sets the cell weights.
     * @param weightx the cell weigh in x-direction
     * @param weighty the cell weight in y-direction
     * @return this object for further modification
     */
    public GBC setWeight(double weightx, double weighty){
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * Sets the insets of this cell.
     * @param top the spacing to use on top
     * @param left the spacing to use to the left
     * @param bottom the spacing to use on the bottom
     * @param right the spacing to use to the right
     * @return this object for further modification
     */
    public GBC setInsets(int top, int left, int bottom, int right){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBC setIpad(int ipadx, int ipady){
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}