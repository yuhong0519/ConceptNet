/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleUI;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Hong
 */
public class RecaptchaPanel extends javax.swing.JPanel {
    private  JLabel[] propertyLabels;
    private Color propertyLabelEntered = new Color(230,230,230);
    private Color propertyLabelExited = new Color(255,255,255);
    private Color conceptLabelEntered = new Color(200,200,255);
    private Color conceptLabelExited = new Color(230,230,255);
    private RecaptchaControl rc = new RecaptchaControl();
    private int totalNumProperties = 6;
    
    ArrayList<String> properties1 = new ArrayList();
    ArrayList<String> properties2 = new ArrayList();
    
    private int propertyLabelDefaultWid = 120, propertyLabelDefaultHeit = 29;
    private int yStartPos = 99, xStartPos = 332;
    /**
     * Creates new form RecaptchaPanel
     */
    public RecaptchaPanel() {
        
        initComponents();
        initPropertyLabels();
        startProcess();
    }

    private void startProcess(){
        ArrayList<String> concepts = new ArrayList();
        ArrayList<String> properties = new ArrayList();
        rc.getConceptProperty(concepts, properties);
        jLabel1.setText(concepts.get(0));
        jLabel2.setText(concepts.get(1));
        if(properties.size() > totalNumProperties){
            System.err.println("Get more properties than the number required");
            return;
        }
        for(int i = 0; i < properties.size(); i++){
            String tp = properties.get(i);
            propertyLabels[i].setVisible(true);
            propertyLabels[i].setText(tp);
            if(tp.length() > 12){
                
                add(propertyLabels[i], new org.netbeans.lib.awtextra.AbsoluteConstraints(xStartPos, yStartPos + 50 * i, propertyLabelDefaultWid + 5 * (tp.length()-12), propertyLabelDefaultHeit));
//                propertyLabels[i].setSize(propertyLabelDefaultWid + 9 * (tp.length()-10), propertyLabelDefaultHeit);
            }
            else{
                add(propertyLabels[i], new org.netbeans.lib.awtextra.AbsoluteConstraints(xStartPos, yStartPos + 50 * i, propertyLabelDefaultWid , propertyLabelDefaultHeit));
//              
            }
        }
        for(int i = properties.size(); i < totalNumProperties; i++){
            propertyLabels[i].setVisible(false);
        }
        this.revalidate();
        this.repaint();
        properties1.clear();
        properties2.clear();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        restartButton = new javax.swing.JButton();
        finishButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        restartButton.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        restartButton.setText("Restart");
        restartButton.setEnabled(false);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });
        add(restartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 531, 110, 39));

        finishButton.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        finishButton.setText("Finish");
        finishButton.setMaximumSize(new java.awt.Dimension(200, 150));
        finishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });
        add(finishButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 530, 110, 39));

        jLabel1.setBackground(new java.awt.Color(230, 230, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 130, 40));

        jLabel2.setBackground(new java.awt.Color(230, 230, 255));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 130, 40));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, 400));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 150, 400));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 200, 40));
    }// </editor-fold>//GEN-END:initComponents

    
    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        // TODO add your handling code here:
//        this.getLayout().addLayoutComponent("property1", propertyLabels[1]);
//        propertyLabels[0].setText("testttt");
//        propertyLabels[1].setText("testttt2");
//        
////        this.add(propertyLabels[1]);
////        add(propertyLabels[0], new org.netbeans.lib.awtextra.AbsoluteConstraints(332, pos, 90, 29));
////        add(propertyLabels[1], new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 183, 90, 29));
//        
////        pos += 50;
//        this.revalidate();
//        this.repaint();
        finishButton.setEnabled(true);
        restartButton.setEnabled(false);
        jLabel5.setText("");
