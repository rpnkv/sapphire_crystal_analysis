package capture_subsystem.auxillary.image_decorators;

import capture_subsystem.gui.ImagePanel;
import capture_subsystem.interfaces.ImageSetable;

public abstract class ImageDecorator implements ImageSetable{

    private final String alias;
    ImageSetable innerDecorator;

    protected ImageDecorator(String alias) {
        this.alias = alias;
    }

    public void addDecorator(ImageDecorator newDecorator) throws NullPointerException{
        if (innerDecorator instanceof ImagePanel){
            newDecorator.setInnerDecorator(innerDecorator);
            this.innerDecorator = newDecorator;
        }else
            if(innerDecorator instanceof ImageDecorator)
                ((ImageDecorator) innerDecorator).addDecorator(newDecorator);
        else throw new NullPointerException("in class" + alias);
    }

    public void deleteDecorator(String alias) throws NullPointerException{
        if (innerDecorator instanceof ImageDecorator)
            if (innerDecorator.toString().equals(alias))
                innerDecorator = ((ImageDecorator) innerDecorator).getInnerDecorator();
            else
                ((ImageDecorator) innerDecorator).deleteDecorator(alias);
        else throw new NullPointerException("in class" + alias);
    }

    public ImageSetable getRootDecorator() throws NullPointerException{
        if(innerDecorator instanceof ImagePanel)
            return innerDecorator;
        else
            if (innerDecorator instanceof ImageDecorator)
                ((ImageDecorator) innerDecorator).getRootDecorator();
            else throw new NullPointerException("in decorator class" + alias);
        return null;
    }

    public ImageSetable getInnerDecorator() {
        return innerDecorator;
    }

    public void setInnerDecorator(ImageSetable innerDecorator) {
        this.innerDecorator = innerDecorator;
    }

    @Override
    public String toString() {
        return alias;
    }
}
