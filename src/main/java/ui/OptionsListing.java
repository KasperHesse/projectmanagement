package ui;

/**
 * @author Kasper Hesse, s183735
 *
 */
public class OptionsListing {

	static final String[] MAINOPTIONS = new String[] {"Logout", 
			"Show projects", 
			"New project",
			"View free developers"
	};

	static final String[] PROJECTOPTIONS = new String[] {"Choose activity",
			"Manage project"
	};
	
	static final String[] MANAGEPROJECTOPTIONS = new String[] {"Add developer (PM)",
			"Remove developer (PM)",
			"Change/assign project manager",
			"Get time report (PM)",
			"Add new activity (PM)"
	};
	
	static final String[] ACTIVITYOPTIONS = new String[] {"Register time", 
			"Edit registered time", 
			"View registered time", 
			"Seek assistance", 
			"Assign developer to activity (PM)",
			"Remove developer from activity (PM)",
			"Remove assisant developer (PM)",
			"Change hours budgeted (PM)",
			"Change start date",
			"Change stop date"
	};
}
