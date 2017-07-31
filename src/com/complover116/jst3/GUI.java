package com.complover116.jst3;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class GUI {
	static void initConfigGUI() {
		new configGUI().init();
	}
	static class configGUI implements ActionListener {
		JFrame configWindow;
		JPanel rootPanel;
		JRadioButton oldModeButton;
		JRadioButton newModeButton;
		ButtonGroup modeSelectors;
		JSeparator modeButtonsSeparator;
		JPanel cards;
		JPanel oldMode;
		JPanel newMode;
		static final String OLDMODE = "Old Mode";
		static final String NEWMODE = "New Mode";
		static final String START_OLDMODE = "Start Old Mode";
		JTextField oldModeFilename;
		JLabel oldModeFilenameLabel;
		JButton oldModeStartButton;
		void init() {
			System.out.print("Starting the configuration GUI...");
			configWindow = new JFrame("JST3 Config");
			configWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			rootPanel = new JPanel();
			rootPanel.setLayout(new GridBagLayout());
			configWindow.add(rootPanel);
			oldModeButton = new JRadioButton("Old Mode(Overlay)");
			oldModeButton.setMnemonic(KeyEvent.VK_O);
			oldModeButton.setActionCommand(OLDMODE);
			oldModeButton.addActionListener(this);
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;
				rootPanel.add(oldModeButton, c);
			}
			newModeButton = new JRadioButton("New Mode(Own Window)");
			newModeButton.setMnemonic(KeyEvent.VK_N);
			newModeButton.setSelected(true);
			newModeButton.setActionCommand(NEWMODE);
			newModeButton.addActionListener(this);
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 1;
				c.gridy = 0;
				rootPanel.add(newModeButton, c);
			}
			modeSelectors = new ButtonGroup();
			modeSelectors.add(oldModeButton);
			modeSelectors.add(newModeButton);
			
			modeButtonsSeparator = new JSeparator(JSeparator.HORIZONTAL);
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 1;
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipady = 5;
				rootPanel.add(modeButtonsSeparator, c);
			}
			
			cards = new JPanel();
			cards.setLayout(new CardLayout());
			oldMode = new JPanel();
			cards.add(oldMode, OLDMODE);
			newMode = new JPanel();
			cards.add(newMode, NEWMODE);
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 2;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.weightx = 1;
				c.weighty = 1;
				rootPanel.add(cards, c);
			}
			
			
			oldMode.setLayout(new GridBagLayout());
			oldModeFilename = new JTextField();
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 1;
				c.gridy = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1;
				oldMode.add(oldModeFilename, c);
			}
			oldModeFilenameLabel = new JLabel("Filename:");
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;
				c.weightx = 0;
				oldMode.add(oldModeFilenameLabel, c);
			}
			oldModeStartButton = new JButton();
			oldModeStartButton.setText("Start in Old Mode");
			oldModeStartButton.setActionCommand(START_OLDMODE);
			oldModeStartButton.addActionListener(this);
			{
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 1;
				c.gridwidth = GridBagConstraints.REMAINDER;
				oldMode.add(oldModeStartButton, c);
			}
			configWindow.pack();
			configWindow.setVisible(true);
			System.out.println("OK");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == OLDMODE) {
				System.out.println("Old Mode Selected");
				((CardLayout)cards.getLayout()).show(cards, OLDMODE);
				configWindow.pack();
			}
			if(e.getActionCommand() == NEWMODE) {
				System.out.println("New Mode Selected");
				((CardLayout)cards.getLayout()).show(cards, NEWMODE);
				configWindow.pack();
			}
			if(e.getActionCommand() == START_OLDMODE) {
				System.out.println("Old Mode starting...");
				OldMode.startOldMode(oldModeFilename.getText());
				
			}
		}
	}
}
