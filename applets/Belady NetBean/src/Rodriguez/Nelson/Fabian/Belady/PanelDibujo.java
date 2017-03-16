/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Rodriguez.Nelson.Fabian.Belady;

/**
 *
 * @author Nelson
 */
public class PanelDibujo extends javax.swing.JPanel{
    public void paint(java.awt.Graphics g){
        super.paint(g);
        if(p != null)
            p.dibujar();
    }


    public void setPrincipal(Principal p){
        this.p = p;
    }
    Principal p;
}
