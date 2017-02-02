/**
 * Factory class so that the GameDisplay class does not need to know the name 
 * of ConnectLogic
 * 
 * @author Richard Salas Chavez (7764077)
 * @version June 15, 2016
 */

public class ControllerFactory {
	
	public static ConnectController makeController(GameDisplay gd) {
		return new ConnectLogic(gd);
	}
	
}
