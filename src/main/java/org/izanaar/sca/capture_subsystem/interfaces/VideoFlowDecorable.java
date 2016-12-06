package org.izanaar.sca.capture_subsystem.interfaces;

import org.izanaar.sca.capture_subsystem.auxillary.image_decorators.ImageDecorator;

/**
 * Created by ierus on 5/20/15.
 */
public interface VideoFlowDecorable {

    void addDecorator(ImageDecorator decorator);
    void deleteDecorator(String alias);

}
