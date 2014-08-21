/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleUI;

/**
 *
 * @author Hong
 */
public class RecaptchaMain extends javax.swing.JFrame{
    
    public RecaptchaMain() {
        RecaptchaPanel recaptchaPanel = new RecaptchaPanel();
        setContentPane(recaptchaPanel);
//        setSize(800, 600);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                RecaptchaMain rm = new RecaptchaMain();
                rm.setSize(800,630);
                rm.setResizable(false);
                rm.setVisible(true);
                
//                rm.setExtendedState( rm.MAXIMIZED_BOTH );
            }
        });
    }
    
}
