package org.izanaar.sca.capture_subsystem.auxillary.image_decorators;

import org.izanaar.sca.capture_subsystem.gui.ImagePanel;
import org.izanaar.sca.capture_subsystem.interfaces.ImageSetable;

public abstract class ImageDecorator implements ImageSetable {

    private final String alias;
    ImageSetable innerDecorator;

    protected ImageDecorator(String alias) {
        this.alias = alias;
    }

    public final void addDecorator(ImageDecorator newDecorator) throws NullPointerException{
        if (innerDecorator instanceof ImagePanel){
            newDecorator.setInnerDecorator(innerDecorator);
            this.innerDecorator = newDecorator;
        }else
            if(innerDecorator instanceof ImageDecorator)
                ((ImageDecorator) innerDecorator).addDecorator(newDecorator);
        else throw new NullPointerException("in class" + alias);
    }

    public final void deleteDecorator(String alias) throws NullPointerException{
        if (innerDecorator instanceof ImageDecorator)
            if (innerDecorator.toString().equals(alias))
                innerDecorator = ((ImageDecorator) innerDecorator).getInnerDecorator();
            else
                ((ImageDecorator) innerDecorator).deleteDecorator(alias);
        else throw new NullPointerException("in class" + alias);
    }

    public final ImageSetable getRootDecorator() throws NullPointerException{
        if(innerDecorator instanceof ImagePanel)
            return innerDecorator;
        else
            if (innerDecorator instanceof ImageDecorator)
                ((ImageDecorator) innerDecorator).getRootDecorator();
            else throw new NullPointerException("in decorator class" + alias);
        return null;
    }

    public final ImageSetable getInnerDecorator() {
        return innerDecorator;
    }

    public final void setInnerDecorator(ImageSetable innerDecorator) {
        this.innerDecorator = innerDecorator;
    }

    @Override
    public final String toString() {
        return alias;
    }
}
