package inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import grafig.Loader;
import grafig.Panel;

public class ActionButton {

	BtnCreator btnCreator = new BtnCreator();
	JButton btnForward = new JButton("Forward");
	JButton btnBackward = new JButton("Backward");
	JButton btnBuilding = new JButton("Building");
	JButton btnSpawnSoldir = new JButton("Building");
	ArrayList<JButton> ContentButtonArray = new ArrayList<JButton>();
	ArrayList<JButton> jButton = new ArrayList<JButton>();
	JButton btn;
	
	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}
	
	public void createUserUnitOptions(Panel console){
		deselectOptions(console);
		btn = new JButton(wrapLines("Forward"));
		btnCreator.createOne(btn, 200+(0*62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Forward! MY FIRENDS ATACKKKKKK!!!");
			}
		});
		jButton.add(btn);
		console.add(btn);
		
		btn = new JButton(wrapLines("Fast Forward"));
		btnCreator.createOne(btn, 200+(1*62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("NOW! ATTACK THE BASE!");
			}
		});
		jButton.add(btn);
		console.add(btn);
		
		btn = new JButton(wrapLines("Stay"));
		btnCreator.createOne(btn, 200+(2*62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Stop guys. Let them come!");
			}
		});
		jButton.add(btn);
		console.add(btn);
		
		btn = new JButton(wrapLines("Retreat"));
		btnCreator.createOne(btn, 200+(3*62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Reverse of Forward!!");
			}
		});
		jButton.add(btn);
		console.add(btn);
	}
	
	// Geb�udeoptionen
	public void createUserOptions(Panel console, Panel field, Game game, ArrayList<Buildings> BuildingsEntity, int i, Loader load, Funktions func){
		deselectOptions(console);
		String [] noName  = BuildingsEntity.get(i).getSpwanableEntity();
		// Generiert alle m�glichen Geb�udeoptionen
		for (int n = 0; n != noName.length; n++){
			btn = new JButton(wrapLines(noName[n]));
			btnCreator.createOne(btn, 200+(n*62), 30, 60, 60, 87);
			String type = getEntityAction(noName[n]);
			
			btn.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					if (type.equals("Building")){
						System.out.println("Ein Geb�ude wurde ausgew�hlt!");
						load.connection.createBuilding(i, cutHTMLout(((JButton)arg0.getSource()).getText()));
					} else if (type.equals("Ground")) {
						System.out.println("Eine Bodeneinheit wurde ausgew�hlt!");
						switch (cutHTMLout(((JButton)arg0.getSource()).getText())) {
						case ("Marine"):
							System.out.println("Marine");
							load.connection.createUnit(1, i);
							break;
						case ("Chronite Tank"):
							System.out.println("Chronit Tank");
							load.connection.createUnit(2, i);
							break;
						}
					} else if (type.equals("Air")) {
						System.out.println("Eine Lufteinheit wurde ausgew�hlt!");
						switch (cutHTMLout(((JButton)arg0.getSource()).getText())) {
						case ("Scout"):
							System.out.println("Scout");
							load.connection.createUnit(3, i);
							break;
						}
						//int number = (int) (Math.random()*4)+1;
						//String UnitString = "Unit/Air/" + cutHTMLout(((JButton)arg0.getSource()).getText())+ ".png";
						//game.entity(UnitString, number, true);
					} else if (type.equals("Generation")) {
						System.out.println("Eine generierung wurde ausgew�hlt!");
					} else if (type.equals("Destroy")) {
						System.out.println("Das gew�hlte Geb�ude wird abgerissen");
						load.connection.destroyBuilding(i);
					} else if (type.equals("null")) {
						System.out.println("Keine Option f�r dieses Button vorhanden!!");
					} else {
						System.out.println("Kritischer Fehler: ActionButton.java => getEntityType.mth");
					}	
				}
			});
			jButton.add(btn);
			console.add(btn);
		}
		System.out.println(jButton.size());
		JLabel Description = new JLabel("");
		Description.setText(wrapLines(BuildingsEntity.get(i).getDescription()));
		Description.setForeground(Color.BLACK);
		Description.setBounds(20, -50, 180, 300);
		console.add(Description);
		
		JLabel BuildingNameLbl = new JLabel("");
		BuildingNameLbl.setText(wrapLines(BuildingsEntity.get(i).getName()));
		BuildingNameLbl.setForeground(Color.BLACK);
		BuildingNameLbl.setFont(new Font("Arial", Font.PLAIN, 19));
		BuildingNameLbl.setBounds(20, -20, 180, 100);
		console.add(BuildingNameLbl);

		console.repaint();
	}
	
	public void deselectOptions(Panel panel){
		JButton[] jButtonArray = new JButton[jButton.size()];
		jButtonArray = jButton.toArray(jButtonArray);
		if (jButtonArray.length == 0){
		}else{
			for (JButton n : jButtonArray){
				panel.remove(n);
			}
		}
		jButton.clear();;
		panel.repaint();
	}

	// �ndert einen Zustand eine Entitys
	public void Entity(int i, Panel panel, ArrayList<Unit> entity){

		panel.remove(btnBuilding);
		panel.remove(btnSpawnSoldir);

		
		// Building-Button
		btnForward = new JButton("Forward");
		btnCreator.createOne(btnForward, 20, 600, 60, 60, 87);
		btnForward.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {

			}
		});
		panel.add(btnForward);
		
		// Building-Button
		btnBackward = new JButton("Backward");
		btnCreator.createOne(btnBackward, 90, 600, 60, 60, 87);
		btnBackward.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {

			}
		});
		panel.add(btnBackward);	
		panel.repaint();
	}
	
	// �nder die Zust�nde einer ganzen Gruppe
	public void GroupEntity(ArrayList<Unit> entity, Panel panel){
		
	}
	
	public void Barracks(){
		
	}
	
	public String getEntityAction(String EntityName){
		File file;
		file = new File("Buildings/" + EntityName + ".png");
		if (file.exists()) {
			return "Building";
		}
		file = new File("Unit/Air/" + EntityName + ".png");
		if (file.exists()) {
			return "Air";
		}
		file = new File("Unit/Ground/" + EntityName + ".png");
		if (file.exists()) {
			return "Ground";
		}
		
		switch (EntityName) {
		case "Recruit":
			return "Generation";
		case "Salvage":
			return "Generation";
		case "Financel Support":
			return "Generation";
		case "Reinforcments":
			return "Generation";
		case "Reserve Energy":
			return "Generation";
		case "Power":
			return "Generation";
		case "Exhange":
			return "Generation";
		case "Traiding":
			return "Generation";
		case "Resuscitate":
			return "Generation";
		case "Recover":
			return "Generation";
		case "Black Operations":
			return "Generation";
		case "Destroy":
			return "Destroy";
		}
		return "null";
		
	}	
	
	public String cutHTMLout(String html){
		String ButtonName = html.substring(6);
		String[] parts = ButtonName.split("<");
		ButtonName = parts[0];
		return ButtonName;
	}

	public void destroyUserOptions(Panel console) {
		deselectOptions(console);
	}

}
