package by.itacademy.matveenko.jd2.controller;

import java.util.HashMap;
import java.util.Map;

import by.itacademy.matveenko.jd2.controller.impl.DoAddNews;
import by.itacademy.matveenko.jd2.controller.impl.DoChangeLocal;
import by.itacademy.matveenko.jd2.controller.impl.DoDeleteNews;
import by.itacademy.matveenko.jd2.controller.impl.DoEditNews;
import by.itacademy.matveenko.jd2.controller.impl.DoRegistration;
import by.itacademy.matveenko.jd2.controller.impl.DoSignIn;
import by.itacademy.matveenko.jd2.controller.impl.DoSignOut;
import by.itacademy.matveenko.jd2.controller.impl.DoUnpublishNews;
import by.itacademy.matveenko.jd2.controller.impl.GoToAddNewsPage;
//import by.itacademy.matveenko.jd2.controller.impl.GoToBasePage;
import by.itacademy.matveenko.jd2.controller.impl.GoToEditNewsPage;
import by.itacademy.matveenko.jd2.controller.impl.GoToNewsList;
import by.itacademy.matveenko.jd2.controller.impl.GoToViewNews;
import by.itacademy.matveenko.jd2.controller.impl.GoToRegistrationPageCommand;
import by.itacademy.matveenko.jd2.controller.impl.GoToUserPersonalAccount;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		//commands.put(CommandName.GO_TO_BASE_PAGE, new GoToBasePage());
		commands.put(CommandName.GO_TO_VIEW_NEWS, new GoToViewNews());
		commands.put(CommandName.GO_TO_NEWS_LIST, new GoToNewsList());		
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.GO_TO_USER_PERSONAL_ACCOUNT, new GoToUserPersonalAccount());		
		commands.put(CommandName.DO_SIGN_IN, new DoSignIn());
		commands.put(CommandName.DO_SIGN_OUT, new DoSignOut());		
		commands.put(CommandName.GO_TO_ADD_NEWS_PAGE, new GoToAddNewsPage());
        commands.put(CommandName.DO_ADD_NEWS, new DoAddNews());
        commands.put(CommandName.GO_TO_EDIT_NEWS_PAGE, new GoToEditNewsPage());
        commands.put(CommandName.DO_EDIT_NEWS, new DoEditNews());
        commands.put(CommandName.DO_UNPUBLISH_NEWS, new DoUnpublishNews());
        commands.put(CommandName.DO_DELETE_NEWS, new DoDeleteNews());
        commands.put(CommandName.DO_CHANGE_LOCAL, new DoChangeLocal());      
	}
		
	public Command getCommand(String name) {
		CommandName commandName = CommandName.valueOf(name.toUpperCase());
		Command command = commands.get(commandName);
		return command;
	}
}