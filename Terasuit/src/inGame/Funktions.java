package inGame;

import grafig.Panel;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Funktions {
	
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Integer> selectedEntitysID = new ArrayList<Integer>();
	CreateUnit cunit = new CreateUnit();
	SelectedUnits selectedUnit = new SelectedUnits();
	
	public ArrayList<Unit> getEntity() {
		return entity;
	}

	public void setEntity(ArrayList<Unit> entity) {
		this.entity = entity;
	}

	// Erstellt eine neue Einheit auf dem Spielfeld und f�gt es der Unitliste hinzu
	public void createEntity(Panel panel, String Entitytype, int color){
		entity = cunit.createEntity(panel, Entitytype, entity, color);
	}
	
	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h) {
		selectedEntitysID = selectedUnit.getGroupOfUnits(entity, selectedEntitysID, minX, minY, w, h);
		for (int id : selectedEntitysID) {
			System.out.println(id);
		}
		
		for (int id : selectedEntitysID) {
			String type = entity.get(id-1).getEntityname();
			boolean directionLeft = entity.get(id-1).isEntityRushLeft();
			int color = entity.get(id-1).getEntitymembership();
			ImageIcon pic = new ImageIcon(cunit.mark(type, directionLeft, color, false));
			Unit unit = new Unit();
			unit = entity.get(id-1);
			unit.getLabel().setIcon(pic);
			entity.set(id-1, unit);
		}
	}
	
	// Iteriert �ber eine Liste mit IDs von Einheiten in der Entity List und ver�ndert ihre Helligkeit zu dunkel
	public void deMarkEntittys(){
		for (int id : selectedEntitysID) {
			String type = entity.get(id-1).getEntityname();
			boolean directionLeft = entity.get(id-1).isEntityRushLeft();
			int color = entity.get(id-1).getEntitymembership();
			ImageIcon pic = new ImageIcon(cunit.mark(type, directionLeft, color, true));
			Unit unit = new Unit();
			unit = entity.get(id-1);
			unit.getLabel().setIcon(pic);
			entity.set(id-1, unit);
		}
		selectedEntitysID.clear();
	}
	
	/**
	 * Updated die Userliste mit den aktuellen gesendeten Serverpatch
	 * @param panel
	 * @param NEWentity
	 * @param OLDentity
	 */
	public void UpdateGameEngine(Panel panel, ArrayList<Unit> NEWentity, ArrayList<Unit> OLDentity){
		
		// �bergebende neue Angaben, werden in die Userliste �bertragen
		for (int i = 0; i != NEWentity.size(); i++){
			int numb = NEWentity.get(i).getEntityNummer();
			for (int n = 0; n != OLDentity.size(); n++){
				if (OLDentity.get(n).getEntityNummer() == numb){
					OLDentity.set(n, NEWentity.get(i));
				}
			}
		}
	}
	
	public void UpdateBuildingList(Panel panel, ArrayList<Buildings> NEWbuildings, ArrayList<Buildings> OLDbuilding){
		
		// �bergebende neue Geb�ude werden in die Userliste (Buildings) �bertragen
		for (int i = 0; i != NEWbuildings.size(); i++){
			int numb = NEWbuildings.get(i).getNumber();
			for (int n = 0; n != OLDbuilding.size(); n++){
				if (OLDbuilding.get(n).getNumber() == numb){
					OLDbuilding.set(n, NEWbuildings.get(i));
				}
			}
		}
		
	}
	
	public void UpdatedGameEntity(Panel panel, ArrayList<Unit> entity){
		for (int i = 0; i != entity.size(); i++){
			if (entity.get(i).isEntityFire() == false){
				if (entity.get(i).isEntityMove() == true){
					if (entity.get(i).isEntityRushLeft() == true){
						// L�uft nach links
					}else{
						// L�uft nach rechts
					}
				}
			}else{
				// Einheit feuert auf gegner
			}
		}
	}
	
	public void UpdatedGameBuildings(Panel panel, ArrayList<Buildings> building){
		for (int i = 0; i != building.size(); i++){
			
		}
	}
	
	
}
