/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class PanelDibujo extends javax.swing.JPanel{
    public void paint(java.awt.Graphics g){
        super.paint(g);
        //g.drawString("Hola mundo!!!", 10, 10);
    }

    PanelDibujo(Region[] r){
        this.regiones = r;
    }
    Region[] regiones;
}
