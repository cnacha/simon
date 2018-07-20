
	package com.mfu.web.util;

	import java.beans.PropertyEditorSupport;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.Locale;

	public class DatePropertyEditor  extends PropertyEditorSupport {
		String format = "dd/MM/yyyy";

		Date defaultDate = new Date();

		public void setAsText(String textValue) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format,new Locale("th", "TH"));
			if (textValue == null) {
				setValue(defaultDate);
				return;
			}
			Date retDate = defaultDate;
			try {
				retDate = dateFormatter.parse(textValue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			setValue(retDate);
		}

		@Override
		public String getAsText() {
			Date date = (Date) getValue();
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format,new Locale("th", "TH"));
			if(date == null)
				return "";
			else
			return dateFormatter.format(date);
		}

	}
	