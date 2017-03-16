import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class ProcesoEnApplet extends javax.swing.JApplet {

    public void init() {
        
        this.resize(675, 300);
        initComponents();

    }

 

    private void initComponents() {
       
       
        jScrollPane1 = new javax.swing.JScrollPane();
        salida = new javax.swing.JTextArea();
        tfNroProcesos = new javax.swing.JTextField();
        tfNroCiclos = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        chkFifo = new javax.swing.JCheckBox();
        chkRR = new javax.swing.JCheckBox();
        chkHRN = new javax.swing.JCheckBox();
        chkRNM = new javax.swing.JCheckBox();
        btnEjecutar = new javax.swing.JButton();
        btnWhoIs = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        

        salida.setEditable(true);
        salida.setEnabled(true);
        salida.setFocusable(true);
        salida.setColumns(40);
        salida.setRows(15);
        salida.setTabSize(4);
        salida.setDoubleBuffered(true);
        


        jScrollPane1.setViewportView(salida);

        tfNroProcesos.setColumns(2);
        tfNroProcesos.setText ("5");
        tfNroProcesos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNroProcesos.setToolTipText("Ingrese la cantidad de procesos a utilizar en la simulaci�n.");

        tfNroCiclos.setColumns(2);
        tfNroCiclos.setText("20");
        tfNroCiclos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfNroCiclos.setToolTipText("Se sugiere entre 30 y 40 cilos de control por cada 10 procesos.");

        jLabel1.setText("Nro. Procesos");

        jLabel2.setText("Nro. Ciclos de Control");

        chkFifo.setText("Fifo");
        chkFifo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkFifo.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chkFifo.setSelected(true);

        chkRR.setText("RR");
        chkRR.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkRR.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chkRR.setSelected(true);

        chkHRN.setText("HRN");
        chkHRN.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkHRN.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chkHRN.setSelected(true);

        chkRNM.setText("RNM");
        chkRNM.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkRNM.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chkRNM.setSelected(true);

        btnEjecutar.setText("Ejecutar Simulacion");

        btnEjecutar.addActionListener (new ActionListener(){


            public void actionPerformed(ActionEvent e){


                int nroProcesos = Integer.parseInt(tfNroProcesos.getText());
                int nroCiclos = Integer.parseInt(tfNroCiclos.getText ());
                boolean sFifo = chkFifo.isSelected();
                boolean sRR=chkRR.isSelected ();
                boolean sHRN=chkHRN.isSelected ();
                boolean sRNM=chkRNM.isSelected ();

                if(nroProcesos >0 && nroProcesos <=50){
                    if(nroCiclos > 0){
                        procesos = new Procesos();
                        i1 = salida.getText().length () + 738;
                        System.out.println(i1);
                        try{
                        procesos.simular (nroCiclos, nroProcesos,salida, sFifo, sRR, sHRN, sRNM);
                        }catch (Exception exception){
                            setContentPane (new JPanel());
                            getContentPane().add(new Label("Error grave. " + exception.getMessage () + " - " + exception.toString ()));
                            getContentPane().repaint ();
                            exception.printStackTrace ();
                        }finally{
                            salida.setCaretPosition(i1);
                        }
                    }
                }else{

                }
            }
        });

        btnWhoIs.setText("Acerca de...");
        btnWhoIs.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e){

                JFrame ven = new JFrame();
                JTextArea jTextArea1= new JTextArea();                
                jTextArea1.setBackground(new java.awt.Color(241, 243, 248));
                jTextArea1.setColumns(20);
                jTextArea1.setFont(new java.awt.Font("Bodoni MT", 0, 14));
                jTextArea1.setRows(5);
                jTextArea1.setText("Este Applet realiza la simulación de la Planificación del \nProcesador.  Fue desarrollado para la Catedra Sistemas \nOperativos, dictada por el Mster. David Luis La Red Martínez,\nen la Facultad de Ciencias Exactas y Naturales y Agrimensura,\ndependiente de la Universidad Nacional del Nordeste (UNNE),\npor los alumnos Nelson Fabián Rodriguez (Adscripto a la Catedra)\ny Anibal Sebastian Rolando (Colaborador)");
                jTextArea1.setEditable(false);
                ven.setTitle("Acerca de...");
                ven.add(jTextArea1);
                ven.setVisible(true);
                ven.setSize(400,200);
                ven.setLocation(300,200);
                ven.show();                
            }

        });
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e){
                salida.setText ("");
            }

        });
       
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getRootPane ().getContentPane ());
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnWhoIs, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEjecutar, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(chkFifo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(chkRR)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(chkHRN))
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkRNM)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfNroCiclos, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfNroProcesos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEjecutar, btnLimpiar, btnWhoIs});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfNroProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfNroCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkFifo)
                            .addComponent(chkRR)
                            .addComponent(chkHRN)
                            .addComponent(chkRNM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEjecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnWhoIs, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEjecutar, btnLimpiar, btnWhoIs});

    }
   

    private javax.swing.JTextField tfNroCiclos;
    private javax.swing.JTextField tfNroProcesos;
    private javax.swing.JTextArea salida;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnWhoIs;
    private javax.swing.JCheckBox chkFifo;
    private javax.swing.JCheckBox chkHRN;
    private javax.swing.JCheckBox chkRNM;
    private javax.swing.JCheckBox chkRR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private Procesos procesos;
    private int i1;
    private AcercaDe ad;
}