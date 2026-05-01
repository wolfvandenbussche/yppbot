package bot.state;

public enum GameState {
    UNKNOWN,
    LOGIN_SCREEN,       // Black bg + blue oval "PUZZLE PIRATES", Logon button
    CHARACTER_CREATE,   // Creating a new pirate (name input, appearance)
    CHARACTER_SELECT,   // Choosing from existing pirates on the account
    LOADING,            // Transition / loading screen
    IN_TOWN,            // Standing on an island in town
    NOTICE_BOARD,       // Notice board / expedition board dialog open
    CONFIRM_DIALOG,     // Generic yes/no confirmation popup
    ON_SHIP,            // On a ship deck (not at a station)
    BILGING,            // At bilge station — puzzle active
    BLACKSMITHING,      // At smithy — puzzle active
    CARPENTRY,          // At carpentry station
    SAILING,            // At sailing station
    RIGGING,            // At rigging station
    GUNNING,            // At cannon station
    MISSION_COMPLETE    // Mission finished dialog
}
