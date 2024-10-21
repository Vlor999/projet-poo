package gui;

import io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
public class GUISimulator extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JScrollPane sp;
    private SimulationPanel simuPanel;
    private JPanel controlPanel;
    
    private JButton restartButton;
    private JButton playPauseButton;
    private JButton nextButton;
    private JButton exitButton;
    private JButton fileButton;

    private JLabel speedLabel;
    private JSpinner speedSpinner;
    private JLabel stepLabel;
    private JSpinner stepSpinner;
    private JComboBox<String> selectBox;
    private static final int INIT_SPEED = 100;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10000;
    private static final int STEP_SPEED = 10;
    private boolean choosed = false;
    private int panelWidth;
    private int panelHeight;
    private int numberTickInStep = 1;

    private Timer timer;

    private Simulable simulator;

    private boolean warning = false;

    public GUISimulator(int paramInt1, int paramInt2, Color paramColor) {
        this(paramInt1, paramInt2, paramColor, new DefaultSimulator());
    }

    public GUISimulator(int paramInt1, int paramInt2, Color paramColor, Simulable paramSimulable) {
        super("Simulateur de Systèmes Multi-Agents");
        
        this.timer = new Timer(100, new TimerListener());
        this.timer.stop();
        setSimulable(paramSimulable);

        
        try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        this.simuPanel = new SimulationPanel(paramInt1, paramInt2, paramColor);
        this.sp = new JScrollPane(this.simuPanel);
        this.sp.setPreferredSize(new Dimension(Math.min(800, paramInt1), Math.min(600, paramInt2)));
        
        this.panelWidth = paramInt1;
        this.panelHeight = paramInt2;
        this.simuPanel.setBackground(paramColor);
        
        JPanel jPanel1 = new JPanel(new GridLayout(2, 2));
        this.speedLabel = new JLabel("Tps entre 2 affichages (ms) :");
        this.speedSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        
        jPanel1.add(this.speedLabel);
        jPanel1.add(this.speedSpinner);
        this.stepLabel = new JLabel("Nb de pas simulés entre 2 affichages :");
        this.stepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 10));
        
        jPanel1.add(this.stepLabel);
        jPanel1.add(this.stepSpinner);

        this.fileButton = new JButton("Ouvrir un fichier (*.map)");
        this.fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(GUISimulator.this);

                // Si un fichier est sélectionné, afficher le chemin dans une boîte de dialogue
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(GUISimulator.this, "Fichier sélectionné : " + selectedFile.getAbsolutePath());
                    
                    timer.stop(); 
                    playPauseButton.setText("Lecture");

                    LecteurDonnees.lireFichierEtSimuler(selectedFile.getAbsolutePath(), GUISimulator.this);
                    
                    simuPanel.repaint();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(GUISimulator.this, "Aucun fichier sélectionné.");
                }
            }
        });

        // Ajout du bouton au panel
        
        this.selectBox = new JComboBox<>();
        this.selectBox.addItemListener(paramItemEvent -> paramSimulable.selectedItem((String)paramItemEvent.getItem()));
        this.restartButton = new JButton("Début");
        this.playPauseButton = new JButton("Lecture");
        this.nextButton = new JButton("Suivant");
        this.exitButton = new JButton("Quitter");
        JPanel jPanel2 = new JPanel();
        jPanel2.add(this.playPauseButton);
        jPanel2.add(this.nextButton);
        jPanel2.add(this.restartButton);
        jPanel2.add(this.exitButton);
        jPanel2.add(this.fileButton);
        
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new BorderLayout());
        this.controlPanel.add(jPanel1, "West");
        this.controlPanel.add(jPanel2, "East");
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(this.sp, "Center");
        getContentPane().add(this.controlPanel, "South");
        
        DisplayControler displayControler = new DisplayControler(this);
        this.restartButton.setActionCommand("restart");
        this.restartButton.addActionListener(displayControler);
        this.playPauseButton.setActionCommand("playPause");
        this.playPauseButton.addActionListener(displayControler);
        this.nextButton.setActionCommand("next");
        this.nextButton.addActionListener(displayControler);
        this.speedSpinner.addChangeListener(displayControler);
        this.stepSpinner.addChangeListener(displayControler);
        this.exitButton.setActionCommand("exit");
        this.exitButton.addActionListener(displayControler);
        
        setDefaultCloseOperation(3);
        pack();
        setVisible(true);
    }


    public void setSimulable(Simulable paramSimulable) {
    this.simulator = paramSimulable;
    if (this.selectBox != null) {
        this.selectBox.addItemListener(paramItemEvent -> paramSimulable.selectedItem((String)paramItemEvent.getItem()));
        }
    }

    public int getPanelWidth() {
        return this.panelWidth;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    private JPanel getSimulationPanel() {
        return this.simuPanel;
    }

    public void addGraphicalElement(GraphicalElement paramGraphicalElement) {
        this.simuPanel.addGraphicalElement(paramGraphicalElement);
    }

    public void reset() {
        this.simuPanel.reset();
    }

    private void next() {
        this.simulator.next();
        this.simuPanel.repaint();
        repaint();
    }

    public void resizePanel(int paramInt1, int paramInt2) {
        this.panelHeight = paramInt2;
        this.panelWidth = paramInt1;
        this.simuPanel.setPreferredSize(new Dimension(paramInt1, paramInt2));
        this.sp.revalidate();
        this.simuPanel.revalidate();
    }

    public void addItemToList(String paramString) {
        this.selectBox.addItem(paramString);
        if (this.selectBox.getItemCount() == 1) {
        this.controlPanel.add(this.selectBox);
        pack();
        } 
    }

    public void setWarning(boolean paramBoolean) {
        this.warning = paramBoolean;
    }

    private class TimerListener implements ActionListener { private TimerListener() {}
        
        public void actionPerformed(ActionEvent param1ActionEvent) {
        long l = System.currentTimeMillis();
        for (byte b = 0; b < GUISimulator.this.numberTickInStep; b++) {
            GUISimulator.this.next();
        }
        if (GUISimulator.this.warning && System.currentTimeMillis() - l > GUISimulator.this.timer.getDelay())
            System.err.println("/!\\ Pas de simulation plus long que la durée attendue ! " + (System.currentTimeMillis() - l) + "ms au lieu de " + GUISimulator.this.timer.getDelay() + "ms"); 
        } }


    private class DisplayControler implements ActionListener, ChangeListener {
        private boolean play;
        
        public DisplayControler(GUISimulator param1GUISimulator1) {
        init();
        }
        
        private void init() {
            this.play = false;
            GUISimulator.this.timer.stop();
            GUISimulator.this.timer.restart();
            GUISimulator.this.timer.stop();
            GUISimulator.this.playPauseButton.setText("Lecture");
        }
        
        public void actionPerformed(ActionEvent param1ActionEvent) {
            if (Objects.equals(param1ActionEvent.getActionCommand(), "playPause")) {
                if (this.play) {
                    GUISimulator.this.timer.stop();
                    GUISimulator.this.playPauseButton.setText("Lecture");
                } else {
                    GUISimulator.this.timer.restart();
                    GUISimulator.this.playPauseButton.setText("Pause");
                } 
                this.play = !this.play;
            } else if (Objects.equals(param1ActionEvent.getActionCommand(), "next")) {
                for (byte b = 0; b < GUISimulator.this.numberTickInStep; b++) {
                GUISimulator.this.next();
                }
            } else if (Objects.equals(param1ActionEvent.getActionCommand(), "restart")) {
                init();
                    GUISimulator.this.simulator.restart();
                    GUISimulator.this.simuPanel.repaint();
                    GUISimulator.this.repaint();
            } else if (Objects.equals(param1ActionEvent.getActionCommand(), "exit")) {
                System.exit(0);
            } 
        }

        
        public void stateChanged(ChangeEvent param1ChangeEvent) {
            GUISimulator.this.numberTickInStep = ((SpinnerNumberModel)GUISimulator.this.stepSpinner.getModel())
        .getNumber().intValue();
            GUISimulator.this.timer.setDelay(((SpinnerNumberModel)GUISimulator.this.speedSpinner.getModel())
            .getNumber().intValue());
        }
    }
}