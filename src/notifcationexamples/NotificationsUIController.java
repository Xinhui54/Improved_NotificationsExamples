/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button t1;
    
    @FXML
    private Button t2;
    
    @FXML
    private Button t3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if (task1 == null)
        {
            System.out.println("start task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            t1.setText("End Task1");
        }
        else
        {
            task1.end();
            t1.setText("Start Task1");
            task1=null;
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            t1.setText("Start Task1");
        }
        
        if (message.equals("Task2 done.")) {
            System.out.println("T2D2");
            t2.setText("Start Task2");
        }
        if (message.equals("Task3 done.")) {
            t3.setText("Start Task3");
        }

        textArea.appendText(message + "\n");
        
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if (task2 == null)
        {
            System.out.println("start task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done."))
                {
                    t2.setText("Start Task2");
                    task2=null;
                }
            });
            t2.setText("End Task2");
            task2.start();

            
        }
        else
        {
            task2.end();
            t2.setText("Start Task2");
            task2=null;
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if (task3 == null)
        {
            System.out.println("start task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                String message=(String)evt.getNewValue();
                textArea.appendText(message + "\n");
                if (message.equals("Task3 done."))
                {
                    t3.setText("Start Task3");
                    task3=null;
                }
            });
            t3.setText("End Task3");
            task3.start();

            
        }
        else
        {
            task3.end();
            t3.setText("Start Task3");
            task3=null;
        }
    }
}
