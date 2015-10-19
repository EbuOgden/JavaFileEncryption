import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Encryption extends JFrame {

	
	private JPanel contentPane;
	private final JButton btnBroseFiles = new JButton("Browse Files");
	private final JLabel lblChooseFile = new JLabel("Choose File");
	private final JLabel lblFileName = new JLabel("File Name");
	private final JTextField textField = new JTextField();
	private final JButton btnEncrypt = new JButton("Encrypt");
	private final JButton btnDecrypt = new JButton("Decrypt");
	private final JLabel lblNewLabel = new JLabel("");
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JLabel lblFilePath = new JLabel("File Path");
	private final JTextField textField_1 = new JTextField();
	private final JLabel lblEnterPassword = new JLabel("Enter Password (16 character)");
	private final JTextField textField_2 = new JTextField();
	private final JCheckBox chckbxNewCheckBox = new JCheckBox("Delete Original File");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encryption frame = new Encryption();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Encryption() {
		textField_2.setBounds(265, 152, 134, 28);
		textField_2.setColumns(10);
		textField_1.setBounds(265, 112, 134, 28);
		textField_1.setColumns(10);
		textField.setBounds(265, 72, 134, 28);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnBroseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				fileopen.showDialog(null, "Open");
				File file = fileopen.getSelectedFile();	
				textField.setText(file.getName());
				textField_1.setText(file.getPath());
				
				
			}
		});
		btnBroseFiles.setBounds(265, 31, 117, 29);
		
		contentPane.add(btnBroseFiles);
		lblChooseFile.setBounds(6, 36, 80, 16);
		
		contentPane.add(lblChooseFile);
		lblFileName.setBounds(6, 75, 68, 16);
		
		contentPane.add(lblFileName);
		
		contentPane.add(textField);

		
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePathName = textField_1.getText();
				String ALGORITHM = "AES";
				String TRANSFORMATION = "AES";
				File inputFile = new File(filePathName);
				String key = textField_2.getText();
				
				
				
				/*RuntimeMXBean rmx = ManagementFactory.getRuntimeMXBean();//to get pc name
				char [] first16 = new char [16];
				String pcName = rmx.getName();
				int index = pcName.length() - 16;
				int counter = 0;
				for(int i = pcName.length() - 1; i > index  ; i--)
				{
					first16[counter] = (char)pcName.charAt(i);//get pc name and last 16 byte take for key
					counter++;
				}
				String key = new String(first16);// char array to string
				System.out.println(key);
				*/
				
			try{
				if(key.length() < 16 || key.length() > 16){
					JOptionPane.showMessageDialog(null, "Please Enter 16 character!");
				}
				if(key.length() == 16){
					JOptionPane.showMessageDialog(null, "Don't forget your password!");
				}
				
				
				Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
	            cipher.init(1, secretKey);
	          	            	            
	            FileInputStream inputStream = new FileInputStream(inputFile);
	            byte[] inputBytes = new byte[(int) inputFile.length()];
	            inputStream.read(inputBytes);

	            byte[] outputBytes = cipher.doFinal(inputBytes);
	            
	            String outputFile = textField_1.getText() + ".encrypted";
				FileOutputStream outputStream = new FileOutputStream(outputFile);
	            outputStream.write(outputBytes);
	            textField.setText("");
	            textField_1.setText("");
	            textField_2.setText("");
	            if(chckbxNewCheckBox.isSelected()){
	            	inputFile.delete();
	            }
	            chckbxNewCheckBox.setSelected(false);
	            lblNewLabel.setText("Encryption Succesful!");
	            	           
	            
	            inputStream.close();
	            outputStream.close();
			}
			catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
	            try {
					throw new CryptoException("Error encrypting/decrypting file", ex);
				} catch (CryptoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btnEncrypt.setBounds(72, 231, 117, 29);
		
		contentPane.add(btnEncrypt);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ALGORITHM = "AES";
				String TRANSFORMATION = "AES";
				File encryptedFile = new File(textField_1.getText());
				String key = textField_2.getText();
				
				
				/*
				RuntimeMXBean rmx = ManagementFactory.getRuntimeMXBean();//to get pc name
				char [] first16 = new char [16];
				for(int i = 0; i < 16 ; ++i)
				{
					first16[i] = (char)rmx.getName().charAt(i);//get pc name and first 16 byte take for key
				}
				String key = new String(first16);// char array to string
			
				*/
			try{
				if(key.length() < 16 || key.length() > 16){
					JOptionPane.showMessageDialog(null, "Please Enter 16 character!");
				}
				Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
	            cipher.init(2, secretKey);
	            
	            FileInputStream inputStream = new FileInputStream(encryptedFile);
	            byte[] inputBytes = new byte[(int) encryptedFile.length()];
	            inputStream.read(inputBytes);
	          
	            byte[] outputBytes = cipher.doFinal(inputBytes);
	            
	            String out = textField_1.getText();
	            int outputFilelength = out.length();
	            outputFilelength = outputFilelength - 10;
	            char [] output = new char[outputFilelength];
	            for(int counter = 0; counter < outputFilelength; counter++){
	            	output[counter] = out.charAt(counter);
	            }
	            
	            String outputFile = new String(output);
	            FileOutputStream outputStream = new FileOutputStream(outputFile);
	            outputStream.write(outputBytes);
	            textField.setText("");
	            textField_1.setText("");
	            textField_2.setText("");
	            if(chckbxNewCheckBox.isSelected()){
	            	encryptedFile.delete();
	            }
	            chckbxNewCheckBox.setSelected(false);
	            lblNewLabel_1.setText("Decryption Succesful!");
	            
	            inputStream.close();
	            outputStream.close();
			}
			catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
	            try {
					throw new CryptoException("Error encrypting/decrypting file", ex);
				} catch (CryptoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btnDecrypt.setBounds(252, 231, 117, 29);
		
		contentPane.add(btnDecrypt);
		lblNewLabel.setBounds(27, 272, 183, 16);
		
		contentPane.add(lblNewLabel);
		lblNewLabel_1.setBounds(252, 272, 193, 16);
		
		contentPane.add(lblNewLabel_1);
		lblFilePath.setBounds(6, 121, 61, 16);
		
		contentPane.add(lblFilePath);
		
		contentPane.add(textField_1);
		lblEnterPassword.setBounds(6, 160, 203, 16);
		
		contentPane.add(lblEnterPassword);
		
		contentPane.add(textField_2);
		chckbxNewCheckBox.setBounds(265, 192, 169, 23);
		
		contentPane.add(chckbxNewCheckBox);
	}
}
