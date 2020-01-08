import controllers.primary.ViewController;
import controllers.primary.login.LoginController;
import controllers.serialisation.RepositorySerialisationController;

public class SOFT252 {
    public static void main(String[] args){
        RepositorySerialisationController.loadAll();

        ViewController viewController = ViewController.getInstance();

        viewController.initialise(
                 LoginController.getInstance().index(), "Patient Management System"
        );
    }
}