//        System.out.println(propertyLabels[0].getY());
        startProcess();
    }//GEN-LAST:event_restartButtonActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
        // TODO add your handling code here:
        
        finishButton.setEnabled(false);
        restartButton.setEnabled(true);
        for(int i = 0; i < totalNumProperties; i++){
            propertyLabels[i].setText("");
            propertyLabels[i].setVisible(false);
            
        }
        jLabel3.setText("");
        jLabel4.setText("");
        jLabel1.setText("");
        jLabel2.setText("");      
        

        if(rc.checkValid(properties1, properties2)){
//            JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this), "You succeeded!");
//            dialog.setModal(true);
//            dialog.setSize(200, 50);
//            dialog.setVisible(true);
            jLabel5.setText("You succeeded!");
        }
        else{
//            JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this), "You failed!");
//            dialog.setModal(true);
//            dialog.setSize(200, 50);
//            dialog.setVisible(true);    
            jLabel5.setText("You failed!");
        }
        properties1.clear();
        properties2.clear();
    }//GEN-LAST:event_finishButtonActionPerformed

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        jLabel2.setBackground(conceptLabelEntered);
        
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
        jLabel2.setBackground(conceptLabelExited);
        
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        jLabel1.setBackground(conceptLabelEntered);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
        jLabel1.setBackground(conceptLabelExited);
    }//GEN-LAST:event_jLabel1MouseExited

private void propertyLabelMouseEntered(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    ((JLabel)evt.getSource()).setBackground(propertyLabelEntered);
    this.revalidate();
    this.repaint();
//        System.out.println(((JLabel)(evt.getSource())).getY());
}

private void propertyLabelMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    ((JLabel)evt.getSource()).setBackground(propertyLabelExited);
    this.revalidate();
    this.repaint();
        
}

private String addToPropertyLabel(String current, String name){    
    String mid = "";
    if(current != null && current.length() > 6){
        mid = current.substring(6, current.length()-7);
    }            
    return "<html>" + mid + "<br>" + name + "</html>";
}

private void propertyLabelMouseDragged(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    PointerInfo a = MouseInfo.getPointerInfo();
    Point mousePosition  = a.getLocation();
    JLabel tlabel = (JLabel)evt.getSource();
    int wid = tlabel.getWidth();
    int heit = tlabel.getHeight();
    int mouseX = mousePosition.x-this.getLocationOnScreen().x;
    int mouseY = mousePosition.y-this.getLocationOnScreen().y;
    add((JLabel)evt.getSource(), new org.netbeans.lib.awtextra.AbsoluteConstraints(mouseX-wid/2, mouseY-heit/2, wid, heit));
    if(mouseX < 150 && mouseX > 20 && mouseY > 30 && mouseY < 70){
        String tp = ((JLabel)evt.getSource()).getText();
        if(!properties1.contains(tp)){
            properties1.add(tp);
            jLabel3.setText(addToPropertyLabel(jLabel3.getText(), tp));
            ((JLabel)evt.getSource()).setVisible(false);
        }

    }
    else if(mouseX < 780 && mouseX > 650 && mouseY > 30 && mouseY < 70){
        String tp = ((JLabel)evt.getSource()).getText();
        if(!properties2.contains(tp)){
            properties2.add(tp);  
            jLabel4.setText(addToPropertyLabel(jLabel4.getText(), tp));
//            jLabel4.setText(jLabel4.getText() + "\n" + tp);
            ((JLabel)evt.getSource()).setVisible(false);
        }

    }
    
    this.revalidate();
    this.repaint();
} 
    

 

private void initPropertyLabels(){
    
    
    propertyLabels = new JLabel[totalNumProperties];
    
    for(int i = 0; i < totalNumProperties; i++){
        
        propertyLabels[i] = new javax.swing.JLabel();
        propertyLabels[i].setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        propertyLabels[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        propertyLabels[i].setEnabled(false);
        propertyLabels[i].setOpaque(true);
        propertyLabels[i].setBackground(propertyLabelExited);
        
        propertyLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                propertyLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                propertyLabelMouseExited(evt);
            }
        });
        propertyLabels[i].addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                propertyLabelMouseDragged(evt);
            }
        });
        propertyLabels[i].setText("");
        add(propertyLabels[i], new org.netbeans.lib.awtextra.AbsoluteConstraints(xStartPos, yStartPos + i * 50, propertyLabelDefaultWid, propertyLabelDefaultHeit));
        
    }
    
    this.revalidate();
    this.repaint();
    
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton finishButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton restartButton;
    // End of variables declaration//GEN-END:variables
}
