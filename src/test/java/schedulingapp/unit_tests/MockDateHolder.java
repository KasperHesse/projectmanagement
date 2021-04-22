//package schedulingapp.unit_tests;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
//import schedulingapp.*;
//
//
//
//public class MockDateHolder {
//	
//	DateServer dateServer = mock(DateServer.class);
//	
//	public MockDateHolder(SchedulingApp schedulingApp) {
//		GregorianCalendar calendar = new GregorianCalendar();
//		setDate(calendar);
//		schedulingApp.setDateServer(dateServer);
//	}
//
//	public void setDate(Calendar calendar) {
//		Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
//		when(this.dateServer.getDate()).thenReturn(c);
//	}
//}
