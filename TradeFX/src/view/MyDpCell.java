package view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

class MyDpCell<T, S> extends TableCell<T, S> {
	private DatePicker datePicker;

	public MyDpCell() {
		super();
		if (datePicker == null) {
			createDatePicker();
		}
		setGraphic(datePicker);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		datePicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//commitEdit( Integer.parseInt(mtf.getText().toString()));
				System.out.println("jjkjoi"+(S) datePicker.getValue());
				commitEdit((S) datePicker.getValue());
			};
		});
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				datePicker.requestFocus();
			}
		});
	}
	
//	@Override
//	public void commitEdit(S newValue) {
//		// Wird beim scrollen ausgeführt
//		System.out.println("Wird beim scrollen ausgeführt");		
//		super.commitEdit(newValue);//);
//	}
//	

	
	//@Override
	public void startEdit() {
		datePicker = new DatePicker();
	}
	@Override
	public void updateItem(S item, boolean empty) {
		 super.updateItem(item, empty);

         SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");

         if (null == this.datePicker) {
             System.out.println("datePicker is NULL");
         }

         if (empty) {
             setText(null);
             setGraphic(null);
         } else {

             if (isEditing()) {
                 setContentDisplay(ContentDisplay.TEXT_ONLY);

             } else {
            	 //System.out.println("dsfsdfsdfs"+item);
            	 if (item != null){
                 setDatepikerDate(smp.format(item));
                 setText(smp.format(item));
            	 }
                 setGraphic(this.datePicker);
                 setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
             }
          
         }
         if (item!=null){
        	 
        	 System.out.println("dp: update Item:"+smp.format(item));
         }
	}
	private void setDatepikerDate(String dateAsStr) {

        LocalDate ld = null;
        int jour, mois, annee;

        jour = mois = annee = 0;
        try {
            jour = Integer.parseInt(dateAsStr.substring(0, 2));
            mois = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDatepikerDate / unexpected error " + e);
        }

        ld = LocalDate.of(annee, mois, jour);
        //ld = LocalDate.of(1999, 1, 1);
        datePicker.setValue(ld);
    }

	private void createDatePicker() {
		this.datePicker = new DatePicker();
		datePicker.setPromptText(datePicker.getValue()+"jj/mm/aaaa");
		datePicker.setEditable(true);

		datePicker.setOnAction(new EventHandler() {
			public void handle(Event t) {
				LocalDate date = datePicker.getValue();
				int index = getIndex();

				SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
				cal.set(Calendar.MONTH, date.getMonthValue() - 1);
				cal.set(Calendar.YEAR, date.getYear());

				setText(smp.format(cal.getTime()));
				commitEdit((S) cal.getTime());

			}
		});

	}

}