import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
    The Controller class is like our listener, the controller listens what the user typed or pressed and caught it
 by the help of ActionListener, CaretListener interfaces.
 So the controller implements all these mentioned above interfaces.
 */


public class Controller implements ActionListener, CaretListener{


    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }


    public Model getModel() {
        return model;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof JComboBox){
            model.doFontAction((JComboBox<String>) event.getSource());
        }

        String command = event.getActionCommand();
        model.doAction(command);
    }


    public void caretUpdate(CaretEvent caretEvent) {
        model.doCaretAction();
    }

}

