package gui;

import io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;
public class GUISimulator extends JFrame
{

    String[] options = {"Pokemon", "Pacman", "Mario"};
    

    private static final long serialVersionUID = 1L;
    private JScrollPane sp;
    private SimulationPanel simuPanel;
    private JPanel controlPanel;
    
    private JButton restartButton;
    private JButton playPauseButton;
    private JButton nextButton;
    private JButton exitButton;
    private JButton fileButton;
    private JComboBox<String> typeImage;

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

    private JButton createHoverButton(String text) {
        JButton button = new JButton(text);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIManager.getColor("control"));
            }
        });
        return button;
    }

    public GUISimulator(int paramInt1, int paramInt2, Color paramColor, Simulable paramSimulable) {
        super("Simulateur de Systèmes Multi-Agents");
    
        this.timer = new Timer(100, new TimerListener());
        this.timer.stop();
        setSimulable(paramSimulable);
    
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    
        // Panneau pour la zone de simulation
        this.simuPanel = new SimulationPanel(paramInt1, paramInt2, paramColor);
        this.sp = new JScrollPane(this.simuPanel);
        this.sp.setPreferredSize(new Dimension(Math.min(800, paramInt1), Math.min(600, paramInt2)));
    
        this.panelWidth = paramInt1;
        this.panelHeight = paramInt2;
        this.simuPanel.setBackground(paramColor);
    
        // Panneau de configuration (haut de l'écran) pour les valeurs modifiables
        JPanel controlSettingsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        this.speedLabel = new JLabel("Tps entre 2 affichages (ms) :");
        this.speedSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        controlSettingsPanel.add(this.speedLabel);
        controlSettingsPanel.add(this.speedSpinner);
    
        this.stepLabel = new JLabel("Nb de pas simulés entre 2 affichages :");
        this.stepSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 10));
        controlSettingsPanel.add(this.stepLabel);
        controlSettingsPanel.add(this.stepSpinner);
    
        // Panneau de boutons de contrôle (bas de l'écran)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.fileButton = createHoverButton("Ouvrir un fichier (*.map)");
        this.selectBox = new JComboBox<>();
        this.selectBox.addItemListener(paramItemEvent -> paramSimulable.selectedItem((String)paramItemEvent.getItem()));
        this.typeImage = new JComboBox<>(options);
    
        this.restartButton = createHoverButton("Début");
        this.playPauseButton = createHoverButton("Lecture");
        this.nextButton = createHoverButton("Suivant");
        this.exitButton = createHoverButton("Quitter");
    
        // Ajout de tous les boutons dans le panneau des boutons
        buttonPanel.add(this.playPauseButton);
        buttonPanel.add(this.nextButton);
        buttonPanel.add(this.restartButton);
        buttonPanel.add(this.exitButton);
        buttonPanel.add(this.fileButton);
        buttonPanel.add(this.typeImage);
    
        // Configuration du panneau principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(this.sp, BorderLayout.CENTER);
        getContentPane().add(controlSettingsPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    
        // Gestion des actions
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
        this.typeImage.setActionCommand("typeImage");
        this.typeImage.addActionListener(displayControler);
        this.fileButton.setActionCommand("fileButton");
        this.fileButton.addActionListener(displayControler);
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            } else if (Objects.equals(param1ActionEvent.getActionCommand(), "typeImage")) {
                String selectedItem = (String) typeImage.getSelectedItem();
                JOptionPane.showMessageDialog(GUISimulator.this, "Vous avez selectionné : " + selectedItem);
                int index = typeImage.getSelectedIndex();
                Draw.setNumberToShow(index);

                GUISimulator.this.simuPanel.repaint();
                repaint();
                GUISimulator.this.simulator.next();
            }
            else if (Objects.equals(param1ActionEvent.getActionCommand(), "fileButton")) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(GUISimulator.this);

                // Si un fichier est sélectionné, afficher le chemin dans une boîte de dialogue
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(GUISimulator.this, "Fichier sélectionné : " + selectedFile.getAbsolutePath());

                    LecteurDonnees.lireFichierEtSimuler(selectedFile.getAbsolutePath(), GUISimulator.this);
                    speedSpinner.setValue(100);
                    stepSpinner.setValue(1);
                    
                    simuPanel.repaint();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(GUISimulator.this, "Aucun fichier sélectionné.");
                }
            }
        }

        
        public void stateChanged(ChangeEvent param1ChangeEvent) {
            GUISimulator.this.numberTickInStep = ((SpinnerNumberModel)GUISimulator.this.stepSpinner.getModel())
        .getNumber().intValue();
            GUISimulator.this.timer.setDelay(((SpinnerNumberModel)GUISimulator.this.speedSpinner.getModel())
            .getNumber().intValue());
        }

        public void setPlay(boolean param1Boolean) {
            this.play = param1Boolean;
        }
    }

    @Override
    public String toString()
    {
        return "GUISimulator";
    }
}