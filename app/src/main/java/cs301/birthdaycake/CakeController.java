package cs301.birthdaycake;

public class CakeController {
    private CakeView cakeView;
    private CakeModel cakeModel;

    public CakeController(CakeView cakeView){
            this.cakeView = cakeView;
            this.cakeModel = cakeView.getCakeModel();
    }
}
