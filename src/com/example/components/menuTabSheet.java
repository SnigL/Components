package com.example.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.GregorianCalendar;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.data.Item;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@SuppressWarnings("serial")
public class menuTabSheet extends VerticalLayout implements
        TabSheet.SelectedTabChangeListener {
	
    private TabSheet tabSheet;
    private Table table;
    private Calendar calendar;

    public menuTabSheet() {

        /**
         * Tab 1 content
         */
        VerticalLayout l1 = new VerticalLayout();
        l1.setMargin(true);
        table = new Table();
        table.setWidth("700px");
		table.setSelectable(true);
		table.setImmediate(true);
		table.addContainerProperty("Firstname", String.class, null);
		table.addContainerProperty("Lastname", String.class, null);
		table.addContainerProperty("Street", String.class, null);
		table.addContainerProperty("YoB", String.class, null);
		table.addContainerProperty("YoD", String.class, null);
		
		Object newItemId = table.addItem();
		Item row1 = table.getItem(newItemId);
		row1.getItemProperty("Firstname").setValue("Martin");
		row1.getItemProperty("Lastname").setValue("Svensson");
		row1.getItemProperty("Street").setValue("Sverigevägen");
		row1.getItemProperty("YoB").setValue("2014");
		row1.getItemProperty("YoD").setValue("2041");
		table.addItem(new Object[]{"David", "Svensson", "Skånevägen", "1998", "2048"}, 2);
		table.addItem(new Object[]{"Fredrik", "Andersson", "Avenyn", "2015", "2050"}, 3);
		table.addItem(new Object[]{"Diana", "Svensson", "Stockholmsvägen", "2016", "2060"}, 4);
		
		 Panel tablePanel = new Panel("Customers");
	        Layout panelContentTable = new VerticalLayout();
	        panelContentTable.addComponents(table);
	        tablePanel.setContent(panelContentTable);
		
        l1.addComponent(tablePanel);
        
        /**
         * Tab 2 content
         */
        VerticalLayout l2 = new VerticalLayout();
        l2.setMargin(true);

        final Image image = new Image("Uploaded Image");
        image.setVisible(false);

        class ImageUploader implements Receiver, SucceededListener {
            public File file;
            
            public OutputStream receiveUpload(String filename,
                                              String mimeType) {
                FileOutputStream fos = null; 
                try {
                    file = new File("" +filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                                     e.getMessage(),
                                     Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
                    return null;
                }
                return fos;
            }

            public void uploadSucceeded(SucceededEvent event) {
                image.setVisible(true);
                image.setSource(new FileResource(file));
            }
        };
        ImageUploader receiver = new ImageUploader(); 

        Upload upload = new Upload("", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);

        Panel panel = new Panel("File");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload, image);
        panel.setContent(panelContent);
        
        l2.addComponent(panel);
         
       /**
        * Tab 3 content
        */     
        VerticalLayout l3 = new VerticalLayout();
        l3.setMargin(true);
    	calendar = new Calendar();
    	calendar.setWidth("1300px");
    	calendar.setHeight("650px");
    	calendar.setFirstVisibleHourOfDay(8);
    	
    	GregorianCalendar startMondayMeeting = new GregorianCalendar();
        GregorianCalendar endMondayMeeting   = new GregorianCalendar();
        GregorianCalendar startWednesdayMeeting = new GregorianCalendar();
        GregorianCalendar endWednesdayMeeting = new GregorianCalendar();
    	
    	startMondayMeeting.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startMondayMeeting.set(java.util.Calendar.MINUTE, 00);
        startMondayMeeting.set(java.util.Calendar.DAY_OF_WEEK,
                     java.util.Calendar.MONDAY);
        startWednesdayMeeting.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startWednesdayMeeting.set(java.util.Calendar.MINUTE, 0);
        startWednesdayMeeting.set(java.util.Calendar.DAY_OF_WEEK,
        			java.util.Calendar.WEDNESDAY);
        endWednesdayMeeting.set(java.util.Calendar.HOUR_OF_DAY, 10);
        endWednesdayMeeting.set(java.util.Calendar.MINUTE, 0);
        endWednesdayMeeting.set(java.util.Calendar.DAY_OF_WEEK,
                     java.util.Calendar.WEDNESDAY);
        endMondayMeeting.set(java.util.Calendar.HOUR_OF_DAY, 10);
        endMondayMeeting.set(java.util.Calendar.MINUTE, 0);
        endMondayMeeting.set(java.util.Calendar.DAY_OF_WEEK,
                     java.util.Calendar.MONDAY);
        BasicEvent mondayEvent = new BasicEvent("Morning meeting", "",
                startMondayMeeting.getTime(), endMondayMeeting.getTime());
        BasicEvent wednesdayEvent = new BasicEvent("Wednesday meeting", "",
        		startWednesdayMeeting.getTime(), endWednesdayMeeting.getTime());
        calendar.addEvent(mondayEvent);
        calendar.addEvent(wednesdayEvent);
        
        calendar.setHandler(new BasicEventResizeHandler() {
        	public static final long twelveHoursInMs = 12*60*60*1000;
        	
        	protected void setDates(EditableCalendarEvent event, Date start, Date end) {
        		long eventLength = end.getTime() - start.getTime();
        		if (eventLength <= twelveHoursInMs) {
        			super.setDates(event, start, end);
        		}
        	}
        });
    	
    	l3.addComponent(calendar);
    	
    	//Google Maps Plugin that doesn't work with Vaadin 7...
        //GoogleMap googleMap = new GoogleMap(getApplication(), new Point2D.Double(22.3, 60.4522), 8);
        //googleMap.setWidth("400px");
        //googleMap.setHeight("400px");
    	
    	/**
    	 * Tab 4
    	 */
    	VerticalLayout l4 = new VerticalLayout();
    	l4.setMargin(true);
    	Chart chart = new Chart();
    	chart.setHeight("600px");
    	chart.setWidth("1200px");
    	
    	Configuration conf = chart.getConfiguration();
    	conf.setTitle("Number of customers");
    	
    	PlotOptionsLine plotOptions = new PlotOptionsLine();
    	plotOptions.setMarker(new Marker(false));
    	conf.setPlotOptions(plotOptions);
    	
    	ListSeries series = new ListSeries("Customers");
    	series.setData(139, 98, 187, 190, 225);
    	conf.addSeries(series);
    	
    	XAxis xaxis = new XAxis();
    	xaxis.setCategories("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
    	xaxis.setTitle("Weekdays");
    	
    	YAxis yaxis = new YAxis();
    	yaxis.setTitle("");
    	conf.addxAxis(xaxis);
    	conf.addyAxis(yaxis);
    	
    	l4.addComponent(chart);
    	
    	/**
    	 * Tab 5
    	 */
    	
    	VerticalLayout l5 = new VerticalLayout();
    	l5.setMargin(true);
    	
    	Chart itemChart = new Chart(ChartType.COLUMN);
    	itemChart.setHeight("600px");
    	itemChart.setWidth("1200px");
    	
    	Configuration itemChartCfg = itemChart.getConfiguration();
    	itemChartCfg.setTitle("Browser market share");
    	
    	ListSeries serieData = new ListSeries("September 2013");
    	serieData.setData(53.2, 27.8, 12.1, 3.9, 1.7);
    	itemChartCfg.addSeries(serieData);
    	
    	ListSeries serieDataOld = new ListSeries("September 2012");
    	serieDataOld.setData(44.1, 33.2, 16.4, 4.2, 2.1);
    	itemChartCfg.addSeries(serieDataOld);
    	
    	XAxis itemXaxis = new XAxis();
    	itemXaxis.setCategories("Chrome", "Firefox", "Internet Explorer", "Safari", "Opera");
    	itemXaxis.setTitle("Browser");
    	YAxis itemYaxis = new YAxis();
    	itemYaxis.setTitle("%");
    	itemChartCfg.addxAxis(itemXaxis);
    	itemChartCfg.addyAxis(itemYaxis);
    	
    	l5.addComponent(itemChart);
    	
    	/**
    	 * Initiate TabSheet()
    	 */
    	
    	tabSheet = new TabSheet();
    	tabSheet.setHeight("700px");
    	tabSheet.setWidth("1400px");
    	tabSheet.addTab(l1, "Home");
    	tabSheet.addTab(l2, "Upload");
    	tabSheet.addTab(l3, "Calendar");
    	tabSheet.addTab(l4, "Customer chart");
    	tabSheet.addTab(l5, "Browser chart");
    	tabSheet.addListener(this);

    	addComponent(tabSheet);
    }

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		// TODO Auto-generated method stub		
	}
	
}