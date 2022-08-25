package coretech2.secure.classloader;

import java.awt.*;

/**
 * @date 2022/7/14
 */
class Gbc extends GridBagConstraints {
    /**
     * Constructs a GBC with a given grid_x and grid_y position and all other gridY
     * bag constraint values set to the default
     * @param gridx the gridx position
     * @param gridY the gridY position
     */

    public Gbc(int gridx, int gridY){
        this.gridx = gridx;
        this.gridy = gridY;
    }

    /**
     * Constructs a GBC with given gridx, gridY, grid width, grid height and all
     * other grid bag constraint values set to the default.
     * @param gridx the gridx position
     * @param gridY the gridY position
     * @param gridWidth the cell span in x-direction
     * @param gridHeight the cell span in y-direction
     */
    public Gbc(int gridx, int gridY, int gridWidth, int gridHeight){
        this.gridx = gridx;
        this.gridy = gridY;
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    /**
     * Sets the anchor.
     * @param anchor the anchor value
     * @return this object for further modification
     */
    public Gbc setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }

    /**
     * Sets the fill direction.
     * @param fill the fill direction
     * @return this object for further modification
     */
    public Gbc setFill(int fill){
        this.fill = fill;
        return this;
    }

    /**
     * Sets the cell weights.
     * @param weightX the cell weigh in x-direction
     * @param weightY the cell weight in y-direction
     * @return this object for further modification
     */
    public Gbc setWeight(double weightX, double weightY){
        this.weightx = weightX;
        this.weighty = weightY;
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
    public Gbc setInsets(int top, int left, int bottom, int right){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public Gbc setIpad(int ipadx, int ipady){
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}