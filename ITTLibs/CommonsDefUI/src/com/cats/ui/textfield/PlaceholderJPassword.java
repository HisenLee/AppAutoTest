package com.cats.ui.textfield;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class PlaceholderJPassword extends JPasswordField {

    public static void main(final String[] args) {
        final PlaceholderJPassword tf = new PlaceholderJPassword("");
        tf.setColumns(20);
        tf.setPlaceholder("All your base are belong to us!");
        final Font f = tf.getFont();
        tf.setFont(new Font(f.getName(), f.getStyle(), 30));
        JOptionPane.showMessageDialog(null, tf);
    }

    private String placeholder;

    public PlaceholderJPassword() {
    }

    public PlaceholderJPassword(
        final Document pDoc,
        final String pText,
        final int pColumns)
    {
        super(pDoc, pText, pColumns);
    }

    public PlaceholderJPassword(final int pColumns) {
        super(pColumns);
    }

    public PlaceholderJPassword(final String pText) {
        super(pText);
    }

    public PlaceholderJPassword(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);
        
        if (placeholder == null || placeholder.length() == 0 || getPassword().length > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left+3, pG.getFontMetrics()
            .getMaxAscent() + getInsets().top + 3);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }
}