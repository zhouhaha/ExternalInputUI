/*
 * Copyright 2016 andylizi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.andylizi.externalinputui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.UIManager;

/**
 * @author andylizi
 */
public class InputGUI extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final Font FONT_YAHEI = new Font("Microsoft YaHei", Font.PLAIN, 12);

    public InputGUI(String title, String btnDone, String btnCancel) {
        super((JDialog) null, title,  true);
        initComponents();
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(getClass().getResource("/assets/externalinputui/icon/input_ui.png")));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        this.btnDone.setText(btnDone);
        this.btnCancel.setText(btnCancel);
    }
    
    public static void initLAF(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        }catch(Exception ex){
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch(Exception ex1) {}
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textbox = new javax.swing.JTextField();
        btnDone = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);
        setName("externalInputUI"); // NOI18N
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                onFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onClickClose(evt);
            }
        });

        textbox.setFont(FONT_YAHEI);
        textbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onPressEnter(evt);
            }
        });

        btnDone.setFont(FONT_YAHEI);
        btnDone.setText("gui.done");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDone(evt);
            }
        });

        btnCancel.setFont(FONT_YAHEI);
        btnCancel.setText("gui.cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickCancel(evt);
                onClickDone(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(textbox, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onClickDone(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onClickDone
        dispose();
    }//GEN-LAST:event_onClickDone

    private void onClickCancel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onClickCancel
        textbox.setText(null);
    }//GEN-LAST:event_onClickCancel

    private void onPressEnter(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onPressEnter
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            btnDone.doClick();
        else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)
            onClickCancel(null);
    }//GEN-LAST:event_onPressEnter

    private void onClickClose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onClickClose
        textbox.setText(null);
        dispose();
    }//GEN-LAST:event_onClickClose

    private void onFocus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_onFocus
        textbox.requestFocusInWindow();
    }//GEN-LAST:event_onFocus

    public static String showInputDialog(String title, String btnDone, String btnCancel){
        InputGUI ui = new InputGUI(title, btnDone, btnCancel);
        ui.setVisible(true);
        ui.dispose();
        return ui.textbox.getText();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDone;
    private javax.swing.JTextField textbox;
    // End of variables declaration//GEN-END:variables
}
