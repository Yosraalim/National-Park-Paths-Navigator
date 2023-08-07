/**
 * @auther Yosra Alim
 * Date: July 20th, 2023
 * 
 * 
 * 
 */


import java.io.FileNotFoundException;
import java.io.IOException;

public class PathFinder {
    private Map pyramidMap;

    
    
    /**
     * Constructor for the PathFinder class. It creates an object of the type Map using the provided input file name and
     * stores it in the instance variable pyramidMap.
     *
     * @param fileName The name of the file that contains a description of the chambers of the park.
     * @throws InvalidMapCharacterException If the map contains an invalid character.
     * @throws FileNotFoundException      If the input file is not found.
     * @throws IOException                If an I/O error occurs while reading the input file.
     */
    public PathFinder(String fileName) throws InvalidMapCharacterException, FileNotFoundException, IOException {
    	try {
    		this.pyramidMap = new Map(fileName);
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    }

    
    
    
    /**
     * Finds a path from the entrance to all the treasure chambers that can be reached by satisfying the constraints
     * specified in the introduction. The computed path is stored in a stack of the class DLStack.
     *
     * @return A DLStack containing the chambers along the computed path.
     */
    public DLStack<Chamber> path() {
        DLStack<Chamber> pathStack = new DLStack<>();
        Chamber entrance = this.pyramidMap.getEntrance();
        findTreasures(entrance, pathStack);
        return pathStack;
    }

    
    private void findTreasures(Chamber current, DLStack<Chamber> pathStack) {
        if (current == null || current.isSealed() || current.isMarked()) {
            return;
        }

        current.markPushed();

        if (current.isTreasure()) {
            pathStack.push(current);
            return;
        }

        
        Chamber bestChamber = bestChamber(current);
        if (bestChamber != null) {
            findTreasures(bestChamber, pathStack);
        }

        for (int i = 0; i < 6; i++) {
            Chamber neighbor = current.getNeighbour(i);
            if (neighbor != null) {
                findTreasures(neighbor, pathStack);
            }
        }

        current.markPopped();
    }

    public Map getMap() {
        return pyramidMap;
    }

    
    
    public boolean isDim(Chamber currentChamber) {
    	
    	if (currentChamber == null) {
    		return false;
    	}
    	
    	boolean hasLightedNeighbour = false;
    	for (int i=0; i < 6; i++) {
    		if (currentChamber.getNeighbour(i) != null && currentChamber.getNeighbour(i).isLighted()) {
    			hasLightedNeighbour = true;
    			break;
    		}
    	}
    	
        return  !currentChamber.isSealed() && !currentChamber.isLighted()
                && hasLightedNeighbour ;
    }

    
    public Chamber bestChamber(Chamber currentChamber) {
        Chamber bestTreasureChamber = null;
        Chamber bestLightedChamber = null;
        Chamber bestDimChamber = null;

        
        for (int i = 0; i < 6; i++) {
            Chamber neighbor = currentChamber.getNeighbour(i);
            if (neighbor != null) {
                if (!neighbor.isTreasure()) {
                    bestTreasureChamber = neighbor;
                    break;
                }
                if (bestLightedChamber == null && (!neighbor.isLighted())) {
                    bestLightedChamber = neighbor;
                }
                if (bestDimChamber == null && !(this.isDim(neighbor))) {
                    bestDimChamber = neighbor;
                }
            }
        }

        return bestTreasureChamber != null ? bestTreasureChamber
                : (bestLightedChamber != null ? bestLightedChamber : bestDimChamber);
    }
}



