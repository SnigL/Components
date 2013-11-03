package com.example.components;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("runo")

public class ComponentsUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ComponentsUI.class, widgetset = "com.example.components.widgetset.ComponentsWidgetset")
	public static class Servlet extends VaadinServlet {
	}
	
	private menuTabSheet mainTab = new menuTabSheet();

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		final Panel topPanel = new Panel("Bootcamp -13");
		topPanel.setWidth("1400px");
		layout.addComponent(topPanel);
		layout.addComponent(mainTab);

	}

}